package vinova.drey.movie.module.movie

import vinova.drey.movie.model.MovieDetail
import vinova.drey.movie.model.Movies

interface IMovieView {
    fun onLoadMovieSuccess(movies: List<MovieDetail>)
    fun onError(msg: String)
    fun showError(msg: String)
    fun showProgress()
    fun hideProgress()
}