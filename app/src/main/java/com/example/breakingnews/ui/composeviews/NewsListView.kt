package com.example.breakingnews.ui.composeviews

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.breakingnews.ui.SourcesViewModel
import com.example.breakingnews.ui.model.Article
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsList(source: String, viewModel: SourcesViewModel) {
    LaunchedEffect(key1 = source) {
        viewModel.getNews(source)
    }
    val news by viewModel.news.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    when {
        news?.isLoading == true -> {
            LoadingView()
        }

        news?.newstems?.articles?.isNotEmpty() == true -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(news?.newstems?.articles.orEmpty()) { article ->
                    article?.let {
                        NewsItemView(article = article)
                    }
                }
            }
        }

        else -> {
            Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
                LaunchedEffect(key1 = news?.errorMessage) {
                    scope.launch {
                        if (news?.errorMessage.isNullOrEmpty().not()) {
                            snackBarHostState.showSnackbar(news?.errorMessage.toString())
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun NewsItemView(article: Article) {
    Column {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = "News Banner Image",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
