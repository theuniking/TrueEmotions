package com.verome.core.ui.external.app.service

import android.graphics.Bitmap
import kotlinx.coroutines.flow.SharedFlow

interface ExternalAppService {
    val galleryEvents: SharedFlow<Bitmap>
    fun openGallery()
}