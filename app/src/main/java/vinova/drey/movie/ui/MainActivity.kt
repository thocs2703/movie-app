package vinova.drey.movie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_main.*
import vinova.drey.movie.R
import vinova.drey.movie.api.Const
import vinova.drey.movie.model.Movie
import vinova.drey.movie.repository.MoviesRepository

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesLayoutMgr: LinearLayoutManager
    private lateinit var youTubePlayerFragment: YouTubePlayerFragment

    private var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


        recycler_list_movie.layoutManager = moviesLayoutMgr

        moviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie)}
        recycler_list_movie.adapter = moviesAdapter

//        val intent = YouTubeStandalonePlayer.createVideoIntent(this, Const.YOUTUBE_API, "NhWg7AQLI_8")
//        startActivity(intent)

//        youTubePlayerFragment = fragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment
//        youTubePlayerFragment.initialize(Const.YOUTUBE_API, object: YouTubePlayer.OnInitializedListener{
//            override fun onInitializationSuccess(
//                p0: YouTubePlayer.Provider?,
//                p1: YouTubePlayer?,
//                p2: Boolean
//            ) {
//
//            }
//
//            override fun onInitializationFailure(
//                p0: YouTubePlayer.Provider?,
//                p1: YouTubeInitializationResult?
//            ) {
//
//            }
//
//        })

        getListMovies()

    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
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
        recycler_list_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = moviesLayoutMgr.itemCount // init = 20
//                val visibleItemCount = moviesLayoutMgr.childCount // init = 2
                val firstVisibleItem = moviesLayoutMgr.findFirstVisibleItemPosition() // init = 0

                Log.d("MainActivity", "Total Item Count: $totalItemCount")
//                Log.d("MainActivity", "Visible Item Count: $visibleItemCount")
                Log.d("MainActivity", "First Visible Count: $firstVisibleItem")

                if (firstVisibleItem >= totalItemCount / 2) {
                    recycler_list_movie.removeOnScrollListener(this)
                    page++
                    getListMovies()
                }
            }
        })
    }

}