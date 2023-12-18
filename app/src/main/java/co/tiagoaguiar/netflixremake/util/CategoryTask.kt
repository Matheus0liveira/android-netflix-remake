package co.tiagoaguiar.netflixremake.util

import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {

    fun execute(url: String) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                val reqURL = URL(url)
                val urlConnection = reqURL.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000 // 2s
                urlConnection.connectTimeout = 2000 // 2s

                val statusCode = urlConnection.responseCode

                if (statusCode > 400) throw IOException("Communication Failure with server!")

                val stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                Log.i("HttpClient", jsonAsString)

            } catch (e: IOException) {
                Log.e("Error EXXXXX", e.message ?: "Unknown Error", e)

            }


        }

    }
}