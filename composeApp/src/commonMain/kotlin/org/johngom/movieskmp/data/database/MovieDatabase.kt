package org.johngom.movieskmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.johngom.movieskmp.data.Movie

const val DATABASE_NAME = "movies.db"

@Database(entities = [Movie::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO

}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    override fun initialize(): MovieDatabase
}