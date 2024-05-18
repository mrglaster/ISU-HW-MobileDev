package com.example.seonsors_task

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAdapter: SensorAdapter
    private lateinit var sensorCategorySpinner: Spinner
    private lateinit var sensorRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorCategorySpinner = findViewById(R.id.sensorCategorySpinner)
        sensorRecyclerView = findViewById(R.id.sensorRecyclerView)

        sensorRecyclerView.layoutManager = LinearLayoutManager(this)
        sensorAdapter = SensorAdapter(emptyList())
        sensorRecyclerView.adapter = sensorAdapter

        val categories = listOf("Датчики окружающей среды", "Датчики положения устройства", "Датчики состояния человека")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sensorCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateSensorList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                updateSensorList(0)
            }
        }

    }

    private fun updateSensorList(category: Int) {
        val sensors = when (category) {
            0 -> sensorManager.getSensorList(Sensor.TYPE_ALL).filter { it.type in environmentSensors }
            1 -> sensorManager.getSensorList(Sensor.TYPE_ALL).filter { it.type in positionSensors }
            2 -> sensorManager.getSensorList(Sensor.TYPE_ALL).filter { it.type in humanSensors }
            else -> emptyList()
        }
        sensorAdapter.updateSensors(sensors)
    }

    companion object {
        private val environmentSensors = listOf(
            Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_PRESSURE
        )

        private val positionSensors = listOf(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_PROXIMITY
        )

        private val humanSensors = listOf(
            Sensor.TYPE_HEART_RATE,
            Sensor.TYPE_STEP_COUNTER,
            Sensor.TYPE_STEP_DETECTOR
        )
    }
}