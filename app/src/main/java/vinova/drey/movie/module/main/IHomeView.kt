package vinova.drey.movie.module.main

import vinova.drey.movie.model.Movies

interface IHomeView {
    fun onLoadMovieSuccess(movies: Movies)
    fun onError(msg: String)
    fun showError(msg: String)
    fun showProgress()
    fun hideProgress()
}