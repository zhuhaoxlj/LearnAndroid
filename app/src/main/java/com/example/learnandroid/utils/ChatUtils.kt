package com.example.learnandroid.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 聊天相关的工具
 *
 * @author zhuhao
 * @date  23:40
 **/
@Composable
fun Modifier.unRead(show: Boolean, color: Color): Modifier =
    this.drawWithContent {
            drawContent()
            if (show) {
                drawCircle(color, 5.dp.toPx(), Offset(size.width - 1.dp.toPx(), 1.dp.toPx()))
            }
        }
