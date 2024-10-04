package org.johngom.movieskmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.johngom.movieskmp.data.Movie

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movie")
    fun fetchPopularMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun fetchMovieById(id: Int): Flow<Movie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<Movie>)
}