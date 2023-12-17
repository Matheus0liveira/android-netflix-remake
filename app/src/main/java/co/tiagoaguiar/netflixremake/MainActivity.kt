package co.tiagoaguiar.netflixremake

import CategoryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()

        for (j in 1..10) {
            val movies = mutableListOf<Movie>()
            for (i in 1..5) {
                movies.add(
                    Movie(
                        coverUrl = if (i % 2 == 0) R.drawable.movie_4 else R.drawable.movie
                    )
                )
            }
            categories.add(Category(name = "Category $j", movies))
        }

        val adapter = CategoryAdapter(categories)
        val rv = findViewById<RecyclerView>(R.id.rv_main)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

    }


}