package vinova.drey.movie.service

import android.graphics.Movie
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import vinova.drey.movie.api.Api
import vinova.drey.movie.util.Constant
import vinova.drey.movie.model.MovieDetail
import vinova.drey.movie.model.Movies

// Using Singleton
interface MovieApi {
    companion object {
        private var instance : MovieApi? = null

        private fun create(): MovieApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)
        }

        fun getInstance() : MovieApi{
            if (instance == null){
                synchronized(MovieApi::class.java){
                    instance = create()
                    Log.d("MovieApi", "$instance")
                }
            }
            return instance!!
        }
    }

    @GET("movie/now_playing")
    fun getListMovies(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("page") page: Int
    ): Call<List<MovieDetail>>
}