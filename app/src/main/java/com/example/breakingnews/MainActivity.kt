package com.example.breakingnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.breakingnews.ui.SourcesViewModel
import com.example.breakingnews.ui.composeviews.CategoryListView
import com.example.breakingnews.ui.composeviews.SourcesList
import com.example.breakingnews.ui.theme.BreakingNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SourcesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreakingNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val categories = viewModel.categoryList.collectAsStateWithLifecycle()
                    Column {
                        CategoryListView(
                            items = categories.value.orEmpty(),
                            onItemSelected = { category ->
                                viewModel.getSources(category)
                            })
                        Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 2.dp)
                        SourcesList()
                    }
                }
            }
        }
    }
}
