package com.simats.siddha.myapplication.di

import android.content.Context
import androidx.room.Room
import com.simats.siddha.myapplication.api.ApiService
import com.simats.siddha.myapplication.api.AuthManager
import com.simats.siddha.myapplication.api.Repository
import com.simats.siddha.myapplication.data.local.AnalysisDao
import com.simats.siddha.myapplication.data.local.AppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://10.67.101.127:8000/api/"

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authManager: AuthManager): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val token = authManager.getTokenSync()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val tokenAuthenticator = object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                val token = authManager.getTokenSync() ?: return null
                val refresh = authManager.getRefreshTokenSync() ?: return null

                synchronized(this) {
                    val newToken = authManager.getTokenSync()
                    if (token != newToken && newToken != null) {
                        return response.request.newBuilder()
                            .header("Authorization", "Bearer $newToken")
                            .build()
                    }

                    val client = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).build()
                    val jsonBody = "{\"refresh\":\"$refresh\"}"
                    val requestBody = jsonBody.toRequestBody("application/json".toMediaType())
                    val refreshRequest = Request.Builder()
                        .url(BASE_URL + "token/refresh/")
                        .post(requestBody)
                        .build()

                    try {
                        val refreshResponse = client.newCall(refreshRequest).execute()
                        if (refreshResponse.isSuccessful) {
                            refreshResponse.body?.string()?.let { bodyString ->
                                val json = JSONObject(bodyString)
                                val newAccess = json.getString("access")
                                val newRefresh = if (json.has("refresh")) json.getString("refresh") else refresh
                                runBlocking { authManager.saveToken(newAccess, newRefresh) }
                                
                                return response.request.newBuilder()
                                    .header("Authorization", "Bearer $newAccess")
                                    .build()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    runBlocking { authManager.clearToken() }
                    return null
                }
            }
        }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, analysisDao: AnalysisDao, authManager: AuthManager): Repository {
        return Repository(apiService, analysisDao, authManager)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "siddha_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAnalysisDao(appDatabase: AppDatabase): AnalysisDao {
        return appDatabase.analysisDao()
    }
}
