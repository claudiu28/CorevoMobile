package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corevo.main.data.model.Post
import com.corevo.main.repo.SocialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val socialRepository: SocialRepository) : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val newPostDescription = MutableStateFlow("")

    init {
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _isLoading.value = true
            val res = socialRepository.getFeed()
            if (res.isSuccess) {
                _posts.value = res.getOrNull() ?: emptyList()
            }
            _isLoading.value = false
        }
    }

    fun createPost(onSuccess: () -> Unit) {
        if (newPostDescription.value.isBlank()) return
        viewModelScope.launch {
            _isLoading.value = true
            val res = socialRepository.createPost(newPostDescription.value)
            if (res.isSuccess) {
                newPostDescription.value = ""
                loadFeed()
                onSuccess()
            }
            _isLoading.value = false
        }
    }

    fun likePost(postId: Long) {
        viewModelScope.launch {
            socialRepository.likePost(postId)
            _posts.value = _posts.value.map {
                if (it.postId == postId) it.copy(likesCount = it.likesCount + 1, isLiked = true)
                else it
            }
        }
    }
}
