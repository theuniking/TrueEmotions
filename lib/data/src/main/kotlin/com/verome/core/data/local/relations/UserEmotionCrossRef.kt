package com.verome.core.data.local.relations

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "emotionId"])
data class UserEmotionCrossRef(
    val userId: Long,
    val emotionId: Long,
)
