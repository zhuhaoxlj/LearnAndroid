package com.example.learnandroid.net

import androidx.lifecycle.ViewModel
import java.util.LinkedList

/**
 *
 * @author zhuhao
 * @date  10:16
 **/
abstract class BaseViewModel : ViewModel() {
    private var requests: LinkedList<RequestAdapter<*>> = LinkedList()

    /**
     * 添加请求
     */
    protected fun managerRequest(request: RequestAdapter<*>?) {
        if (request != null) {
            request.request()
            requests.add(request)
        }
    }

    /**
     * 取消所有网络请求
     */
    fun cancelAllRequest() {
        if (requests.isNotEmpty()) {
            for (request in requests) {
                request.cancel()
            }
            requests.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequest()
    }

}