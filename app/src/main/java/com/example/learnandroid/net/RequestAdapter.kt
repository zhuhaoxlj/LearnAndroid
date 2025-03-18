package com.example.learnandroid.net

/**
 * @author zhuhao
 * @date  10:16
 **/
abstract class RequestAdapter<T> {

    var mRequest: T? = null

    fun request() {
        mRequest = startRequest()
    }

    fun cancel() {
        cancelRequest(mRequest)
    }

    protected abstract fun startRequest(): T

    protected abstract fun cancelRequest(request: T?)

}
