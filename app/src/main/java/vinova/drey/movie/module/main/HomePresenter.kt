package vinova.drey.movie.module.main

import android.util.Log
import vinova.drey.movie.base.BasePresenter
import vinova.drey.movie.service.ApiRequestHelper
import vinova.drey.movie.service.MovieApi

class HomePresenter: IHomePresenter, BasePresenter<IHomeView>() {
    var page: Int = 1
    override fun getListMovie() {
        if(isViewAttached()){
            ApiRequestHelper.asyncRequest(
                request = MovieApi.getInstance().getListMovies(page = page),
                onError = {errorMsg -> getView()?.onError(msg = errorMsg) },
                onSuccess = {response -> getView()?.onLoadMovieSuccess(movies = response!!)}
            )
            Log.d("MoviePresenter", "Page = $page")
        }
    }
}