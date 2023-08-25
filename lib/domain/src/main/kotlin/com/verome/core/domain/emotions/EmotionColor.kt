package com.verome.core.domain.emotions

import android.os.Parcelable
import com.verome.domain.R
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class EmotionColor(
    val color: Int,
) : Parcelable {
    data object Joy : EmotionColor(
        color = R.color.green,
    )

    data object Sadness : EmotionColor(
        color = R.color.blue,
    )

    data object Anger : EmotionColor(
        color = R.color.red,
    )

    data object Fear : EmotionColor(
        color = R.color.yellow,
    )

    data object Shame : EmotionColor(
        color = R.color.grey,
    )
}