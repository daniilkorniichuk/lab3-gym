package com.example.lab3.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab3.data.dao.GymDao
import com.example.lab3.data.dao.SubscriptionDao
import com.example.lab3.data.dao.VisitorDao
import com.example.lab3.data.entity.Gym
import com.example.lab3.data.entity.Subscription
import com.example.lab3.data.entity.Visitor

@Database(entities = [Visitor::class, Gym::class, Subscription::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun visitorDao(): VisitorDao
    abstract fun gymDao(): GymDao
    abstract fun subscriptionDao(): SubscriptionDao
}
