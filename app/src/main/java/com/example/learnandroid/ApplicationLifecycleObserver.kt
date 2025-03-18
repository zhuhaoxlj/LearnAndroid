package com.example.learnandroid

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Application生命周期观察，提供整个应用进程的生命周期
 *
 * Lifecycle.Event.ON_CREATE只会分发一次，Lifecycle.Event.ON_DESTROY不会被分发。
 *
 * 第一个Activity进入时，ProcessLifecycleOwner将分派Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME。
 * 而Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP，将在最后一个 Activity 退出后后延迟分发。如果由于配置更改而销毁并重新创建活动，则此延迟足以保证ProcessLifecycleOwner不会发送任何事件。
 *
 * 作用：监听应用程序进入前台或后台
 */
class ApplicationLifecycleObserver : LifecycleEventObserver {
    val TAG = "ApplicationLifecycleObserver"

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> Log.w(TAG, "ApplicationObserver: app moved to foreground")
            Lifecycle.Event.ON_STOP -> Log.w(TAG, "ApplicationObserver: app moved to background")
            else -> {}
        }
    }
}
