package vinova.drey.movie.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.movie_item.view.*
import vinova.drey.movie.R
import vinova.drey.movie.api.Const
import vinova.drey.movie.api.Const.IMAGE_URL
import vinova.drey.movie.model.Movie

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private var onMovieClick:(movie: Movie) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Movie) {
            itemView.titleText.text = item.title
            itemView.overviewText.text = item.overview + "..."

            val moviePosterURL = Const.IMAGE_URL + item.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .transform(CenterCrop())
                .into(itemView.imagePoster);

            itemView.setOnClickListener{
                onMovieClick.invoke(item)
            }
        }
    }
}