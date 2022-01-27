package com.example.tokotlin.room

import androidx.room.*

@Dao
interface HistoryWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryWeatherEntity)

    @Query("SELECT * FROM history_weather_entity")
    fun getAllHistoryWeather():List<HistoryWeatherEntity>

    //fun getAllHistoryWeather() // TODO получить по какому-то полю



    /*
    @Delete
    fun delete(entity: HistoryWeatherEntity)

    @Update
    fun update(entity: HistoryWeatherEntity)
    */
}