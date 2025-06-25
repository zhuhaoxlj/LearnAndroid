package com.example.learnandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroid.databinding.ActivityDrawBinding

/**
 * @author zhuhao zhuhao084@gmail.com
 **/
class DrawActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawBinding

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DrawActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}