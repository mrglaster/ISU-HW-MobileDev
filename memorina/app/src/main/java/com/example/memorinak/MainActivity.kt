package com.example.memorinak

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val GRID_SIZE = 4
    }

    private val cardsImages = arrayOf(
        R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4,
        R.drawable.i5, R.drawable.i6, R.drawable.i7, R.drawable.i8
    )
    private val cardsImagesIndices = (1..8).flatMap { listOf(it, it) }.toTypedArray()
    private var clickedSecond: ImageView? = null
    private var clickedFirst: ImageView? = null
    private var pairsFound: Int = 0
    private val cardsViews = ArrayList<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardsImagesIndices.shuffle()

        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat()

        for (i in 1..(GRID_SIZE * GRID_SIZE)) {
            cardsViews.add(
                ImageView(applicationContext).apply {
                    setImageResource(R.drawable.squarecat)
                    layoutParams = params
                    val currentIndex = cardsImagesIndices[i - 1]
                    tag = "i$currentIndex"
                    setOnClickListener(colorListener)
                }
            )
        }

        val rows = Array(GRID_SIZE) { LinearLayout(applicationContext) }
        for ((count, view) in cardsViews.withIndex()) {
            val row: Int = count / GRID_SIZE
            rows[row].addView(view)
        }

        for (row in rows) {
            layout.addView(row)
        }

        setContentView(layout)
    }

    private suspend fun setBackgroundWithDelay(v: ImageView) {
        val clickedIndex = v.tag.toString().filter { it.isDigit() }.toInt() - 1
        v.setImageResource(cardsImages[clickedIndex])
        delay(500)

        if (clickedFirst == null) {
            clickedFirst = v
        } else {
            clickedSecond = v
        }

        when {
            clickedFirst != null && clickedSecond != null -> {
                if (clickedFirst!!.tag == clickedSecond!!.tag) {
                    clickedFirst!!.visibility = View.INVISIBLE
                    clickedSecond!!.visibility = View.INVISIBLE
                    clickedFirst = null
                    clickedSecond = null
                    pairsFound += 1

                    if (pairsFound == GRID_SIZE * GRID_SIZE / 2) {
                        Toast.makeText(this, "You won! YaY!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    clickedFirst!!.setImageResource(R.drawable.squarecat)
                    clickedSecond!!.setImageResource(R.drawable.squarecat)
                    clickedFirst = null
                    clickedSecond = null
                }
            }
        }
    }

    private val colorListener = View.OnClickListener {
        lifecycleScope.launch(Dispatchers.Main) {
            setBackgroundWithDelay(it as ImageView)
        }
    }
}
