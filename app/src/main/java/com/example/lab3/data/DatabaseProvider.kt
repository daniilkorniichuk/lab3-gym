package com.example.lab3.data

import android.content.Context
import androidx.room.Room
import java.io.File

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val dbName = "lab3.db"
            val dbFile: File = context.getDatabasePath(dbName)

            val builder = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                dbName
            )

            if (!dbFile.exists()) {
                builder.createFromAsset(dbName)
            }

            builder
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}
