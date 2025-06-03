package com.example.lab3.data.entity

import androidx.room.*

@Entity(
    tableName = "Subscriptions",
    foreignKeys = [
        ForeignKey(
            entity = Visitor::class,
            parentColumns = ["Id"],
            childColumns = ["VisitorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Gym::class,
            parentColumns = ["Id"],
            childColumns = ["GymId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val id: Int = 0,
    @ColumnInfo(name = "VisitorId") val visitorId: Int,
    @ColumnInfo(name = "GymId") val gymId: Int,
    @ColumnInfo(name = "StartDate") val startDate: String,
    @ColumnInfo(name = "EndDate") val endDate: String
)
