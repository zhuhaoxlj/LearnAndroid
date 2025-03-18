package com.example.learnandroid.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 将像素转换为dp
 */
@Composable
fun Int.pxToDp(): Dp {
    return with(LocalDensity.current) { this@pxToDp.toDp() }
} 