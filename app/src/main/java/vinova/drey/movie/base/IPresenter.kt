package com.example.basemvp.base

interface IPresenter<V>{
    fun attachView(view: V)
    fun detachView()
}