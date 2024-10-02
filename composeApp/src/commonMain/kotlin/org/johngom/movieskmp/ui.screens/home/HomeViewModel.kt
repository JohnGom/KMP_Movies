package ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.johngom.movieskmp.data.Movie
import org.johngom.movieskmp.data.MoviesService
import org.johngom.movieskmp.data.RemoteMovie
import org.johngom.movieskmp.data.movies

class HomeViewModel(
    private val movieService: MoviesService
): ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
         viewModelScope.launch {
             state = UiState(isLoading = true)
             state = movieService.fetchPopularMovies().results
                 .map { it.toDomainMovie() }
                 .let { UiState(movies = it, isLoading = false) }
         }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )
}

private fun RemoteMovie.toDomainMovie() = Movie(
    id = id,
    title = title,
    poster = "https://image.tmdb.org/t/p/w500$posterPath"
)
