package vinova.drey.movie.module.movie

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import vinova.drey.movie.api.Api
import vinova.drey.movie.base.BasePresenter
import vinova.drey.movie.model.Movies
import vinova.drey.movie.service.ApiRequestHelper
import vinova.drey.movie.service.MovieApi

class MoviePresenter: IMoviePresenter, BasePresenter<IMovieView>() {
    private val page: Int = 1
    override fun getListMovie() {
        if(isViewAttached()){
            ApiRequestHelper.asyncRequest(
                request = MovieApi.getInstance().getListMovies(page = page),
                onError = {errorMsg -> getView()?.onError(msg = errorMsg) },
                onSuccess = {response -> getView()?.onLoadMovieSuccess(response ?: listOf()) }
            )
            Log.d("MoviePresenter", "Page = $page")
        }
    }
}