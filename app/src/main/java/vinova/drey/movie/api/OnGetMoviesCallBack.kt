package vinova.drey.movie.api

import vinova.drey.movie.model.Movie

interface OnGetMoviesCallBack {
    fun onSuccess(movies: List<Movie>);

    fun onError();
}