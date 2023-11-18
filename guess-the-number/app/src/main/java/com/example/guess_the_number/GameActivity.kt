package com.example.guess_the_number

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class GameActivity : AppCompatActivity() {
    private lateinit var yesButton:Button
    private lateinit var noButton: Button
    private lateinit var question:TextView
    private var begin:Int = 0
    private var end :Int= 0
    private var potentialNumber:Int = -1
    private var isEnd:Boolean = false


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)
        question = findViewById(R.id.question)
        begin = intent.getIntExtra("begin", 0)
        end = intent.getIntExtra("end", 100)
        yesButton.setOnClickListener {
            if (!isEnd){
                begin = potentialNumber
                updateQuestion()
            } else {
                question.text = "I won! YaY!"
            }
        }
        noButton.setOnClickListener {
            if (!isEnd){
                end = potentialNumber
                updateQuestion()
            } else {
                question.text = "IDK Then"
            }
        }
        updateQuestion()
    }

    fun updateQuestion(){
        Log.d("END", end.toString())
        Log.d("BEGIN", begin.toString())
        if (end - begin > 1){
            potentialNumber = (end + begin) / 2
            val questionString = "Is the number greater than $potentialNumber ?"
            question.text = questionString
        } else {
            isEnd = true
            potentialNumber =  end
            val questionString = "Is it the number $potentialNumber ?"
            question.text = questionString
        }
    }




}