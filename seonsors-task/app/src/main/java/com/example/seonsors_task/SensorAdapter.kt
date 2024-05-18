package com.example.seonsors_task
import android.annotation.SuppressLint
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SensorAdapter(private var sensors: List<Sensor>) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return SensorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensors[position]
        holder.nameTextView.text = sensor.name
        holder.typeTextView.text = sensor.stringType
    }

    override fun getItemCount() = sensors.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateSensors(newSensors: List<Sensor>) {
        sensors = newSensors
        notifyDataSetChanged()
    }

    class SensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(android.R.id.text1)
        val typeTextView: TextView = view.findViewById(android.R.id.text2)
    }
}