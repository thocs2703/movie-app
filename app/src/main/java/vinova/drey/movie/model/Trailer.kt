package vinova.drey.movie.model


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("quicktime")
    val quicktime: List<Any>,
    @SerializedName("youtube")
    val youtube: List<Youtube>
)

data class Youtube(
    @SerializedName("name")
    val name: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("type")
    val type: String
)