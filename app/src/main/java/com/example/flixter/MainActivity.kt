package com.example.flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val NOW_PLAYING_URL ="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val moviesList = mutableListOf<Movie>()

    // Getting reference to recycle view
    private lateinit var rvMovies : RecyclerView

    /*
    1. define the data model class as the data source - DONE
    2. Add recycle view to the layout - DONE
    3. Create custom row xml file to visualize the item - DONE
    4. Create an adapter and View Holder to render an item - DONE
    5. Bind the adapter to the data source to populate RecyclerView
    6. Bind the layout manager to the RecycleView
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val orientation = resources.configuration.orientation
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setContentView(R.layout.activity_main)
//            // ...
//        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.activity_main)
//            // ...
//        }
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        // Calling MovieAdapter constructor with params (context, datasource)
        val movieAdapter = MovieAdapter(this, moviesList)

        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            // JsonHttpResponseHandler is function in AbsCallback Interface which is the param to the get function
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG,"onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG,"onSuccess: JSON data $json")
                // movieJsonArray contains the contents of the results key of the JSON object
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    moviesList.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie List $moviesList")
                }catch (e: JSONException){
                    Log.e(TAG,"Encountered Exception $e")
                }


            }

        })
    }
}