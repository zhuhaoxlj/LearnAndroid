package com.example.learnandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learnandroid.bean.Chat
import com.example.learnandroid.ui.theme.LearnAndroidTheme
import com.example.learnandroid.utils.unRead
import com.example.learnandroid.vm.LearnAndroidVM

/**
 * @author zhuhao
 * @date  23:13
 **/
@Composable
fun ChatList(chatList: List<Chat>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LearnAndroidTheme.themeColors.background)
    ) {
        Box(Modifier.background(LearnAndroidTheme.themeColors.listItem)) {
            LazyColumn {
                items(chatList.size) { index ->
                    val chat = chatList[index]
                    ChatItem(chat)
                    if (index != chatList.size - 1) {
                        HorizontalDivider(
                            thickness = 0.5.dp,
                            color = LearnAndroidTheme.themeColors.chatListDivider,
                            modifier = Modifier.padding(start = 60.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Image(
            painter = painterResource(chat.friend.avatar),
            contentDescription = "头像",
            modifier = Modifier
                .padding(4.dp)
                .unRead(!chat.msgs.last().read, LearnAndroidTheme.themeColors.badge)
                .clip(RoundedCornerShape(4.dp))
                .size(48.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(48.dp)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = chat.friend.name)
            Text(text = chat.msgs.last().text, modifier = Modifier.padding(bottom = 2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListPreview() {
    val viewModel = LearnAndroidVM()
    ChatList(viewModel.chats)
}

@Preview(showBackground = true)
@Composable
private fun ChatItemPreview() {
    val viewModel = LearnAndroidVM()
    ChatItem(viewModel.chats[1])
}