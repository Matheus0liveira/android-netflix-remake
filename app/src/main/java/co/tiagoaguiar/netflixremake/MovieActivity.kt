package co.tiagoaguiar.netflixremake

import MovieAdapter
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Movie

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val txtTitle = findViewById<TextView>(R.id.movie_txt_title)
        val txtDesc = findViewById<TextView>(R.id.movie_txt_desc)
        val txtCast = findViewById<TextView>(R.id.movie_txt_cast)
        val rv = findViewById<RecyclerView>(R.id.movie_rv_similar)

        txtTitle.text = "Batman Begins"
        txtDesc.text = "Essa é a descrição do filme do Batman"
        txtCast.text = getString(R.string.cast, "Ator A, Ator B, Atriz A, Atriz B")


        val movies = mutableListOf<Movie>()
        for (i in 1..30) {
            movies.add(
                Movie(
                    coverUrl = if (i % 2 == 0) R.drawable.movie_4 else R.drawable.movie
                )
            )
        }
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
}