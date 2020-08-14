package vinova.drey.movie.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vinova.drey.movie.R
import vinova.drey.movie.adapter.MoviesAdapter
import vinova.drey.movie.util.Constant
import vinova.drey.movie.model.MovieDetail
import vinova.drey.movie.module.movie.IMovieView
import vinova.drey.movie.module.movie.MoviePresenter
import vinova.drey.movie.service.MovieApi

class MainActivity : AppCompatActivity(), IMovieView {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesLayoutMgr: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var page = 1

    private lateinit var swipeContainer: SwipeRefreshLayout

    private lateinit var moviePresenter: MoviePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
//        progressBar = findViewById(R.id.progress_bar)
//        progressBar.visibility = View.VISIBLE

        adapt()

        refreshLayout()

    }

    private fun init() {
        Log.d("MainActivity", "Movie Presenter init")
        moviePresenter = MoviePresenter()
        moviePresenter.attachView(this)
        Log.d("MainActivity", "Movie Presenter Is View Attached: ${moviePresenter.isViewAttached()}")
        moviePresenter.getListMovie()
    }

    private fun setItemsData() {
        moviesAdapter =
            MoviesAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        Log.d("MainActivity", "Movie Adapter: ${moviesAdapter.itemCount}")
    }

    private fun adapt(){
        moviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        recyclerView = findViewById(R.id.recycler_list_movie)
        recyclerView.layoutManager = moviesLayoutMgr

        setItemsData()
        recyclerView.adapter = moviesAdapter
    }

    private fun showMovieDetails(movie: MovieDetail) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(Constant.MOVIE_ID, movie.id)
        intent.putExtra(Constant.MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(Constant.MOVIE_POSTER, movie.posterPath)
        intent.putExtra(Constant.MOVIE_TITLE, movie.title)
        intent.putExtra(Constant.MOVIE_RATING, movie.rating)
        intent.putExtra(Constant.MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(Constant.MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }


    private fun attachListMoviesOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = moviesLayoutMgr.itemCount // init = 20
                val firstVisibleItem = moviesLayoutMgr.findFirstVisibleItemPosition() // init = 0

                Log.d("MainActivity", "Total Item Count: $totalItemCount")
//                Log.d("MainActivity", "Visible Item Count: $visibleItemCount")
                Log.d("MainActivity", "First Visible Count: $firstVisibleItem")

                if (firstVisibleItem >= totalItemCount / 2) {
                    recyclerView.removeOnScrollListener(this)
                    page++
                    moviePresenter.getListMovie()
                }
            }
        })
    }

    private fun refreshLayout() {
        swipeContainer = findViewById(R.id.swipe_container)
        swipeContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        swipeContainer.setColorSchemeColors(Color.WHITE)

        swipeContainer.setOnRefreshListener {
            moviesAdapter.clear()
            moviePresenter.getListMovie()
            setItemsData()
            recyclerView.adapter = moviesAdapter
            swipeContainer.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviePresenter.detachView()
    }

    override fun onLoadMovieSuccess(movies: List<MovieDetail>) {
        Log.d("MainActivity", "Load Movie Success")
        moviesAdapter.appendMovies(movies)
        attachListMoviesOnScrollListener()
    }

    override fun onError(msg: String) {
        Log.d("MainActivity", "Load Movie Error")
    }

    override fun showError(msg: String) {
        Log.d("showError", msg)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}