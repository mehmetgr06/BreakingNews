package com.example.breakingnews.ui.composeviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.breakingnews.base.shimmerBackground
import com.example.breakingnews.ui.SourcesViewModel
import com.example.breakingnews.ui.model.SourceItem

@Composable
fun SourcesList(modifier: Modifier = Modifier, viewModel: SourcesViewModel = hiltViewModel()) {

    val sources by viewModel.sources.collectAsStateWithLifecycle()
    if (sources?.isLoading == true) {
        LoadingView()
    } else {
        LazyColumn(modifier = modifier) {
            items(sources?.sourceItems.orEmpty()) { source ->
                SourceItemView(modifier = modifier, source = source)
            }
        }
    }
}

@Composable
fun SourceItemView(modifier: Modifier, source: SourceItem) {
    Column {
        Text(text = source.name.toString())
        Spacer(modifier = modifier.height(4.dp))
        Text(text = source.description.toString())
        Divider(modifier = modifier.padding(vertical = 4.dp), thickness = 2.dp)
    }
}

@Composable
fun LoadingView() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shimmerBackground()
    )
}
