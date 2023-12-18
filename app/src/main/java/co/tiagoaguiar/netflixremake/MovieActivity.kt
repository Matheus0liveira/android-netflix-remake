package co.tiagoaguiar.netflixremake

import MovieAdapter
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.model.MovieDetail
import co.tiagoaguiar.netflixremake.util.MovieTask
import java.lang.IllegalStateException

class MovieActivity : AppCompatActivity(), MovieTask.Callback {

    lateinit var txtTitle: TextView
    lateinit var txtDesc: TextView
    lateinit var txtCast: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val id = intent.getIntExtra("id", 1) ?: throw IllegalStateException("Id Not Found")

        val url =
            "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=c8fb62dd-d9bd-4b93-8991-20207311dbdf"
        MovieTask(this).execute(url)


        txtTitle = findViewById(R.id.movie_txt_title)
        txtDesc = findViewById(R.id.movie_txt_desc)
        txtCast = findViewById(R.id.movie_txt_cast)
        val rv = findViewById<RecyclerView>(R.id.movie_rv_similar)

        val movies = mutableListOf<Movie>()

        val adapter = MovieAdapter(movies, R.layout.movie_item_similar)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter


        val toolbar = findViewById<Toolbar>(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        val layerListDrawable = ContextCompat.getDrawable(this, R.drawable.shadows) as LayerDrawable
        val movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4)
        layerListDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)

        val coverImg = findViewById<ImageView>(R.id.movie_img)
        coverImg.setImageDrawable(layerListDrawable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) finish()

        return true

    }

    override fun onResult(movieDetail: MovieDetail) {
        Log.i("RSULTZZZ", movieDetail.toString())
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPreExecute() {
        Toast.makeText(this, "LOADING", Toast.LENGTH_SHORT).show()
    }


}