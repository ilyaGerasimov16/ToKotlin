package com.example.tokotlin.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryWeatherEntity)

    @Query("SELECT * FROM history_weather_entity")
    fun getAllHistoryWeather():List<HistoryWeatherEntity>

/** Lesson 9 **/
    @Query("DELETE FROM history_weather_entity WHERE id= :id")
    fun delete(id: Long )

    @Query("SELECT * FROM history_weather_entity WHERE id= :id")
    fun getHistoryCursor(id: Long ):Cursor
/****/
}