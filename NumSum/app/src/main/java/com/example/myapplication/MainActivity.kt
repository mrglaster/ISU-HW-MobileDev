package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstNumber = findViewById<EditText>(R.id.first_number)
        val secondNumber = findViewById<EditText>(R.id.second_number)
        val resultText = findViewById<TextView>(R.id.result)
        val calculateButton = findViewById<Button>(R.id.button)

        calculateButton.setOnClickListener {
            val firstStr:String = firstNumber.text.toString();
            if (firstStr.isEmpty()){
                Toast.makeText(this, "The first number shouldn't be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val secondStr:String = secondNumber.text.toString();
                if (secondStr.isEmpty()){
                    Toast.makeText(this, "The second number shouldn't be empty!", Toast.LENGTH_SHORT).show()
                } else {
                    val result:Float = firstStr.toFloat() + secondStr.toFloat()
                    resultText.text = result.toString()
                }
            }
        }
    }
}