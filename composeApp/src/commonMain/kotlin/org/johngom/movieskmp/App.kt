package org.johngom.movieskmp

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.johngom.movieskmp.data.database.MoviesDAO
import ui.screens.Navigation

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(moviesDAO: MoviesDAO) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

    Navigation(moviesDAO)
}

