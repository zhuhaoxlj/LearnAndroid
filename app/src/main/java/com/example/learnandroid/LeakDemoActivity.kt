package com.example.learnandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * 内存泄漏演示Activity
 * 此Activity包含多个常见的内存泄漏场景，用于测试LeakCanary的检测能力
 */
class LeakDemoActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "LeakDemoActivity"
        
        // 静态变量持有Activity引用会导致内存泄漏
        private var leakedContext: Context? = null
        
        fun start(context: Context) {
            val intent = Intent(context, LeakDemoActivity::class.java)
            context.startActivity(intent)
        }
    }
    
    // 内部类引用（可能导致内存泄漏）
    private lateinit var leakyRunnable: Runnable
    
    // Handler（可能导致内存泄漏）
    private val handler = Handler(Looper.getMainLooper())
    
    // 单例对象持有Activity引用（导致内存泄漏）
    private val leakySingleton = LeakySingleton.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_demo)
        
        // 示例1：静态变量持有Activity引用
        val btnLeakStatic = findViewById<Button>(R.id.btn_leak_static)
        btnLeakStatic.setOnClickListener {
            // 故意将Activity存储在静态变量中
            leakedContext = this
            showMessage("静态变量已持有Activity引用，这会导致内存泄漏")
        }
        
        // 示例2：匿名内部类持有外部类引用
        val btnLeakThread = findViewById<Button>(R.id.btn_leak_thread)
        btnLeakThread.setOnClickListener {
            startLeakyThread()
            showMessage("已启动具有内存泄漏的线程")
        }
        
        // 示例3：Handler泄漏
        val btnLeakHandler = findViewById<Button>(R.id.btn_leak_handler)
        btnLeakHandler.setOnClickListener {
            postLeakyHandler()
            showMessage("已发送延迟消息到Handler，这可能导致内存泄漏")
        }
        
        // 示例4：单例模式导致的内存泄漏
        val btnLeakSingleton = findViewById<Button>(R.id.btn_leak_singleton)
        btnLeakSingleton.setOnClickListener {
            leakySingleton.setActivity(this)
            showMessage("Activity引用已存储在单例对象中，这会导致内存泄漏")
        }
        
        // 关闭按钮
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }
    }
    
    private fun startLeakyThread() {
        // 创建一个持有外部类引用的匿名内部类
        leakyRunnable = Runnable {
            while (true) {
                try {
                    Log.d(TAG, "Leaky thread is running...")
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    break
                }
            }
        }
        
        // 启动线程
        Thread(leakyRunnable).start()
    }
    
    private fun postLeakyHandler() {
        // 发送一个在Activity销毁后才会执行的延迟消息
        handler.postDelayed({
            // 在Activity销毁后访问Activity的引用会导致内存泄漏
            Log.d(TAG, "Activity state: ${this.isDestroyed}")
        }, 60000) // 60秒后执行
    }
    
    private fun showMessage(message: String) {
        val tvMessage = findViewById<TextView>(R.id.tv_message)
        tvMessage.text = message
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
        // 注意：这里故意不释放资源以演示内存泄漏
    }
    
    // 单例类（用于演示内存泄漏）
    class LeakySingleton private constructor() {
        private var activityRef: AppCompatActivity? = null
        
        companion object {
            private var instance: LeakySingleton? = null
            
            fun getInstance(): LeakySingleton {
                if (instance == null) {
                    instance = LeakySingleton()
                }
                return instance!!
            }
        }
        
        fun setActivity(activity: AppCompatActivity) {
            this.activityRef = activity
        }
    }
} 