package com.example.tokotlin.room

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance=this
    }

    companion object{
        private var appInstance:App? = null
        private const val DB_NAME = "History.db"
        private var db:HistoryDatabase? = null

        fun getHistoryWeatherDAO():HistoryWeatherDAO{
            if(db==null){
                if(appInstance==null){
                    throw IllformedLocaleException("Всё сломалось")
                } else{
                    db = Room.databaseBuilder(appInstance!!,HistoryDatabase::class.java, DB_NAME)
                        .addMigrations(object :Migration(1,2){
                            override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("ALTER TABLE history_weather_entity RENAME COLUMN icon TO icon2")
                            }
                        })
                        .build()
                }
            }
            return db!!.historyWeatherDao()
        }
    }

}