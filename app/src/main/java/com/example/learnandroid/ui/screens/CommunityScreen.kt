package com.example.learnandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learnandroid.R
import com.example.learnandroid.ui.components.FakeStatusBar
import com.example.learnandroid.ui.components.TitleBar
import com.example.learnandroid.ui.theme.LearnAndroidTheme

@Composable
fun CommunityScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Immersive status bar with different color for this screen
        FakeStatusBar(color = Color(0xFF3F51B5))
        
        // Title bar with different color
        TitleBar(
            title = "社区",
            backgroundColor = Color(0xFF3F51B5),
            titleColor = Color.White
        )
        
        // Community content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(5) { index ->
                CommunityPost(
                    username = "Android 开发者 ${index + 1}",
                    timePosted = "${index + 1}小时前",
                    content = "这是一个关于Android开发的社区讨论帖子示例。这里可以分享您的Android开发经验和问题。"
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CommunityPost(
    username: String,
    timePosted: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // User info
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar and username
                Box(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = username,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 50.dp)
                    )
                }
                
                // Time posted
                Text(
                    text = timePosted,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            
            // Post content
            Text(
                text = content,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    LearnAndroidTheme {
        CommunityScreen()
    }
} 