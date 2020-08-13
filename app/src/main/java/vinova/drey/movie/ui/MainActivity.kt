package vinova.drey.movie.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.youtube.player.YouTubePlayerFragment
import kotlinx.android.synthetic.main.activity_main.*
import vinova.drey.movie.R
import vinova.drey.movie.adapter.MoviesAdapter
import vinova.drey.movie.util.Const
import vinova.drey.movie.model.Movie
import vinova.drey.movie.repository.MoviesRepository

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesLayoutMgr: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var page = 1

    private lateinit var swipeContainer: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        progressBar = findViewById(R.id.progress_bar)
//        progressBar.visibility = View.VISIBLE

        moviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        recyclerView = findViewById(R.id.recycler_list_movie)
        recyclerView.layoutManager = moviesLayoutMgr

        setItemsData()
        recyclerView.adapter = moviesAdapter

        swipeContainer = findViewById(R.id.swipe_container)
        refreshLayout()


//        val intent = YouTubeStandalonePlayer.createVideoIntent(this, Const.YOUTUBE_API, "NhWg7AQLI_8")
//        startActivity(intent)


        getListMovies()

    }

    private fun setItemsData() {
        moviesAdapter =
            MoviesAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
//        progressBar.visibility = View.GONE
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(Const.MOVIE_ID, movie.id)
        intent.putExtra(Const.MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(Const.MOVIE_POSTER, movie.posterPath)
        intent.putExtra(Const.MOVIE_TITLE, movie.title)
        intent.putExtra(Const.MOVIE_RATING, movie.rating)
        intent.putExtra(Const.MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(Const.MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    private fun getListMovies() {
        MoviesRepository.getListMovies(
            page,
            ::onListMoviesFetched,
            ::onError
        )
    }

    private fun onListMoviesFetched(movies: List<Movie>) {
        moviesAdapter.appendMovies(movies)
        attachListMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun attachListMoviesOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = moviesLayoutMgr.itemCount // init = 20
//                val visibleItemCount = moviesLayoutMgr.childCount // init = 2
                val firstVisibleItem = moviesLayoutMgr.findFirstVisibleItemPosition() // init = 0

                Log.d("MainActivity", "Total Item Count: $totalItemCount")
//                Log.d("MainActivity", "Visible Item Count: $visibleItemCount")
                Log.d("MainActivity", "First Visible Count: $firstVisibleItem")

                if (firstVisibleItem >= totalItemCount / 2) {
                    recyclerView.removeOnScrollListener(this)
                    page++
                    getListMovies()
                }
            }
        })
    }

    private fun refreshLayout(){
        swipeContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        swipeContainer.setColorSchemeColors(Color.WHITE)

        swipeContainer.setOnRefreshListener {
            moviesAdapter.clear()
            getListMovies()
            setItemsData()
            recyclerView.adapter = moviesAdapter
            swipeContainer.isRefreshing = false
        }
    }

}