package com.example.todoapp.domain.repository

import com.example.todoapp.data.datasource.articleList
import com.example.todoapp.domain.model.ArticleData

interface FetchArticleInterface {
    suspend fun fetchArticleList(): List<ArticleData> = articleList
}