package com.example.lab3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gyms")
data class Gym(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val id: Int = 0,

    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "Address")
    val address: String
)
