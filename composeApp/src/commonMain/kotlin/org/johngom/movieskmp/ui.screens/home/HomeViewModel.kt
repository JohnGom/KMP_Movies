package ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.johngom.movieskmp.data.Movie
import org.johngom.movieskmp.data.MoviesRepository

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    fun onUiReady() {
         viewModelScope.launch {
             state = UiState(isLoading = true)
             repository.movies.collect {
                 if (it.isNotEmpty()) {
                     state = UiState(movies = it, isLoading = false)
                 }
             }
         }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )
}
