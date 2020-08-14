package vinova.drey.movie.module.detail

import android.util.Log
import vinova.drey.movie.base.BasePresenter
import vinova.drey.movie.module.main.IHomePresenter
import vinova.drey.movie.module.main.IHomeView
import vinova.drey.movie.service.ApiRequestHelper
import vinova.drey.movie.service.MovieApi
import vinova.drey.movie.service.TrailerApi

class DetailPresenter: IDetailPresenter, BasePresenter<IDetailView>() {
    var id: Int = 0
    override fun getTrailer() {
        if(isViewAttached()){
            ApiRequestHelper.asyncRequest(
                request = TrailerApi.getInstance().getTrailer(id = id),
                onError = {errorMsg -> getView()?.onError(msg = errorMsg) },
                onSuccess = {response -> getView()?.onLoadTrailerSuccess(trailer = response!!)}
            )
            Log.d("DetailPresenter", "Id = $id")
        }
    }
}