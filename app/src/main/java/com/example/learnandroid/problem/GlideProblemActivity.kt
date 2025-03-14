package com.example.learnandroid.problem

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroid.databinding.ActivityGlideProblemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author zhuhao
 * @date  21:33
 **/
class GlideProblemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGlideProblemBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, GlideProblemActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlideProblemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btLoadImage.setOnClickListener {
            loadImageWithOkHttp()
        }
    }

    fun loadImageWithOkHttp() {
        val client = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val url =
            "http://kdcourse.gkoudai.com/college-h5/pro/3849d4c9a329c35be1fee78e21aeb30c?e=1740627126&token=AHbk770lJANMYrckwN10alpzwVJzkUYosOg4sv1z:NK81iteFvNuYnltZUxrzjO3Onb8="

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0")
            .header("Referer", "http://kdcourse.gkoudai.com/")
            .build()

        MainScope().launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val body = response.body

                    if (body != null) {
                        // Move ALL body processing to IO thread
                        val bitmap = withContext(Dispatchers.IO) {
                            try {
                                // Read bytes in IO thread
                                val bytes = body.bytes()

                                // For debugging
                                Log.d("ImageLoader", "Received bytes length: ${bytes.size}")

                                // Save to a file (still on IO thread)
                                val tempFile = File.createTempFile("image", ".png", cacheDir)
                                tempFile.writeBytes(bytes)

                                // Decode the bitmap (still on IO thread)
                                BitmapFactory.decodeFile(tempFile.absolutePath)
                            } catch (e: Exception) {
                                Log.e("ImageLoader", "Error processing image data", e)
                                null
                            }
                        }

                        // Back on Main thread
                        if (bitmap != null) {
                            binding.ivImage.setImageBitmap(bitmap)
                        } else {
                            Log.e("ImageLoader", "Failed to decode bitmap")
                        }
                    }
                } else {
                    Log.e("ImageLoader", "Response not successful: ${response.code}")
                }
            } catch (e: Exception) {
                Log.e("ImageLoader", "Error loading image", e)
            }
        }
    }

}