package org.johngom.movieskmp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.johngom.movieskmp.data.database.MoviesDAO

class MoviesRepository(
    private val moviesService: MoviesService,
    private val moviesDAO: MoviesDAO
) {

    val movies: Flow<List<Movie>> = moviesDAO.fetchPopularMovies().onEach { movies ->
        if(movies.isEmpty()) {
            val popularMovies = moviesService.fetchPopularMovies().results.map { it.toDomainMovie() }
            moviesDAO.insertMovies(popularMovies)
        }
    }

    suspend fun fetchMovieById(id: Int): Flow<Movie?> = moviesDAO.fetchMovieById(id).onEach { movie ->
        if(movie == null) {
            val movieById = moviesService.fetchMovieById(id).toDomainMovie()
            moviesDAO.insertMovies(listOf(movieById))
        }

    }
}

private fun RemoteMovie.toDomainMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    poster = "https://image.tmdb.org/t/p/w185/$posterPath",
    backdrop = backdropPath?.let {"https://image.tmdb.org/t/p/w780/$it" },
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    popularity = popularity,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
)