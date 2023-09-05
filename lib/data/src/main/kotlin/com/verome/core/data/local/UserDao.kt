package com.verome.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.verome.core.data.local.model.EmotionEntity
import com.verome.core.data.local.model.UserEntity
import com.verome.core.data.local.relations.UserEmotionCrossRef
import com.verome.core.data.local.relations.UserWithEmotions

@Dao
interface UserDao {
    @Query("SELECT id FROM userEntity WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getIdFromEmailAndPassword(email: String, password: String): Long

    @Query("SELECT * FROM userEntity WHERE id = :id")
    suspend fun getUserById(id: Long): UserEntity

    @Query("SELECT * FROM emotionEntity WHERE id = :id")
    suspend fun getEmotionById(id: Long): EmotionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(userEntity: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmotion(emotionEntity: EmotionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEmotionCrossRef(crossRef: UserEmotionCrossRef)

    @Query("UPDATE userEntity SET avatar = :image WHERE id = :id")
    suspend fun setAvatar(id: String, image: String)

    @Query("UPDATE userEntity SET name = :name WHERE id = :id")
    suspend fun setName(id: String, name: String)

    @Query("SELECT * FROM userEntity WHERE id = :userId LIMIT 1")
    suspend fun getEmotionsOfUser(userId: String): UserWithEmotions
}