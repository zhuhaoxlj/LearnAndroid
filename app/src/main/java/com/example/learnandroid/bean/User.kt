package com.example.learnandroid.bean

import androidx.annotation.DrawableRes
import com.example.learnandroid.R

class User(
    val id: String,
    val name: String,
    @DrawableRes val avatar: Int
) {
    companion object {
        val Me: User = User("markgosling", "feizhu-朱浩", R.drawable.ic_launcher_foreground)
    }
}