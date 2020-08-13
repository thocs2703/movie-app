package vinova.drey.movie.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import vinova.drey.movie.model.Movie
import vinova.drey.movie.model.Youtube

abstract class BaseViewHolder<view : View>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(movie: Movie)

    abstract fun bind(trailer: Youtube)
}