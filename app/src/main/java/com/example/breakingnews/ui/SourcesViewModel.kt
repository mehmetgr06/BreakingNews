package com.example.breakingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingnews.base.Result
import com.example.breakingnews.domain.GetSourcesUseCase
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
    private val getSourcesUseCase: GetSourcesUseCase
) : ViewModel() {

    private val _sources = MutableStateFlow<SourcesState?>(null)
    val sources: StateFlow<SourcesState?> = _sources

    private val _categoryList = MutableStateFlow<List<String>?>(null)
    val categoryList: StateFlow<List<String>?> = _categoryList

    init {
        getSources()
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
}
