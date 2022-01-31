package com.example.tokotlin.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryWeatherEntity::class], version = 2, exportSchema = false)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyWeatherDao():HistoryWeatherDAO
}