package com.verome.emotions.home.presentation.reflection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.verome.core.ui.theme.AppTheme
import com.verome.home.R

@Composable
fun ReflectionContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.gif_yoga).apply(block = {
                size(Size.ORIGINAL)
            }).build(),
            imageLoader = imageLoader,
        ),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ReflectionContentPreview() {
    AppTheme {
        ReflectionContent()
    }
}