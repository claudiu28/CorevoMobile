package com.corevo.main.data.network

import com.corevo.main.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface SocialApi {
    @GET("posts/feed")
    suspend fun getFeed(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<PageType<Post>>

    @POST("posts")
    suspend fun createPost(@Body description: Map<String, String>): Response<Post>

    @POST("posts/{postId}/like")
    suspend fun likePost(@Path("postId") postId: Long): Response<Unit>

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId: Long): Response<List<Comment>>

    @POST("posts/{postId}/comments")
    suspend fun addComment(
        @Path("postId") postId: Long,
        @Body request: Map<String, String>
    ): Response<Comment>
}
