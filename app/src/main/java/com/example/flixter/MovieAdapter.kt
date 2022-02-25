package com.example.flixter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val moviesList: MutableList<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    // Expensive operation: Create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }
    // Cheap - simply binds data to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movieItem = moviesList[position]
        holder.bind(movieItem)

    }

    override fun getItemCount(): Int {
        // can be done inline,but choose not to
        return moviesList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val  tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val  tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val  ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        fun bind(movieItem: Movie) {
            tvTitle.text = movieItem.title
            tvOverview.text =movieItem.overview
            // Use glide with context to load image into layout
            Glide.with(context).load(movieItem.posterImageUrl).into(ivPoster)
        }
    }
}
