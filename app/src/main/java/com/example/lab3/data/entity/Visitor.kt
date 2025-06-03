package com.example.lab3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Visitors")
data class Visitor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val id: Int = 0,

    @ColumnInfo(name = "LastName")
    val lastName: String,

    @ColumnInfo(name = "FirstName")
    val firstName: String,

    @ColumnInfo(name = "Phone")
    val phone: String,

    @ColumnInfo(name = "Email")
    val email: String
)
