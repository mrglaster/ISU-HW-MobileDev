package com.example.layout_adaptive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var pictures: IntArray
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pictures = intArrayOf(R.drawable.car1, R.drawable.car2, R.drawable.car3)

        adapter = ArrayAdapter.createFromResource(this, R.array.pictures, R.layout.item)
        val spinner = findViewById<Spinner>(R.id.pictures_list)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    fun onChangePictureClick(v: View) {
        val iv = findViewById<ImageView>(R.id.picture)
        currentPosition = (currentPosition + 1) % pictures.size
        iv.setImageResource(pictures[currentPosition])
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this, "Item selected: $position", Toast.LENGTH_SHORT).show()
        val iv = findViewById<ImageView>(R.id.picture)
        iv.setImageResource(pictures[position])
        currentPosition = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "Item not selected", Toast.LENGTH_SHORT).show()
    }
}
