package com.simats.siddha.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface AnalysisDao {

    @Query("SELECT * FROM analysis_cache WHERE id = 1")
    suspend fun getCachedAnalysis(): AnalysisEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisEntity)
    
    @Query("DELETE FROM analysis_cache")
    suspend fun clearCache()
}
