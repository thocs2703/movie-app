package vinova.drey.movie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import vinova.drey.movie.R
import vinova.drey.movie.api.Const
import vinova.drey.movie.databinding.MovieDetailBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: MovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.movie_detail)

        val extras = intent.extras

        if (extras!= null) getMovieDetails(extras) else finish()

        binding.executePendingBindings()
    }

    private fun getMovieDetails(extras: Bundle){

        binding.titleText.text = extras.getString(Const.MOVIE_TITLE)
        binding.overviewText.text = extras.getString(Const.MOVIE_OVERVIEW)
        Log.d("MovieDetailsActivity", "Rating: ${extras.getString(Const.MOVIE_RATING)}")
        binding.movieRating.rating = extras.getFloat(Const.MOVIE_RATING, 0f)
        binding.releaseDataText.text = extras.getString(Const.MOVIE_RELEASE_DATE)

        extras.getString(Const.MOVIE_BACKDROP)?.let {
            Glide.with(this)
                .load(Const.IMAGE_URL + it)
                .transform(CenterCrop())
                .into(binding.backdropImage)
        }

        extras.getString(Const.MOVIE_POSTER)?.let {
            Glide.with(this)
                .load(Const.IMAGE_URL + it)
                .transform(CenterCrop())
                .into(binding.posterImage)
        }
    }
}