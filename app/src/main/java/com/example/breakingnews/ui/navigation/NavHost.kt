package com.example.breakingnews.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.breakingnews.ui.composeviews.NewsList
import com.example.breakingnews.ui.composeviews.SourcesList
import androidx.navigation.compose.composable
import com.example.breakingnews.ui.mainscreen.SourcesViewModel
import com.example.breakingnews.ui.composeviews.CategoryListView

@Composable
fun BreakingNewsNavHost(
    navController: NavHostController,
    viewModel: SourcesViewModel
) {
    val categories = viewModel.categoryList.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = AppDestinations.SOURCES_LIST
    ) {
        composable(route = AppDestinations.SOURCES_LIST) {
            Column {
                CategoryListView(
                    items = categories.value.orEmpty(),
                    onItemSelected = { category ->
                        viewModel.getSources(category)
                    })
                Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 2.dp)
                SourcesList(navController, viewModel)
            }
        }
        composable(
            route = "${AppDestinations.NEWS_LIST}/{source}",
            arguments = listOf(navArgument("source") { type = NavType.StringType })
        ) { backStackEntry ->
            val source = backStackEntry.arguments?.getString("source")
            NewsList(source.orEmpty(), viewModel, navController)
        }
    }
}
