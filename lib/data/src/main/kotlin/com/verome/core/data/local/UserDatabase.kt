package com.verome.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.verome.core.data.local.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }
}