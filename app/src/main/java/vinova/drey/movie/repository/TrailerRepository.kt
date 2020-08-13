package vinova.drey.movie.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vinova.drey.movie.api.Api
import vinova.drey.movie.model.Movie
import vinova.drey.movie.model.MoviesResponse
import vinova.drey.movie.model.Trailer
import vinova.drey.movie.model.Youtube
import vinova.drey.movie.util.Const

object TrailerRepository {
    private val api: Api

    // Call when instance is initialized
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getTrailer(
        id: Int,
        onSuccess: (movies: List<Youtube>) -> Unit, // Unit-> doesn't return anything, but accept a list of movies
        onError: () -> Unit // Doesn't accept any thing
    ) {
        api.getTrailer(id)
            .enqueue(object : Callback<Trailer> {
                override fun onResponse(
                    call: Call<Trailer>,
                    response: Response<Trailer>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.youtube) // Invoke is how execute a high-order function
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<Trailer>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}