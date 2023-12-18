package co.tiagoaguiar.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import co.tiagoaguiar.netflixremake.model.Movie
import co.tiagoaguiar.netflixremake.model.MovieDetail
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MovieTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(movieDetail: MovieDetail)
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

                if (statusCode == 400) {
                    stream = urlConnection.errorStream
                    val jsonAsString = stream.bufferedReader().use { it.readText() }

                    val json = JSONObject(jsonAsString)
                    val errorMessage = json.getString("message")
                    throw IOException(errorMessage)

                } else if (statusCode > 400) {
                    throw IOException("Communication Failure with server!")
                }

                stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                handler.post {
                    callback.onResult(toMovieDetail(jsonAsString))
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

    private fun toMovieDetail(jsonAsString: String): MovieDetail {
        val jsonRoot = JSONObject(jsonAsString)

        val id = jsonRoot.getInt("id")
        val desc = jsonRoot.getString("desc")
        val cast = jsonRoot.getString("cast")
        val title = jsonRoot.getString("title")
        val coverUrl = jsonRoot.getString("cover_url")


        val similars = mutableListOf<Movie>()
        val jsonMovies = jsonRoot.getJSONArray("movie")
        for (i in 0 until jsonMovies.length()) {
            val jsonMovie = jsonMovies.getJSONObject(i)
            val similarId = jsonMovie.getInt("id")
            val similarCoverUrl = jsonMovie.getString("cover_url")

            similars.add(Movie(id = similarId, coverUrl = similarCoverUrl))
        }


        return MovieDetail(
            Movie(id, desc, cast, title, coverUrl),
            similars
        )

    }

    data class ToMovie(
        val movie: Movie,
        val recommendedMovies: List<Movie>
    )
}