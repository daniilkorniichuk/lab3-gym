package com.example.lab3.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.lab3.data.entity.Visitor
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitorDao {
    @Query("SELECT * FROM Visitors")
    fun getAll(): Flow<List<Visitor>>

    @Query("SELECT * FROM Visitors WHERE id = :id")
    suspend fun getById(id: Int): Visitor?
}
