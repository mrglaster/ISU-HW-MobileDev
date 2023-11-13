package com.example.tickesapp

import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var departureSpinner:Spinner
    private lateinit var destinationSpinner:Spinner
    private lateinit var selectDepartureDateButton: Button
    private lateinit var selectReturnDateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cityList: MutableList<String> = resources.getStringArray(R.array.cities).toMutableList()


        departureSpinner = findViewById(R.id.departureCity)
        destinationSpinner = findViewById(R.id.destinationCity)
        selectDepartureDateButton = findViewById(R.id.selectDepartureDate)
        selectReturnDateButton = findViewById(R.id.selectReturnDate)
        val myCalendar = Calendar.getInstance()

        val departureDateDialog = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setDateText(myCalendar, selectDepartureDateButton)
        }

        val returnDateDialog = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setDateText(myCalendar, selectReturnDateButton)
        }

        val adapterDeparture = ArrayAdapter.createFromResource(this,
            R.array.cities, android.R.layout.simple_spinner_item)

        val adapterDestination = ArrayAdapter.createFromResource(this,
            R.array.cities, android.R.layout.simple_spinner_item)


        adapterDeparture.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        adapterDestination.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)


        departureSpinner.adapter = adapterDeparture
        destinationSpinner.adapter = adapterDestination
        departureSpinner.setSelection(0)
        destinationSpinner.setSelection(1)


        departureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = departureSpinner.selectedItem.toString()
                val updatedList = cityList.filter { it != selectedItem }.toMutableList()
                val updatedAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    updatedList
                )

                updatedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                destinationSpinner.adapter = updatedAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        }

        selectDepartureDateButton.setOnClickListener {
            DatePickerDialog(this, departureDateDialog, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        selectReturnDateButton.setOnClickListener {
            DatePickerDialog(this, returnDateDialog, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


    }
    private fun setDateText(myCalendar: Calendar?, direction: Button) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK )
        if (myCalendar != null) {
            direction.setBackgroundColor(Color.TRANSPARENT)
            direction.setTextColor(Color.BLACK)
            direction.text= myCalendar.time.toString()
        }
    }
}