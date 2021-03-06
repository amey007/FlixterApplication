package com.example.flixter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private const val TAG = "MovieAdapter"
const val MOVIE_EXTRA = "MOVIE_EXTRA"
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val  tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val  tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val  ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movieItem: Movie) {
            tvTitle.text = movieItem.title
            tvOverview.text =movieItem.overview

            val radius = 25; // corner radius, higher value = more rounded
            val margin = 5; // crop margin, set to 0 for corners with no crop

            // Use glide with context to load image into layout
            Glide.with(context).load(movieItem.posterImageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.img).fitCenter()
                .transform(RoundedCornersTransformation(radius, margin))
                .into(ivPoster)

        }
//        .centerCrop() // scale image to fill the entire ImageView
        override fun onClick(v: View?) {
            // 1. Get notified of a particular movie which is clicked
            val movie = moviesList[adapterPosition]
//            Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
            //2. Use the intent system to navigate to the new Activity
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("MOVIE_EXTRA", movie)
            context.startActivity(intent)
        }
    }
}
