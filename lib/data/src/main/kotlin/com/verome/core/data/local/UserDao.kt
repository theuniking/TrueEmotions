package com.verome.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.verome.core.data.local.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT id FROM userEntity WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getIdFromEmailAndPassword(email: String, password: String): Long

    @Query("SELECT * FROM userEntity WHERE id = :id")
    suspend fun getUserById(id: Long): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(userEntity: UserEntity): Long

    @Query("UPDATE userEntity SET avatar = :image WHERE id = :id")
    suspend fun setAvatar(id: String, image: String)
}