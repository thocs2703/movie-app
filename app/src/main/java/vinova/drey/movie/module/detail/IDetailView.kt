package vinova.drey.movie.module.detail

import vinova.drey.movie.model.Movies
import vinova.drey.movie.model.Trailer

interface IDetailView {
    fun onLoadTrailerSuccess(trailer: Trailer)
    fun onError(msg: String)
    fun showError(msg: String)
    fun showProgress()
    fun hideProgress()
}