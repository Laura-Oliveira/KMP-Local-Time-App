package com.kmp.time

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.kmp.time.view.Time

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        TimeWeb()
    }
}