package com.corevo.main.data.network

import com.corevo.main.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ChatApi {
    @GET("chat/conversations")
    suspend fun getConversations(): Response<List<Conversation>>

    @GET("chat/conversations/{id}/messages")
    suspend fun getMessages(
        @Path("id") conversationId: Long,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): Response<PageType<ChatMessage>>

    @POST("chat/conversations/{id}/messages")
    suspend fun sendMessage(
        @Path("id") conversationId: Long,
        @Body request: CreateMessageRequest
    ): Response<ChatMessage>

    @POST("chat/conversations")
    suspend fun createConversation(@Body request: CreateChatRequest): Response<Conversation>
}
