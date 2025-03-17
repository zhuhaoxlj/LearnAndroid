package com.example.learnandroid.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.learnandroid.R
import com.example.learnandroid.bean.Chat
import com.example.learnandroid.bean.Msg
import com.example.learnandroid.bean.User

/**
 * @author zhuhao
 * @date  21:50
 **/
class LearnAndroidVM : ViewModel() {
    var selectedTab by mutableIntStateOf(0)
    var chats by mutableStateOf(
        listOf(
            // List<Chat>
            Chat(
                friend = User("gaolaoshi", "猪猪", R.drawable.avatar_gaolaoshi),
                mutableStateListOf(
                    Msg(User("gaolaoshi", "猪猪", R.drawable.avatar_gaolaoshi), "锄禾日当午", "14:20"),
                    Msg(User.Me, "汗滴禾下土", "14:20"),
                    Msg(User("gaolaoshi", "猪猪", R.drawable.avatar_gaolaoshi), "谁知盘中餐", "14:20"),
                    Msg(User.Me, "粒粒皆辛苦", "14:20"),
                    Msg(
                        User("gaolaoshi", "猪猪", R.drawable.avatar_gaolaoshi),
                        "唧唧复唧唧，木兰当户织。不闻机杼声，惟闻女叹息。",
                        "14:20"
                    ),
                    Msg(User.Me, "双兔傍地走，安能辨我是雄雌？", "14:20"),
                    Msg(User("gaolaoshi", "猪猪", R.drawable.avatar_gaolaoshi), "床前明月光，疑是地上霜。", "14:20"),
                    Msg(User.Me, "吃饭吧？", "14:20"),
                )
            ),
            Chat(
                friend = User("diuwuxian", "菲菲", R.drawable.avatar_diuwuxian),
                mutableStateListOf(
                    Msg(User("diuwuxian", "菲菲", R.drawable.avatar_diuwuxian), "哈哈哈", "13:48"),
                    Msg(User.Me, "哈哈昂", "13:48"),
                    Msg(User("diuwuxian", "菲菲", R.drawable.avatar_diuwuxian), "你笑个屁呀", "13:48").apply {
                        read = false
                    },
                )
            ),
        )
    )
}