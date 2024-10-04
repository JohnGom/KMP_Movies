package ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import movieskmp.composeapp.generated.resources.Res
import movieskmp.composeapp.generated.resources.api_key
import org.jetbrains.compose.resources.stringResource
import org.johngom.movieskmp.data.MoviesRepository
import org.johngom.movieskmp.data.MoviesService
import org.johngom.movieskmp.data.database.MoviesDAO
import org.johngom.movieskmp.ui.screens.detail.DetailViewModel
import org.johngom.movieskmp.ui.screens.detail.DetailScreen
import ui.screens.home.HomeScreen
import ui.screens.home.HomeViewModel

@Composable
fun Navigation (moviesDAO: MoviesDAO) {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository(moviesDAO)
    NavHost(navController = navController, startDestination = "Home") {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("detail/${movie.id}")
                },
                viewModel = viewModel { HomeViewModel(repository) }
            )
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                onBack = { navController.popBackStack() },
                vm = viewModel { DetailViewModel(movieId, repository) }
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    moviesDAO: MoviesDAO,
    apiKey: String = stringResource(Res.string.api_key)
): MoviesRepository = remember {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", apiKey)
            }
        }
    }
    MoviesRepository(MoviesService(client), moviesDAO)
}