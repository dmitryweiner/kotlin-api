package com.example.myapplication

import retrofit2.http.*

interface APIServiceInterface {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): User?

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post?

    @GET("posts")
    suspend fun getPostsByUserId(@Query("userId") userId: Int): List<Post>
}