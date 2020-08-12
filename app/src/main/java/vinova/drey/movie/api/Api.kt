package vinova.drey.movie.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vinova.drey.movie.model.MoviesResponse

interface Api {

    @GET("movie/now_playing")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Const.API_KEY,
        @Query("page") page: Int
    ): Call<MoviesResponse>
}