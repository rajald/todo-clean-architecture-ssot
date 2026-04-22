package com.example.todoapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleData(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)