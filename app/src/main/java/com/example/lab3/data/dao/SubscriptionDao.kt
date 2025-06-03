package com.example.lab3.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab3.data.entity.Subscription
import com.example.lab3.data.entity.SubscriptionWithDetails

@Dao
interface SubscriptionDao {

    @Transaction
    @Query("SELECT * FROM Subscriptions")
    fun getAllWithDetails(): LiveData<List<SubscriptionWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscription: Subscription)

    @Update
    suspend fun update(subscription: Subscription)

    @Delete
    suspend fun delete(subscription: Subscription)

    @Query("SELECT * FROM Subscriptions WHERE Id = :id")
    suspend fun getById(id: Int): Subscription?

    @Transaction
    @Query("SELECT * FROM Subscriptions")
    fun getAllAsFlow(): kotlinx.coroutines.flow.Flow<List<SubscriptionWithDetails>>
}
