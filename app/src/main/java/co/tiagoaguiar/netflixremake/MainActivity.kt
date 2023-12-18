package co.tiagoaguiar.netflixremake

import CategoryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.util.CategoryTask
import kotlin.random.Random

class MainActivity : AppCompatActivity(), CategoryTask.Callback {
    private lateinit var progressBar: ProgressBar
    private val categories = mutableListOf<Category>()
    private lateinit var adapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_main)


        adapter = CategoryAdapter(categories)
        val rv = findViewById<RecyclerView>(R.id.rv_main)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=c8fb62dd-d9bd-4b93-8991-20207311dbdf")

    }

    override fun onResult(categories: List<Category>) {
        progressBar.visibility = View.GONE

        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        progressBar.visibility = View.GONE
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE

    }


}