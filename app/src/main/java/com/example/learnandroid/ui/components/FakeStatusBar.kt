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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.example.learnandroid.utils.pxToDp

/**
 * 虚拟状态栏
 *
 * @author zhuhao
 * @date  14:35
 **/

@Composable
fun FakeStatusBar(color: Color? = null) {
    val isPreviewMode = LocalInspectionMode.current
    val statusBarHeight = if (isPreviewMode) {
        32.dp // 预览模式下固定为32dp
    } else {
        WindowInsets.statusBars.getTop(LocalDensity.current).pxToDp()
    }
    Spacer(
        modifier = Modifier
            .background(color ?: Color.Transparent)
            .height(statusBarHeight)
            .fillMaxWidth()
    )
}