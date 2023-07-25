package com.example.breakingnews.ui.composeviews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.breakingnews.ui.model.Article
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewsViewPager(articles: List<Article?>) {
    Column {
        HorizontalIndicators(articles)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalIndicators(articles: List<Article?>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        HorizontalPager(pageCount = articles.size, state = pagerState, modifier = Modifier
            .pointerInput(Unit) {
                coroutineScope.launch {
                    while (true) {
                        delay(5000)
                        pagerState.animateScrollToPage(
                            (pagerState.currentPage + 1) % articles.size
                        )
                    }
                }
            }) {
            articles[it]?.let { article ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NewsItemView(article = article)
                }
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp),
            pageCount = articles.size,
            pagerState = pagerState,
        )
    }
}
