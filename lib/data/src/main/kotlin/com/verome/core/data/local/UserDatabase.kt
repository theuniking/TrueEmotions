package com.verome.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.verome.core.data.local.converters.Converters
import com.verome.core.data.local.model.EmotionEntity
import com.verome.core.data.local.model.UserEntity
import com.verome.core.data.local.relations.UserEmotionCrossRef

@Database(
    entities = [
        UserEntity::class,
        EmotionEntity::class,
        UserEmotionCrossRef::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }
}