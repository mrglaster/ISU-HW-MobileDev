package com.example.bulbs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var lampArray: Array<Array<ImageView>>
    private var lampStates: Array<Array<Boolean>> = Array(4) { Array(4) { false } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeLamps()
        generateLamps()
    }

    @SuppressLint("DiscouragedApi")
    private fun initializeLamps() {
        lampArray = Array(4) { row ->
            Array(4) { col ->
                findViewById(resources.getIdentifier("lamp$row$col", "id", packageName))
            }
        }
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                lampArray[i][j].setOnClickListener(this)
            }
        }
        updateLampViews()
    }

    private fun generateLamps() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val randomState = Random.nextBoolean()
                lampStates[i][j] = randomState
                if (randomState) {
                    lampArray[i][j].setImageResource(R.drawable.ic_lamp_on)
                } else {
                    lampArray[i][j].setImageResource(R.drawable.ic_lamp_off)
                }
            }
        }
    }

    override fun onClick(view: View?) {
        view?.let { lamp ->
            for (i in 0 until 4) {
                for (j in 0 until 4) {
                    if (lampArray[i][j] == lamp) {
                        updateLampsStates(i, j)
                        break
                    }
                }
            }
            updateLampViews()
            if (hasWon()) {
                Toast.makeText(this, "YOU WON!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateLampsStates(row: Int, col: Int) {
        for (i in 0 until 4) {
            lampStates[row][i] = !lampStates[row][i]
            lampStates[i][col] = !lampStates[i][col]
        }
        lampStates[row][col] = !lampStates[row][col]
    }

    private fun updateLampViews() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (lampStates[i][j]) {
                    lampArray[i][j].setImageResource(R.drawable.ic_lamp_on)
                } else {
                    lampArray[i][j].setImageResource(R.drawable.ic_lamp_off)
                }
            }
        }
    }

    private fun hasWon(): Boolean {
        val firstState = lampStates[0][0]
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (lampStates[i][j] != firstState) {
                    return false
                }
            }
        }
        return true
    }

}