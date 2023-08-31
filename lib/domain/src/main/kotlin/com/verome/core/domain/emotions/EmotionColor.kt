package com.verome.core.domain.emotions

import android.os.Parcelable
import com.verome.domain.R
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class EmotionColor(
    val name: String,
    val color: Int,
) : Parcelable {
    data object Joy : EmotionColor(
        name = "Joy",
        color = R.color.green,
    )

    data object Sadness : EmotionColor(
        name = "Sadness",
        color = R.color.blue,
    )

    data object Anger : EmotionColor(
        name = "Anger",
        color = R.color.red,
    )

    data object Fear : EmotionColor(
        name = "Fear",
        color = R.color.yellow,
    )

    data object Shame : EmotionColor(
        name = "Shame",
        color = R.color.grey,
    )
}