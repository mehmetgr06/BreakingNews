package com.example.breakingnews.ui.mainscreen

import com.example.breakingnews.common.Result
import com.example.breakingnews.domain.usecase.GetNewsUseCase
import com.example.breakingnews.domain.usecase.GetSavedArticlesUseCase
import com.example.breakingnews.domain.usecase.GetSourcesUseCase
import com.example.breakingnews.domain.usecase.SaveArticleUseCase
import com.example.breakingnews.domain.usecase.UnSaveArticleUseCase
import com.example.breakingnews.ui.model.Article
import com.example.breakingnews.ui.model.News
import com.example.breakingnews.ui.model.NewsState
import com.example.breakingnews.ui.model.Source
import com.example.breakingnews.ui.model.SourceItem
import com.example.breakingnews.ui.model.SourcesState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SourcesViewModelTest {

    @MockK
    val getSourcesUseCase = mockk<GetSourcesUseCase>(relaxed = true)

    @MockK
    val getNewsUseCase = mockk<GetNewsUseCase>(relaxed = true)

    @MockK
    val getSavedArticlesUseCase = mockk<GetSavedArticlesUseCase>(relaxed = true)

    @MockK
    val saveArticleUseCase = mockk<SaveArticleUseCase>(relaxed = true)

    @MockK
    val unSaveArticleUseCase = mockk<UnSaveArticleUseCase>(relaxed = true)

    private lateinit var viewModel: SourcesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = SourcesViewModel(
            getSourcesUseCase,
            getNewsUseCase,
            getSavedArticlesUseCase,
            saveArticleUseCase,
            unSaveArticleUseCase
        )
    }

    @Test
    fun `test getSources success`() = runBlocking {
        val category = "technology"
        val expectedSources = Source(
            sources = listOf(
                SourceItem(
                    id = "1",
                    name = "Tech News",
                    category = category,
                    country = "US",
                    description = "Tech news source",
                    language = "en",
                    url = "https://technews.com"
                )
            ),
            status = "ok",
            categoryList = listOf("technology", "business", "science")
        )
        val successResult = Result.Success<Source?>(expectedSources)

        coEvery { getSourcesUseCase.invoke(category) } returns flowOf(successResult)

        viewModel.getSources(category)

        val sourcesState = viewModel.sources.value
        assertTrue(sourcesState is SourcesState)
        assertEquals(expectedSources, sourcesState?.sourceItems)
    }

    @Test
    fun `test getSources loading`() = runBlocking {
        val category = "technology"
        val loadingResult = Result.Loading<Source?>()

        coEvery { getSourcesUseCase.invoke(category) } returns flowOf(loadingResult)

        viewModel.getSources(category)

        val sourcesState = viewModel.sources.value
        assertTrue(sourcesState is SourcesState)
        assertTrue(sourcesState?.isLoading ?: false)
    }

    @Test
    fun `test getSources error`() = runBlocking {
        val category = "technology"
        val errorMessage = "An error occurred"
        val errorResult = Result.Error<Source?>(errorMessage = errorMessage)

        coEvery { getSourcesUseCase.invoke(category) } returns flowOf(errorResult)

        viewModel.getSources(category)

        val sourcesState = viewModel.sources.value
        assertTrue(sourcesState is SourcesState)
        assertEquals(errorMessage, sourcesState?.errorMessage)
    }

    @Test
    fun `test getNews success`() = runBlocking {
        val source = "Tech News"
        val newsItems = News(
            articles = listOf(
                Article(
                    title = "Article 1",
                    author = "Author 1",
                    content = "Article content",
                    description = "Article description",
                    publishedAt = "2023-07-27",
                    url = "https://example.com/article1",
                    urlToImage = "https://example.com/image1.jpg"
                ),
                Article(
                    title = "Article 2",
                    author = "Author 2",
                    content = "Article content",
                    description = "Article description",
                    publishedAt = "2023-07-26",
                    url = "https://example.com/article2",
                    urlToImage = "https://example.com/image2.jpg"
                )
            ),
            status = "ok",
            totalResults = 2
        )
        val successResult = Result.Success<News?>(newsItems)

        coEvery { getNewsUseCase.invoke(source) } returns flowOf(successResult)

        viewModel.getNews(source)

        val newsState = viewModel.news.value
        assertTrue(newsState is NewsState)
        assertEquals(newsItems, newsState?.newsItems)
    }

    @Test
    fun `test saveArticle`() = runBlocking {
        val article = Article(
            title = "Sample Article",
            author = "Author",
            content = "Article content",
            description = "Article description",
            publishedAt = "2023-07-27",
            url = "https://example.com/sample-article",
            urlToImage = "https://example.com/image.jpg"
        )

        coEvery { saveArticleUseCase(article) } just runs

        viewModel.saveArticle(article)

        val savedArticles = viewModel.savedArticles.value
        assertTrue(savedArticles.contains(article))
    }

    @Test
    fun `test unSaveArticle`() = runBlocking {
        val article = Article(
            title = "Sample Article",
            author = "Author",
            content = "Article content",
            description = "Article description",
            publishedAt = "2023-07-27",
            url = "https://example.com/sample-article",
            urlToImage = "https://example.com/image.jpg"
        )
        viewModel.saveArticle(article)

        coEvery { unSaveArticleUseCase(article) } just runs

        viewModel.unSaveArticle(article)

        val savedArticles = viewModel.savedArticles.value
        assertFalse(savedArticles.contains(article))
    }

    @After
    fun teardown() {
        // Clears all mock implementations
        unmockkAll()
    }
}
