package com.example.learnandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroid.databinding.ActivityViewTouchBinding
import com.example.learnandroid.utils.ToastUtils

/**
 * 自定义触摸反馈
 *
 * @author zhuhao
 * @date  22:09
 **/
@MyBindView
class ViewTouchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewTouchBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ViewTouchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTouchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.view.setOnClickListener {
            ToastUtils.Companion.toast(this, "点击")
        }
    }
}