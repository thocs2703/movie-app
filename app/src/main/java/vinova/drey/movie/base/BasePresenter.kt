package com.example.basemvp.base

open class BasePresenter<V> : IPresenter<V> {
    private var view: V? = null
    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    fun isViewAttached() = view != null

    fun getView() = view
}