package com.example.learnandroid.net

/**
 * @author zhuhao
 * @date  17:49
 **/
import retrofit2.http.GET

interface ApiService {
    @GET("api/wish")
    suspend fun getWishData(): List<String>
}
