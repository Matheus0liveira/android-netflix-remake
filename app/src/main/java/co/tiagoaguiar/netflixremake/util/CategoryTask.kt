package co.tiagoaguiar.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import co.tiagoaguiar.netflixremake.model.Category
import co.tiagoaguiar.netflixremake.model.Movie
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
        fun onPreExecute()

    }

    fun execute(url: String) {
        callback.onPreExecute()
        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {
                val reqURL = URL(url)
                urlConnection = reqURL.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000 // 2s
                urlConnection.connectTimeout = 2000 // 2s

                val statusCode = urlConnection.responseCode

                if (statusCode > 400) throw IOException("Communication Failure with server!")

                stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                handler.post {
                    callback.onResult(toCategory(jsonAsString))
                }

            } catch (e: IOException) {
                val message = e.message ?: "Unknown Error"
                handler.post {
                    callback.onFailure(message)
                }
                Log.e("Error", message, e)

            } finally {
                urlConnection?.disconnect()
                stream?.close()
            }
        }

    }

    private fun toCategory(jsonAsString: String): List<Category> {
        val categories = mutableListOf<Category>()
        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")
        for (i in 0 until jsonCategories.length()) {
            val jsonCategory = jsonCategories.getJSONObject(i)
            val name = jsonCategory.getString("title")
            val jsonMovies = jsonCategory.getJSONArray("movie")


            val movies = mutableListOf<Movie>()
            for (j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(j)
                val id = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")

                movies.add(Movie(id, coverUrl))
            }

            categories.add(Category(name, movies))


        }


        return categories

    }
}