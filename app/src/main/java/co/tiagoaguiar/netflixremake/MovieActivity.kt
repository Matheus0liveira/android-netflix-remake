package co.tiagoaguiar.netflixremake

import MovieAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.model.MovieDetail
import co.tiagoaguiar.netflixremake.util.DownloadImageTask
import co.tiagoaguiar.netflixremake.util.MovieTask
import java.lang.IllegalStateException

class MovieActivity : AppCompatActivity(), MovieTask.Callback {

    private lateinit var txtTitle: TextView
    private lateinit var txtDesc: TextView
    private lateinit var txtCast: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var movies: MutableList<Movie>
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        progressBar = findViewById(R.id.progress_movie)

        val id = intent.getIntExtra("id", 1) ?: throw IllegalStateException("Id Not Found")

        val url =
            "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=c8fb62dd-d9bd-4b93-8991-20207311dbdf"
        MovieTask(this).execute(url)


        txtTitle = findViewById(R.id.movie_txt_title)
        txtDesc = findViewById(R.id.movie_txt_desc)
        txtCast = findViewById(R.id.movie_txt_cast)
        val rv = findViewById<RecyclerView>(R.id.movie_rv_similar)

        movies = mutableListOf()
        movieAdapter = MovieAdapter(movies, R.layout.movie_item_similar) {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("id", it)

            startActivity(intent)
        }
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = movieAdapter


        val toolbar = findViewById<Toolbar>(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) finish()

        return true

    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(movieDetail: MovieDetail) {
        txtTitle.text = movieDetail.movie.title
        txtDesc.text = movieDetail.movie.desc
        txtCast.text = getString(R.string.cast, movieDetail.movie.cast)

        movies.clear()
        movies.addAll(movieDetail.similars)
        movieAdapter.notifyDataSetChanged()

        DownloadImageTask(object :
            DownloadImageTask.Callback {
            override fun onResult(bitmap: Bitmap) {

                val layerListDrawable =
                    ContextCompat.getDrawable(
                        this@MovieActivity,
                        R.drawable.shadows
                    ) as LayerDrawable
                val movieCover = BitmapDrawable(resources, bitmap)
                layerListDrawable.setDrawableByLayerId(R.id.cover_drawable, movieCover)

                val coverImg = findViewById<ImageView>(R.id.movie_img)
                coverImg.setImageDrawable(layerListDrawable)
            }
        }).execute(movieDetail.movie.coverUrl)



        progressBar.visibility = View.GONE

    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE
        finish()
    }


}