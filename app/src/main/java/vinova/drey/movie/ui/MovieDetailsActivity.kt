package vinova.drey.movie.ui

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.youtube.player.*
import vinova.drey.movie.R
import vinova.drey.movie.model.Movie
import vinova.drey.movie.model.Youtube
import vinova.drey.movie.repository.MoviesRepository
import vinova.drey.movie.repository.TrailerRepository
import vinova.drey.movie.util.Const
import kotlin.properties.Delegates


class MovieDetailsActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    //    private lateinit var binding: MovieDetailBinding
    private lateinit var titleText: TextView
    private lateinit var overviewText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var rating: RatingBar
    private lateinit var goBack: TextView

    private lateinit var playerView: YouTubePlayerView
    private lateinit var sourceTrailer: String
//    lateinit var youTubePlayerG: YouTubePlayer


    private var id: Int = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail)


//        binding = DataBindingUtil.setContentView(this, R.layout.movie_detail)

        getView()

        val extras = intent.extras
        if (extras != null) getMovieDetails(extras) else finish()

        getTrailer()

        goBack.text = titleText.text
        goBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        goBack.setOnClickListener {
            super.onBackPressed()
        }

//        playerView.initialize(Const.YOUTUBE_API, this)

//        binding.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }

    private fun getMovieDetails(extras: Bundle) {
        id = (extras.getInt(Const.MOVIE_ID))
        Log.d("MovieDetailsActivity", "MovieID: $id")
        titleText.text = extras.getString(Const.MOVIE_TITLE)
        overviewText.text = extras.getString(Const.MOVIE_OVERVIEW)
        Log.d("MovieDetailsActivity", "Overview: ${extras.getString(Const.MOVIE_OVERVIEW)}")
        Log.d("MovieDetailsActivity", "Rating: ${extras.getString(Const.MOVIE_RATING)}")
        rating.rating = extras.getFloat(Const.MOVIE_RATING, 0f)
        releaseDateText.text = extras.getString(Const.MOVIE_RELEASE_DATE)

//        extras.getString(Const.MOVIE_POSTER)?.let {
//            Glide.with(this)
//                .load(Const.IMAGE_URL + it)
//                .transform(CenterCrop())
//                .into(posterImage)
//        }
    }

    private fun getView() {
        titleText = findViewById(R.id.title_text)!!
        overviewText = findViewById(R.id.overview_text)!!
        releaseDateText = findViewById(R.id.release_data_text)!!
        rating = findViewById(R.id.movie_rating)!!
        playerView = findViewById(R.id.player_youtube)!!
        goBack = findViewById(R.id.actionbar_title)
    }


    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
                                         wasRestored: Boolean) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: youTubePlayer is ${youTubePlayer?.javaClass}")
//        Toast.makeText(this, "Initialized Youtube Player successfully", Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            youTubePlayer?.cueVideo(sourceTrailer)
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?,
                                         youTubeInitializationResult: YouTubeInitializationResult?) {
        val REQUEST_CODE = 0

        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun getTrailer() {
        Log.d("MovieDetailsActivity", "Get Trailer")
        TrailerRepository.getTrailer(
            id,
            ::onTrailerFetched,
            ::onError
        )
    }

    private fun onTrailerFetched(youtubeSources: List<Youtube>) {
        val firstTrailer: Youtube = youtubeSources[0]
        sourceTrailer = firstTrailer.source
        playerView.initialize(Const.YOUTUBE_API, this)
        Log.d("MovieDetailsActivity", "SourceTrailer: ${sourceTrailer}")
    }

    private fun onError() {
        Log.d("MovieDetailsActivity", "Fetched Trailer Error!")
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
        }

        override fun onStopped() {
        }

        override fun onPaused() {
        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

}