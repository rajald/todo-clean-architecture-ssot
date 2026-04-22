package com.example.todoapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.network.client
import com.example.todoapp.domain.model.ArticleData
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ArticleViewModel: ViewModel() {

    private var _articleList = MutableStateFlow<List<ArticleData>>(emptyList())
    val articleList: StateFlow<List<ArticleData>> = _articleList
    val sharedFlow = MutableSharedFlow<Int>()
    val stateFlow = MutableStateFlow<Int>(0)

    private var _bitmaps = MutableStateFlow<Map<Int, ImageBitmap>>(emptyMap())
    val bitmaps: StateFlow<Map<Int, ImageBitmap>> = _bitmaps

    init {
        fetchArticleList()
    }

    fun fetchArticleList() {
        viewModelScope.launch {
            try {
                val response = client.get("https://jsonplaceholder.typicode.com/photos").body<List<ArticleData>>()
                _articleList.value = response

                response.forEach { article ->
                    loadBitmap(article)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun doLongRunningTaskOne(): Flow<String> {
        return flow {
            delay(5000)
            emit("One")
        }
    }

    private fun doLongRunningTaskTwo(): Flow<String> {
        return flow {
            delay(5000)
            emit("Two")
        }
    }

    private fun startLongRunningTasks() {
        viewModelScope.launch {
            doLongRunningTaskOne().zip(doLongRunningTaskTwo()) { resultOne, resultTwo -> {
                resultOne + resultTwo
            }.asFlow().flowOn(Dispatchers.Default).catch {  }.collect { it }

            }
        }
    }

    private fun getMoreUser() {
        TODO("Not yet implemented")
    }

    private fun getUsers() {
        TODO("Not yet implemented")
    }

    fun loadBitmap(articleData: ArticleData) {
        if(_bitmaps.value.containsKey(articleData.id)) return

        viewModelScope.launch(Dispatchers.IO) {
            /*val fileName = "post_img_${articleData.id}.png"

            val cached = getCachedBitmap(context, fileName)
            if(cached != null) {
                updateBitmapState(articleData.id, cached.asImageBitmap())
                return@launch
            }*/
            try {
                val bytes = client.get (articleData.url).body<ByteArray>()
                //saveBitmapToCache(context, fileName, bytes)

                val imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
                updateBitmapState(articleData.id, imageBitmap)
            } catch (e: Exception) {

            }
        }
    }

    fun updateBitmapState(id: Int, imageBitmap: ImageBitmap) {
        _bitmaps.update { currentMap ->
            val newMap = currentMap.toMutableMap()
            newMap[id] = imageBitmap
            newMap
        }
    }


}