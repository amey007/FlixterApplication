package com.example.flixter

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

// Bundling the data(title, Img URL, details) using data class
@Parcelize
data class Movie(
    val title: String,
    val voteAverage: Double,
    val movieID: Int,
    val overview: String,
    // Won't be needing relative url directly, hence making private
    private val posterPath: String,
) : Parcelable {

    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object{
        // Allows us to call mthods movie class without having instance of movie
        fun fromJsonArray(movieJsonArray: JSONArray): MutableList<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getString("title"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getInt("id"),
                        movieJson.getString("overview"),
                        movieJson.getString("poster_path")

                    )
                )
            }
            return movies
        }
    }
}