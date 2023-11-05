package com.example.randomfilmselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var filmName: TextView
    private lateinit var selectButton: Button
    private lateinit var moviesStack: Stack<String>
    private lateinit var resetButton: Button


    private fun getMovies(): Stack<String> {
        val filmsList: MutableList<String> = resources.getStringArray(R.array.movies).toMutableList()
        filmsList.shuffle()
        val filmsStack = Stack<String>()
        filmsStack.addAll(filmsList)
        return filmsStack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filmName = findViewById(R.id.filmTitle)
        selectButton = findViewById(R.id.selectFilmButton)
        resetButton = findViewById(R.id.resetFilmsButton)
        moviesStack = getMovies()

        selectButton.setOnClickListener {
            if (!moviesStack.isEmpty()){
                val result:String = moviesStack.pop()
                filmName.text = result
            } else {
                filmName.text = resources.getString(R.string.all_movies_watched)
            }
        }

        resetButton.setOnClickListener {
            moviesStack = getMovies()
            filmName.text = resources.getString(R.string.films_list_reset)
        }

    }


}