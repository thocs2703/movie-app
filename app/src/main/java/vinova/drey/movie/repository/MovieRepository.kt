package vinova.drey.movie.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vinova.drey.movie.api.Api
import vinova.drey.movie.api.Const
import vinova.drey.movie.api.Const.BASE_URL
import vinova.drey.movie.model.Movie
import vinova.drey.movie.model.MoviesResponse

// Using Singleton
object MoviesRepository {

    private val api: Api

    // Call when instance is initialized
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getListMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit, // Unit-> doesn't return anything, but accept a list of movies
        onError: () -> Unit // Doesn't accept any thing
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies) // Invoke is how execute a high-order function
                        } else {
                            onError.invoke()
                        }
                    }
                }
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}