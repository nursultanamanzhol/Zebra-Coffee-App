package com.example.zebracoffee.common

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    duration: Long,
    uri: Uri,
    modifier: Modifier = Modifier,
    isPaused: Boolean,
    zoomScale: Float = 1.2f  // параметр масштабирования, по умолчанию 120%
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().also { player ->
            val mediaItem = MediaItem.fromUri(uri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = !isPaused
            player.volume = 0f  // Установка громкости на 0 для отключения звука
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(isPaused) {
        exoPlayer.playWhenReady = !isPaused
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                resizeMode =
                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Установка режима масштабирования
                videoSurfaceView?.apply {
                    scaleX = zoomScale // Увеличение размера видео
                    scaleY = zoomScale
                }
            }
        },
        modifier = modifier.fillMaxSize()
    )
}
