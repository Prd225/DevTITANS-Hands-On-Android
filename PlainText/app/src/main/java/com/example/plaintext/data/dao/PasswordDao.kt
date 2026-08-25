package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDao : BaseDao<Password> {
    @Query("SELECT * FROM passwords")
    abstract fun getAll(): Flow<List<Password>>

    @Query("SELECT * FROM passwords where id = :id")
    abstract fun getById(id: Int): Password?

    @Query("SELECT COUNT(*) == 0 FROM passwords")
    abstract fun isEmpty(): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM passwords WHERE login = :login AND id != :excludeID")
    abstract suspend fun countByLogin(login: String, excludeID: Int): Int
}