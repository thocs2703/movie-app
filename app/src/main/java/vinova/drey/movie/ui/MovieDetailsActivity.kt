package vinova.drey.movie.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.youtube.player.*
import kotlinx.android.synthetic.main.movie_detail.*
import kotlinx.android.synthetic.main.movie_item.*
import org.w3c.dom.Text
import vinova.drey.movie.R
import vinova.drey.movie.api.Const


class MovieDetailsActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    //    private lateinit var binding: MovieDetailBinding
    private lateinit var titleText: TextView
    private lateinit var overviewText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var rating: RatingBar
    private lateinit var posterImage: ImageView
    private lateinit var playImage: ImageView

    private lateinit var playerView: YouTubePlayerView
//    lateinit var youTubePlayerG: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_v2)

//        binding = DataBindingUtil.setContentView(this, R.layout.movie_detail)

        getView()

        val extras = intent.extras

        if (extras != null) getMovieDetails(extras) else finish()

//        playImage.setOnClickListener {
//            val intent =
//                YouTubeStandalonePlayer.createVideoIntent(this, Const.YOUTUBE_API, "NhWg7AQLI_8")
//            startActivity(intent)
//        }

//        youTubePlayerView.initialize(Const.YOUTUBE_API, this)

//        playerView = YouTubePlayerView(this)

        playerView.initialize(Const.YOUTUBE_API, this)


//        binding.executePendingBindings()
    }

    private fun getMovieDetails(extras: Bundle) {

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
//        posterImage = findViewById(R.id.poster_image)!!
//        playImage = findViewById(R.id.play_image)!!
        playerView = findViewById(R.id.player_youtube)!!
    }


    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
                                         wasRestored: Boolean) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: youTubePlayer is ${youTubePlayer?.javaClass}")
//        Toast.makeText(this, "Initialized Youtube Player successfully", Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            youTubePlayer?.cueVideo("fis-9Zqu2Ro")
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