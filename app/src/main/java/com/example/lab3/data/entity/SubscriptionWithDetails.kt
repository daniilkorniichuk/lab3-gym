package com.example.lab3.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SubscriptionWithDetails(
    @Embedded val subscription: Subscription,

    @Relation(
        parentColumn = "VisitorId",
        entityColumn = "Id"
    )
    val visitor: Visitor?,

    @Relation(
        parentColumn = "GymId",
        entityColumn = "Id"
    )
    val gym: Gym?
)
