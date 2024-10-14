package org.johngom.movieskmp.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.johngom.movieskmp.data.Movie
import org.johngom.movieskmp.data.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel(private val id: Int): ViewModel(), KoinComponent {

    private val repository: MoviesRepository by inject()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.fetchMovieById(id).collect {
                it?.let {
                    _state.value = UiState(movie = it) }
                }
        }
    }

    fun onFavoriteClick() {
        state.value.movie?.let { movie ->
            viewModelScope.launch {
                repository.toggleFavorite(movie)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
    )
}