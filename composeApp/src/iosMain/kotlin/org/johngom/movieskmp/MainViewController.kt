package org.johngom.movieskmp

import androidx.compose.ui.window.ComposeUIViewController
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.johngom.movieskmp.data.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val database = getDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .build()
    App(database.moviesDAO())
}