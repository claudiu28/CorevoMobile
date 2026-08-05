package com.corevo.main.data.model

data class PageType<T>(
    val content: List<T> = emptyList(),
    val totalPages: Int = 0,
    val totalElements: Long = 0,
    val last: Boolean = true,
    val first: Boolean = true,
    val size: Int = 0,
    val number: Int = 0,
    val empty: Boolean = true
)
