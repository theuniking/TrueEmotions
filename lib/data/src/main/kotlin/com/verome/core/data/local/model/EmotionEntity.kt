package com.verome.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class EmotionEntity(
    val action: String,
    val whatHappened: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val tags: List<String>,
    val emotions: List<String>,
    val color: Int,
    val userId: Long,
    @PrimaryKey val id: Long? = null,
)