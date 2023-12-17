package co.tiagoaguiar.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter()
        val rv = findViewById<RecyclerView>(R.id.rv_main)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

    }


    private inner class MainAdapter : RecyclerView.Adapter<MainAdapter.MovieViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
            return MovieViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 60
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {}

        private inner class MovieViewHolder(private val itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        }
    }


}