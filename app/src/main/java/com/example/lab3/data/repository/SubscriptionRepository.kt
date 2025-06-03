package com.example.lab3.data.repository

import com.example.lab3.data.dao.SubscriptionDao
import com.example.lab3.data.entity.Subscription
import com.example.lab3.data.entity.SubscriptionWithDetails
import kotlinx.coroutines.flow.Flow

class SubscriptionRepository(private val dao: SubscriptionDao) {

    fun getAllSubscriptions(): Flow<List<SubscriptionWithDetails>> {
        return dao.getAllAsFlow()
    }

    suspend fun insert(subscription: Subscription) {
        dao.insert(subscription)
    }

    suspend fun update(subscription: Subscription) {
        dao.update(subscription)
    }

    suspend fun delete(subscription: Subscription) {
        dao.delete(subscription)
    }

    suspend fun getById(id: Int): Subscription? {
        return dao.getById(id)
    }
}
