package com.simats.siddha.myapplication.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.simats.siddha.myapplication.api.RecommendedPlanDto
import com.simats.siddha.myapplication.api.RiskFactor



class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromFloatList(value: List<Float>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toFloatList(value: String?): List<Float>? {
        if (value == null) return emptyList()
        val type = object : TypeToken<List<Float>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRiskFactorList(value: List<RiskFactor>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toRiskFactorList(value: String?): List<RiskFactor>? {
        if (value == null) return emptyList()
        val type = object : TypeToken<List<RiskFactor>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRecommendedPlanDto(value: RecommendedPlanDto?): String? {
        return if (value == null) null else gson.toJson(value)
    }

    @TypeConverter
    fun toRecommendedPlanDto(value: String?): RecommendedPlanDto? {
        if (value == null) return null
        val type = object : TypeToken<RecommendedPlanDto>() {}.type
        return gson.fromJson(value, type)
    }
}
