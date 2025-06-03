package com.example.lab3.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.lab3.data.entity.Gym
import kotlinx.coroutines.flow.Flow

@Dao
interface GymDao {
    @Query("SELECT * FROM Gyms")
    fun getAll(): Flow<List<Gym>>

    @Query("SELECT * FROM Gyms WHERE id = :id")
    suspend fun getById(id: Int): Gym?
}
