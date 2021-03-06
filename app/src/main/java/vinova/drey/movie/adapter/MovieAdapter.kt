package vinova.drey.movie.adapter

import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_poster.view.*
import kotlinx.android.synthetic.main.item_video.view.*
import vinova.drey.movie.R
import vinova.drey.movie.model.MovieDetail
import vinova.drey.movie.model.Movies
import vinova.drey.movie.model.Youtube
import vinova.drey.movie.util.Constant

class MoviesAdapter(
    private var movies: MutableList<MovieDetail>,
    private var onMovieClick: (movie: MovieDetail) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        const val POSTER_VIEW_TYPE = 1
        const val VIDEO_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            POSTER_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_poster, parent, false)
                PortraitViewHolder(view)
            }
            VIDEO_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video, parent, false)
                LandScapeViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position].rating > 5) VIDEO_VIEW_TYPE else POSTER_VIEW_TYPE
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: Movies) {
        val listMovies = movies.movies
        this.movies.addAll(listMovies)
        notifyItemRangeInserted(
            this.movies.size,
            listMovies.size - 1
        )
    }

    inner class PortraitViewHolder(itemView: View) : BaseViewHolder<View>(itemView) {
        override fun bind(movie: MovieDetail) {
            itemView.titleText.text = movie.title
            itemView.overviewText.text = movie.overview

            val moviePosterURL = Constant.IMAGE_URL + movie.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(10, 5)))
                .into(itemView.imagePoster);

            itemView.setOnClickListener {
                onMovieClick.invoke(movie)
            }

        }

        override fun bind(trailer: Youtube) {
        }

    }

    inner class LandScapeViewHolder(itemView: View) : BaseViewHolder<View>(itemView) {
        override fun bind(movie: MovieDetail) {
            val orientation = itemView.context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d("LandScapeViewHolder", "ORIENTATION_LANDSCAPE")
                itemView.title_text?.text = movie.title
                itemView.overview_text?.text = movie.overview
                Log.d("LandScapeViewHolder", "Title ${movie.title}")
                Log.d("LandScapeViewHolder", "Overview ${movie.overview}")
            }

            val backdropURL = Constant.IMAGE_URL + movie.backdropPath
            Glide.with(itemView.context)
                .load(backdropURL)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(10, 5)))
                .into(itemView.backdrop_image);
            itemView.play_image.visibility = View.VISIBLE
            itemView.play_image.setOnClickListener {
                onMovieClick.invoke(movie)
            }
        }

        override fun bind(trailer: Youtube) {

        }
    }

    fun clear() {
        movies.clear()
        notifyDataSetChanged()
    }
}