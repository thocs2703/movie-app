package vinova.drey.movie.base

interface IPresenter<V>{
    fun attachView(view: V)
    fun detachView()
}