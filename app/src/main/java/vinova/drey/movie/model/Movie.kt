package vinova.drey.movie.model

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieDetail>,
    @SerializedName("total_pages")
    val pages: Int
)

data class MovieDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("release_date")
    val releaseDate: String
)