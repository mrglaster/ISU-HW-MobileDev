package com.example.guess_the_number

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var beginInput:EditText
    private lateinit var endInput:EditText
    private lateinit var startGameButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        beginInput = findViewById(R.id.beginNumber)
        endInput = findViewById(R.id.endNumber)
        startGameButton = findViewById(R.id.button)
        startGameButton.setOnClickListener {
            if (beginInput.text.toString().isEmpty() || endInput.text.toString().isEmpty()){
                Toast.makeText(this, "You should fill all the fields!", Toast.LENGTH_SHORT).show()
            } else {
                var begin:Int = Integer.parseInt(beginInput.text.toString())
                var end:Int = Integer.parseInt(endInput.text.toString())
                if (begin == end) {
                    Toast.makeText(this, "Begind and End can't have the same value", Toast.LENGTH_SHORT).show()
                } else  if (abs(begin - end) > 1){

                    if (begin > end){
                        Toast.makeText(this, "Your Begin is greater than End. We'll swap them", Toast.LENGTH_SHORT).show()
                        val temp:Int = end
                        end = begin
                        begin = temp
                    }
                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("begin", begin)
                    intent.putExtra("end", end)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "There should be at least one number between Begin and End!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}