package vinova.drey.movie.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vinova.drey.movie.model.Movies
import vinova.drey.movie.model.Trailer
import vinova.drey.movie.util.Constant

interface Api {
//
//    @GET("movie/now_playing")
//    fun getListMovies(
//        @Query("api_key") apiKey: String = Constant.API_KEY,
//        @Query("page") page: Int
//    ): Call<Movies>

    @GET("movie/{id}/trailers")
    fun getTrailer(
        @Path("id") id: Int,
        @Query("api_key") token: String = Constant.API_KEY
    ): Call<Trailer>
}
