package vinova.drey.movie.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vinova.drey.movie.api.Api
import vinova.drey.movie.model.Trailer
import vinova.drey.movie.model.Youtube
import vinova.drey.movie.util.Constant

interface TrailerApi {
    companion object{
        private var instance : TrailerApi? = null

        private fun create(): TrailerApi {
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
                .create(TrailerApi::class.java)
        }

        fun getInstance() : TrailerApi{
            if (instance == null){
                synchronized(TrailerApi::class.java){
                    instance = create()
                    Log.d("TrailerApi", "$instance")
                }
            }
            return instance!!
        }
    }

    @GET("movie/{id}/trailers")
    fun getTrailer(
        @Path("id") id: Int,
        @Query("api_key") token: String = Constant.API_KEY
    ): Call<Trailer>

}