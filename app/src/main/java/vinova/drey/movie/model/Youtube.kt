package vinova.drey.movie.model


import com.google.gson.annotations.SerializedName

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