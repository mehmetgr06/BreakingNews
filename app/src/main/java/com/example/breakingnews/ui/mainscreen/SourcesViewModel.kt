package com.example.breakingnews.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingnews.common.Result
import com.example.breakingnews.domain.usecase.GetNewsUseCase
import com.example.breakingnews.domain.usecase.GetSavedArticlesUseCase
import com.example.breakingnews.domain.usecase.GetSourcesUseCase
import com.example.breakingnews.domain.usecase.SaveArticleUseCase
import com.example.breakingnews.domain.usecase.UnSaveArticleUseCase
import com.example.breakingnews.ui.model.Article
import com.example.breakingnews.ui.model.NewsState
import com.example.breakingnews.ui.model.SourcesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val unSaveArticleUseCase: UnSaveArticleUseCase
) : ViewModel() {

    private val _sources = MutableStateFlow<SourcesState?>(null)
    val sources: StateFlow<SourcesState?> = _sources

    private val _categoryList = MutableStateFlow<List<String>?>(null)
    val categoryList: StateFlow<List<String>?> = _categoryList

    private val _news = MutableStateFlow<NewsState?>(null)
    val news: StateFlow<NewsState?> = _news

    private val _savedArticles = MutableStateFlow<MutableList<Article>>(mutableListOf())
    val savedArticles: StateFlow<MutableList<Article>> = _savedArticles

    init {
        getSources()
        getSelectedArticles()
    }

    fun getSources(category: String = "") {
        viewModelScope.launch {
            getSourcesUseCase(category).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _sources.value = SourcesState(sourceItems = result.data)
                        if (category.isEmpty()) {
                            _categoryList.value = result.data?.categoryList.orEmpty()
                        }
                    }

                    is Result.Loading -> {
                        _sources.value = SourcesState(isLoading = true)
                    }

                    is Result.Error -> {
                        _sources.value =
                            SourcesState(errorMessage = result.message ?: "An error has occurred")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getNews(source: String) {
        viewModelScope.launch {
            getNewsUseCase(source).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _news.value = NewsState(newstems = result.data)
                    }

                    is Result.Loading -> {
                        _news.value = NewsState(isLoading = true)
                    }

                    is Result.Error -> {
                        _news.value =
                            NewsState(errorMessage = result.message ?: "An error has occurred")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getSelectedArticles() {
        viewModelScope.launch {
            getSavedArticlesUseCase().onEach {
                _savedArticles.value = it.toMutableList()
            }.launchIn(viewModelScope)
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveArticleUseCase(article)
            val updatedList = _savedArticles.value.toMutableList()
            updatedList.add(article)
            _savedArticles.emit(updatedList)
        }
    }

    fun unSaveArticle(article: Article) {
        viewModelScope.launch {
            unSaveArticleUseCase(article)
            val updatedList = _savedArticles.value.toMutableList()
            updatedList.remove(article)
            _savedArticles.emit(updatedList)
        }
    }
}