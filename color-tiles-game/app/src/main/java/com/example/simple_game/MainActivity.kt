package com.example.simple_game
import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


data class Coord(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    private var SOLVE_AUTOMATICLY: Boolean = false

    private lateinit var tiles: Array<Array<View>>
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        val rowsCount:Int = 4
        val colsCount:Int = 4

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiles = Array(rowsCount) { row ->
            Array(colsCount) { col ->
                findViewById<View>(resources.getIdentifier("t$row$col", "id", packageName))
            }
        }
        fillFieldRandom()
        Log.d("ROWS FOUND: ", tiles.size.toString())
        Log.d("COLS FOUND: ", tiles[0].size.toString())
        if (SOLVE_AUTOMATICLY){
            solvePuzzle()
            Log.d("WIN STATUS", "WON BY MACHINE")
        }
    }

    private fun getCoordFromString(s: String): Coord {
        val x:Int = s[0] - '0'
        val y:Int = s[1] - '0'
        Log.d("ORIGIN: ", "X: $x Y: $y")
        return Coord(x, y)
    }

    private fun getColor(view: View): ColorDrawable{
        return view.background as ColorDrawable
    }

    private fun getColorName(view: View):String{
        val color = getColor(view)
        val bright = resources.getColor(R.color.bright)
        if (color.color == bright){
            return "Bright"
        }
        return "Dark"
    }

    private fun changeColor(view: View) {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        val drawable = getColor(view)
        if (drawable.color ==brightColor ) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.getTag().toString())
        changeColor(v)
        for (i in 0..3) {
            changeColor(tiles[coord.x][i])
            changeColor(tiles[i][coord.y])
        }
        if (checkVictory()){
            if (!SOLVE_AUTOMATICLY){
                Log.d("WIN STATUS", "WON BY HUMAN")
            }
            Toast.makeText(this, "The team of color " + getColorName(tiles[0][0]) + " has won!", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkVictory():Boolean {
        val leadingCover = getColor(tiles[0][0]).color
        for ( i in 0..3){
           for (j in 0..3){
               if (getColor(tiles[i][j]).color != leadingCover){
                   return false
               }
           }
       }
        return true
    }

    private fun fillFieldRandom(){
        for (i in 0..3){
            for (j in 0..3){
                val randomNumber = Random.nextInt(0, 100)
                if (randomNumber > 85){
                    changeColor(tiles[i][j])
                }
            }
        }
    }

    private fun solvePuzzle() {
        val rows = tiles.size
        val cols = tiles[0].size
        while (!checkVictory()) {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    if (getColor(tiles[i][j]).color == resources.getColor(R.color.dark)) {
                        tiles[i][j].callOnClick()
                    }
                }
            }
        }
    }

}