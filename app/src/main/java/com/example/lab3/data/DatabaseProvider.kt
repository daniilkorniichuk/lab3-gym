package com.example.lab3.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val dbName = "lab3.db"
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                dbName
            )
                .createFromAsset("lab3.db")
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}
