package com.example.learnandroid.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.learnandroid.ui.theme.LearnAndroidTheme

/**
 * @author zhuhao
 * @date  15:16
 **/
class ChatPageActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChatPageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            LearnAndroidTheme {
                ChatPage()
            }
        }
    }
}

@Composable
fun ChatPage(modifier: Modifier = Modifier) {

}