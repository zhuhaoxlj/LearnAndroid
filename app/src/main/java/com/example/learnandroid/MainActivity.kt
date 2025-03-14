package com.example.learnandroid

import com.example.learnandroid.problem.GlideProblemActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.learnandroid.databinding.ActivityMainBinding
import com.example.learnandroid.rwx.ViewTouchActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.rbGlideProblem.setOnClickListener {
            GlideProblemActivity.start(this)
        }
        binding.rbCustomTouch.setOnClickListener {
            ViewTouchActivity.start(this)
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun NewsStory() {
        Column(
        ) { //  添加Column，使布局垂直排列
            Text("JetPack Compose ♥️")
            Text("Android")
            Text("依然范特西")
        }
    }
}

