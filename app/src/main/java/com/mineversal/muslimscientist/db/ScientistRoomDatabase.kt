package com.mineversal.muslimscientist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mineversal.muslimscientist.Scientist

//Database annotation to specify the entities and set version
@Database(entities = [Scientist::class], version = 1, exportSchema = false)
abstract class ScientistRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: ScientistRoomDatabase? = null

        fun getDatabase(context: Context): ScientistRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScientistRoomDatabase::class.java,
                    "note_db"
                )
                    .allowMainThreadQueries() //allows Room to executing task in main thread
                    .fallbackToDestructiveMigration() //allows Room to recreate table if no migrations found
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getScientistDao() : ScientistDao
}