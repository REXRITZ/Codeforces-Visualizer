package com.ritesh.codeforcesvisualizer.util

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListToString(data: List<String>?): String {
        return data?.joinToString(separator = ",") ?: "[]"
    }

    @TypeConverter
    fun toStringFromList(data: String?): List<String> {
        return data?.split(",") ?: emptyList()
    }
}