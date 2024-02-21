package com.example.learnandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initView()
    }
    private fun initView(){
        with(binding){
            tvHi.text
        }
    }
}