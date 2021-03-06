package com.example.dankmemer

import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.android.volley.toolbox.Volley.newRequestQueue as volleyNewRequestQueue

class MainActivity : AppCompatActivity() {


    var currentImageurl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()

    }

    private fun loadmeme(){
        val pgbar = findViewById<ProgressBar>(R.id.progressbar)
        pgbar.visibility = View.VISIBLE
        val queue = volleyNewRequestQueue(this)
        currentImageurl = "https://meme-api.herokuapp.com/gimme"
        val imageView = findViewById<ImageView>(R.id.imageView)
// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImageurl, null,
            Response.Listener{ response ->
                // Display the first 500 characters of the response string.
                val currentImageurl = response.getString("url")
                Glide.with(this).load(currentImageurl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgbar.visibility = View.GONE
                        return false
                    }
                }).into(imageView)
            },
            Response.ErrorListener {
               Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun sharememe(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Checkout this cool meme I got from Reddit $currentImageurl")
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextmeme(view: android.view.View) {
        loadmeme()
    }
}