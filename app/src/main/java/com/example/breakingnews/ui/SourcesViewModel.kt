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

    init {
        getSources()
    }

    fun getSources() {
        viewModelScope.launch {
            getSourcesUseCase().onEach { result ->
                when (result) {
                    is Result.Success -> {

                    }

                    is Result.Loading -> {

                    }

                    is Result.Error -> {

                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
