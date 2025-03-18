package com.example.learnandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.example.learnandroid.utils.pxToDp

/**
 * 虚拟状态栏
 *
 * @author zhuhao
 * @date  14:35
 **/

@Composable
fun FakeStatusBar(color: Color? = null) {
    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current).pxToDp()
    Spacer(
        modifier = Modifier
            .background(color ?: Color.Transparent)
            .height(statusBarHeight)
            .fillMaxWidth()
    )
}