package com.verome.core.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.verome.core.data.local.model.EmotionEntity
import com.verome.core.data.local.model.UserEntity

data class UserWithEmotions(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
    )
    val emotions: List<EmotionEntity>,
)