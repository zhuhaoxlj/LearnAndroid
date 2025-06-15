package com.example.learnandroid.net

import com.example.learnandroid.bean.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author zhuhao
 * @date  15:45
 **/
interface GithubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>>
}