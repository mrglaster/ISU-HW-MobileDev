package com.example.sea_fight

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var regenerateButton: Button
    companion object {
        const val BOARD_SIZE = 10
        val SHIPS = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gridLayout = findViewById(R.id.gridLayout)
        regenerateButton = findViewById<Button>(R.id.button)
        regenerateButton.setOnClickListener { initGameField() }
        initGameField()
    }

    private fun initGameField() {
        gridLayout.columnCount = BOARD_SIZE
        gridLayout.rowCount = BOARD_SIZE

        val board = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }

        for (size in SHIPS) {
            var isPlaced = false
            while (!isPlaced) {
                val orientation = (0..1).random() // 0 for horizontal, 1 for vertical
                val row = (0 until BOARD_SIZE).random()
                val col = (0 until BOARD_SIZE).random()
                if (canPut(board, row, col, size, orientation)) {
                    put(board, row, col, size, orientation)
                    isPlaced = true
                }
            }
        }

        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                val textView = TextView(this).apply {
                    textSize = 18f
                    gravity = Gravity.CENTER
                    setBackgroundColor(if (board[i][j] == 1) Color.GRAY else ContextCompat.getColor(this@MainActivity, R.color.cyan))
                }
                val params = GridLayout.LayoutParams().apply {
                    width = 50
                    height = 50
                    setMargins(3, 3, 3, 3)
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                }
                textView.layoutParams = params
                gridLayout.addView(textView)
            }
        }
    }

    private fun canPut(board: Array<IntArray>,  row: Int,  col: Int,  size: Int,  orientation: Int): Boolean {
        if (orientation == 0 && col + size > BOARD_SIZE) return false
        if (orientation == 1 && row + size > BOARD_SIZE) return false

        for (i in -1..size) {
            for (j in -1..1) {
                val currentRow = row + i
                val currentCol= col + j
                if (currentRow in 0 until BOARD_SIZE && currentCol in 0 until BOARD_SIZE) {
                    if (board.getOrNull(currentRow)?.getOrNull(currentCol) == 1) return false
                }
            }
        }
        return true
    }



    private fun put(board: Array<IntArray>, row: Int, col: Int, size: Int, orientation: Int) {
        for (i in 0 until size) {
            if (orientation == 0) board[row][col + i] = 1
            if (orientation == 1) board[row + i][col] = 1
        }
    }
}
