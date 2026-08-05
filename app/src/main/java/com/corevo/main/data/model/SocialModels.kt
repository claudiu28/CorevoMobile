package com.corevo.main.data.model

data class MediaItem(
    val id: Long,
    val mediaURL: String,
    val contentType: String = "IMAGE",
    val order: Int = 0
)

data class Post(
    val postId: Long,
    val username: String,
    val profilePicture: String? = null,
    val description: String? = null,
    val mediaList: List<MediaItem> = emptyList(),
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLiked: Boolean = false,
    val createdAt: String? = null
)

data class Comment(
    val commentId: Long,
    val commentText: String,
    val username: String,
    val profilePicture: String? = null
)

data class Notification(
    val notificationId: Long,
    val username: String,
    val profilePicture: String? = null,
    val text: String,
    val type: String,
    val createdAt: String? = null,
    val seen: Boolean = false
)

data class ChatMessage(
    val messageId: Long,
    val conversationId: Long,
    val senderUsername: String,
    val senderImage: String? = null,
    val text: String,
    val sentAt: String? = null,
    val isMine: Boolean = false,
    val isRead: Boolean = false
)

data class MessagePreview(
    val messageId: Long,
    val text: String,
    val senderUsername: String,
    val sentAt: String? = null,
    val isRead: Boolean = false
)

data class Conversation(
    val id: Long,
    val name: String,
    val otherProfilePicture: String? = null,
    val lastMessage: MessagePreview? = null,
    val lastActivity: String? = null,
    val hasUnread: Boolean = false
)

data class CreateChatRequest(val username: String)
data class CreateMessageRequest(val message: String)
