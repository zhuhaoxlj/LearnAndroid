package com.example.learnandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.learnandroid.problem.GlideProblemActivity
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme

@Composable
fun LearnScreen() {
    val context = LocalContext.current
    
    Column {
        FakeStatusBar(color = LearnAndroidTheme.themeColors.listItem)
        TitleBar(
            title = "学习",
            backgroundColor = LearnAndroidTheme.themeColors.listItem,
            titleColor = LearnAndroidTheme.themeColors.textPrimary
        )
        
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    DemoCard(
                        title = "聊天界面 Demo",
                        description = "使用 Jetpack Compose 实现的聊天界面示例",
                        onClick = { ChatDemoActivity.start(context) }
                    )
                }

                item {
                    DemoCard(
                        title = "Glide 问题演示",
                        description = "展示和解决 Glide 加载图片时的常见问题",
                        onClick = { GlideProblemActivity.start(context) }
                    )
                }
            }
        }
    }
}

@Composable
fun DemoCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
} 