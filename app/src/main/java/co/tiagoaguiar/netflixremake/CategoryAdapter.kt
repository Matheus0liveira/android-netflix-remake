import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.netflixremake.R
import co.tiagoaguiar.netflixremake.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    inner class CategoryViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {

            val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)

            txtTitle.text = category.name

            val adapter = MovieAdapter(category.movies, R.layout.movie_item, onItemClickListener)
            val rv = itemView.findViewById<RecyclerView>(R.id.rv_category)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)


        }

    }
}