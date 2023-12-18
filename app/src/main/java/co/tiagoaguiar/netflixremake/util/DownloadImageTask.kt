package co.tiagoaguiar.netflixremake.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import co.tiagoaguiar.netflixremake.model.Category
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DownloadImageTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(bitmap: Bitmap)
    }

    fun execute(url: String) {
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
                val bitmap = BitmapFactory.decodeStream(stream)

                handler.post {
                    callback.onResult(bitmap)
                }

            } catch (e: IOException) {
                val message = e.message ?: "Unknown Error"

                Log.e("Download Image error", message, e)

            } finally {
                urlConnection?.disconnect()
                stream?.close()
            }
        }

    }
}