package com.verome.core.ui.external.app.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.verome.core.ui.activity.ActivityProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

private const val KEY_OPEN_GALLERY = "KEY_OPEN_GALLERY"

class DefaultExternalAppService(
    private val context: Context,
    private val activityProvider: ActivityProvider,
) : ExternalAppService {
    private val galleryChannel = MutableSharedFlow<Bitmap>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override val galleryEvents: SharedFlow<Bitmap>
        get() = galleryChannel.asSharedFlow()

    private val galleryLauncher = activityProvider
        .activity
        ?.activityResultRegistry
        ?.register(
            KEY_OPEN_GALLERY,
            ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            uri?.let {
                galleryChannel.tryEmit(
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(context.contentResolver, it),
                    ),
                )
            }
        }

    override fun openGallery() {
        galleryLauncher?.launch("image/*")
    }
}