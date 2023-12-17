import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.R
import co.tiagoaguiar.netflixremake.model.Movie

class MovieAdapter(
    private val movies: List<Movie>,
    @LayoutRes private val layoutId: Int = R.layout.movie_item
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class MovieViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {

            val imageCover = itemView.findViewById<ImageView>(R.id.img_cover)

            imageCover.setImageResource(movie.coverUrl)

        }

    }
}