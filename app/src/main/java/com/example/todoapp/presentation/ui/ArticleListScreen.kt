package com.example.todoapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.todoapp.R
import com.example.todoapp.domain.model.ArticleData
import com.example.todoapp.domain.repository.FetchArticleInterface
import com.example.todoapp.presentation.viewmodel.ArticleViewModel

@Composable
fun ArticleListScreen(
    modifier: Modifier,
    fetchArticleInterface: FetchArticleInterface = object : FetchArticleInterface {}
) {
    var articleList by remember { mutableStateOf<List<ArticleData>>(emptyList()) }
    val articleListState by produceState(initialValue = emptyList(), fetchArticleInterface) {
        value = fetchArticleInterface.fetchArticleList()
    }
    val articleViewModel: ArticleViewModel = viewModel()
    val articleApiList by articleViewModel.articleList.collectAsStateWithLifecycle()

    val bitmaps by articleViewModel.bitmaps.collectAsStateWithLifecycle()


    LaunchedEffect(fetchArticleInterface) {
        articleList = fetchArticleInterface.fetchArticleList()
    }



    Surface(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(articleApiList) { article ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = MaterialTheme.shapes.extraSmall,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = article.thumbnailUrl,
                            contentDescription = "Image",
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.ic_launcher_background)
                        )

                        bitmaps[article.id]?.let { bitmap ->
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp).padding(horizontal = 12.dp)
                            )
                        } ?: Box(modifier = Modifier.size(80.dp).padding(horizontal = 12.dp).background(Color.Gray))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = article.title,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                            /*Text(
                                text = article.body,
                                style = MaterialTheme.typography.bodySmall
                            )*/
                        }
                    }
                }

            }
        }
    }
}