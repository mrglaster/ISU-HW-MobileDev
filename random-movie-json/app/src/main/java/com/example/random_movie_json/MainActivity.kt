package com.example.random_movie_json

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.InputStream
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var title:TextView
    private lateinit var year:TextView
    private lateinit var genre:TextView
    private lateinit var duration: TextView
    private lateinit var cover:ImageView
    private lateinit var moviesStack: Stack<JSONObject>
    private lateinit var randomButton: Button
    private lateinit var resetButton:  Button


    private fun getMovies(): Stack<JSONObject> {
        val inputStream: InputStream = resources.openRawResource(R.raw.movies)
        val sourceString = inputStream.bufferedReader().use { it.readText() }
        val sourceJson = JSONObject(sourceString)
        val array = sourceJson.getJSONArray("movies")
        val list = mutableListOf<JSONObject>()
        for (i in 0 until array.length()) {
            list.add(array.get(i) as JSONObject)
        }
        list.shuffle()
        val filmsStack = Stack<JSONObject>()
        filmsStack.addAll(list)
        return filmsStack
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = findViewById(R.id.movieTitle)
        year = findViewById(R.id.year)
        genre = findViewById(R.id.genre)
        duration = findViewById(R.id.duration)
        cover = findViewById(R.id.imageView)
        moviesStack = getMovies()
        randomButton = findViewById(R.id.randomButton)
        resetButton = findViewById(R.id.resetButton)

        randomButton.setOnClickListener {
            if (!moviesStack.isEmpty()){
                val movie:JSONObject = moviesStack.pop()
                title.text = "Title: " + movie.getString("name")
                year.text = "Year: " + movie.getString("year")
                duration.text = "Duration: " + movie.getString("duration")
                genre.text = "Genre: " + movie.getString("genre")
                val url:String = movie.getString("image")
                Picasso.get()
                    .load(url)
                    .into(cover)
            } else {
                title.text = "That's all folks!"
                val url:String = "https://i.ibb.co/30j6wjL/allf.jpg"
                year.text = "Year: - "
                duration.text = "Duration: - "
                genre.text = "Genre: - "
                Picasso.get()
                    .load(url)
                    .into(cover)
            }
        }

    }
}