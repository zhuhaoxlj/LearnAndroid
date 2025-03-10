package com.example.learnandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroid.databinding.ActivityMainBinding
import com.example.learnandroid.problem.GlideProblemActivity

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
            GlideProblemActivity.startGlideProblemActivity(this)
        }
    }

}