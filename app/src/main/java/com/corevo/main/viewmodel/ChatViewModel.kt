package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corevo.main.data.model.ChatMessage
import com.corevo.main.data.model.Conversation
import com.corevo.main.repo.SocialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val socialRepository: SocialRepository) : ViewModel() {
    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations

    private val _activeMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val activeMessages: StateFlow<List<ChatMessage>> = _activeMessages

    val currentConversationId = MutableStateFlow<Long?>(null)
    val inputMessage = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)

    init {
        loadConversations()
    }

    fun loadConversations() {
        viewModelScope.launch {
            isLoading.value = true
            val res = socialRepository.getConversations()
            if (res.isSuccess) _conversations.value = res.getOrNull() ?: emptyList()
            isLoading.value = false
        }
    }

    fun selectConversation(conversationId: Long) {
        currentConversationId.value = conversationId
        loadMessages(conversationId)
    }

    fun loadMessages(conversationId: Long) {
        viewModelScope.launch {
            isLoading.value = true
            val res = socialRepository.getMessages(conversationId)
            if (res.isSuccess) _activeMessages.value = res.getOrNull() ?: emptyList()
            isLoading.value = false
        }
    }

    fun sendMessage() {
        val cid = currentConversationId.value ?: return
        val text = inputMessage.value
        if (text.isBlank()) return

        viewModelScope.launch {
            inputMessage.value = ""
            val res = socialRepository.sendMessage(cid, text)
            if (res.isSuccess) {
                loadMessages(cid)
            }
        }
    }
}
