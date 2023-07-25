package com.example.breakingnews.ui.composeviews

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.breakingnews.base.shimmerBackground
import com.example.breakingnews.ui.SourcesViewModel
import com.example.breakingnews.ui.model.SourceItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourcesList(viewModel: SourcesViewModel = hiltViewModel()) {
    val sources by viewModel.sources.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    when {
        sources?.isLoading == true -> {
            LoadingView()
        }
        sources?.sourceItems?.sources?.isNotEmpty() == true -> {
            Column {
                LazyColumn {
                    items(sources?.sourceItems?.sources.orEmpty()) { source ->
                        SourceItemView(source = source)
                    }
                }
            }
        }
        else -> {
            Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
                LaunchedEffect(key1 = sources?.errorMessage) {
                    scope.launch {
                        if (sources?.errorMessage.isNullOrEmpty().not()) {
                            snackBarHostState.showSnackbar(sources?.errorMessage.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SourceItemView(source: SourceItem) {
    Column {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            text = source.name.toString()
        )
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 16.sp,
            color = Color.Black,
            text = source.description.toString()
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp)
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
