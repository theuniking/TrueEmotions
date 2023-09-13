package com.verome.core.domain.emotions

import android.os.Parcelable
import com.verome.domain.R
import kotlinx.parcelize.Parcelize

@Parcelize
open class EmotionColor(
    val name: String,
    val color: Int,
    val trackerImpact: Float,
) : Parcelable {
    data object Joy : EmotionColor(
        name = "Joy",
        color = R.color.green,
        trackerImpact = 4f,
    )

    data object Sadness : EmotionColor(
        name = "Sadness",
        color = R.color.blue,
        trackerImpact = -0.5f,
    )

    data object Anger : EmotionColor(
        name = "Anger",
        color = R.color.red,
        trackerImpact = -3f,
    )

    data object Fear : EmotionColor(
        name = "Fear",
        color = R.color.yellow,
        trackerImpact = -1f,
    )

    data object Shame : EmotionColor(
        name = "Shame",
        color = R.color.grey,
        trackerImpact = -2f,
    )
}