package com.example.breakingnews.ui.composeviews

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.breakingnews.ui.SourcesViewModel
import com.example.breakingnews.ui.model.Article
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsList(source: String, viewModel: SourcesViewModel, navController: NavController) {
    LaunchedEffect(key1 = source) {
        viewModel.getNews(source)
    }
    val news by viewModel.news.collectAsStateWithLifecycle()
    val savedArticles by viewModel.savedArticles.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "News List",
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        when {
            news?.isLoading == true -> {
                LoadingView()
            }

            news?.newstems?.articles?.isNotEmpty() == true -> {
                val firstThreeArticles = news?.newstems?.articles.orEmpty().take(3)
                val remainingArticles = news?.newstems?.articles.orEmpty().drop(3)
                Column {
                    Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 2.dp)
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            NewsViewPager(
                                articles = firstThreeArticles,
                                savedArticles = savedArticles,
                                onSaved = {
                                    viewModel.saveArticle(it)
                                },
                                onUnsaved = {
                                    viewModel.unSaveArticle(it)
                                }
                            )
                        }

                        items(remainingArticles) { article ->
                            article?.let {
                                val isSaved = article in savedArticles
                                NewsItemView(
                                    article = article,
                                    isSavedBookmark = isSaved,
                                    onSaved = {
                                        viewModel.saveArticle(it)
                                    },
                                    onUnSaved = {
                                        viewModel.unSaveArticle(it)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            else -> {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun NewsItemView(
    article: Article,
    isSavedBookmark: Boolean,
    onSaved: (Article) -> Unit,
    onUnSaved: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            model = article.urlToImage,
            contentDescription = "News Banner Image",
            loading = {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            modifier = Modifier.padding(6.dp),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            text = article.description.toString()
        )
        Text(
            modifier = Modifier.padding(6.dp),
            fontSize = 16.sp,
            color = Color.Black,
            text = article.publishedAt.toString()
        )
        Text(
            modifier = Modifier
                .padding(6.dp)
                .clickable {
                    if (isSavedBookmark) onUnSaved(article) else onSaved(article)
                },
            fontSize = 16.sp,
            color = Color.Blue,
            text = if (isSavedBookmark) "Listemden Çıkar" else "Listeme Kaydet"
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp)
    }
}
