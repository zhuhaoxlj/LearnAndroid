package com.example.learnandroid.ui.screens

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroid.databinding.ActivityLearnFragmentBinding

/**
 *
 * @author zhuhao
 * @date  10:01
 **/
class LearnFragmentActivity : AppCompatActivity() {
    private var binding: ActivityLearnFragmentBinding? = null
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        binding = ActivityLearnFragmentBinding.inflate(layoutInflater)
        return super.onCreateView(name, context, attrs)
        initView()
    }

    private fun initView() {
    }
}