package org.johngom.movieskmp.data

data class Movie(val id: Int, val title: String, val poster: String)

val movies = (1..100).map {
    Movie(
        id = it,
        title = "Movie $it",
        poster = "https://picsum.photos/id/$it/100/100"
    )
}
