package com.example.breakingnews

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.breakingnews.ui.composeviews.NewsViewPager
import com.example.breakingnews.ui.model.Article
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsViewPagerScreenTest {

    private val fakeArticles: List<Article?> = listOf(
        Article(
            "Author 1",
            "Content 1",
            "Description 1",
            "2023-07-28T12:34:56Z",
            "Title 1",
            "https://example.com/1",
            "https://example.com/image1.jpg"
        ),
        Article(
            "Author 2",
            "Content 2",
            "Description 2",
            "2023-07-28T12:34:57Z",
            "Title 2",
            "https://example.com/2",
            "https://example.com/image2.jpg"
        ),
    )

    private val fakeSavedArticles: MutableList<Article> = mutableListOf(
        Article(
            "Author 1",
            "Content 1",
            "Description 1",
            "2023-07-28T12:34:56Z",
            "Title 1",
            "https://example.com/1",
            "https://example.com/image1.jpg"
        ),
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNewsViewPager() {
        composeTestRule.setContent {
            NewsViewPager(
                articles = fakeArticles,
                savedArticles = fakeSavedArticles,
                onSaved = {},
                onUnsaved = {}
            )
        }

        composeTestRule.onNodeWithTag("HorizontalPagerTag").assertIsDisplayed()
    }
}
