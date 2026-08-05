package com.corevo.main.repo

import com.corevo.main.data.model.*
import com.corevo.main.data.network.ChatApi
import com.corevo.main.data.network.SocialApi

class SocialRepository(
    private val socialApi: SocialApi,
    private val chatApi: ChatApi
) {
    suspend fun getFeed(page: Int = 0): Result<List<Post>> {
        return try {
            val res = socialApi.getFeed(page = page)
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!.content)
            else Result.failure(Exception("Failed to load social feed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPost(description: String): Result<Post> {
        return try {
            val res = socialApi.createPost(mapOf("description" to description))
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to post"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun likePost(postId: Long): Result<Unit> {
        return try {
            val res = socialApi.likePost(postId)
            if (res.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Failed to like post"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversations(): Result<List<Conversation>> {
        return try {
            val res = chatApi.getConversations()
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to fetch chat conversations"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(conversationId: Long): Result<List<ChatMessage>> {
        return try {
            val res = chatApi.getMessages(conversationId)
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!.content)
            else Result.failure(Exception("Failed to load messages"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendMessage(conversationId: Long, text: String): Result<ChatMessage> {
        return try {
            val res = chatApi.sendMessage(conversationId, CreateMessageRequest(text))
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to send message"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
