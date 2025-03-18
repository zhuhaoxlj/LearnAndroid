package com.example.learnandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme

@Composable
fun CommunityScreen() {
    Column {
        FakeStatusBar(color = LearnAndroidTheme.themeColors.listItem)
        TitleBar(
            title = "社区",
            backgroundColor = LearnAndroidTheme.themeColors.listItem,
            titleColor = LearnAndroidTheme.themeColors.textPrimary
        )
        
        Text(
            text = "社区功能开发中...",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
} 