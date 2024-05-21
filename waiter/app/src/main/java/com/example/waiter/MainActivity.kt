package com.example.waiter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class MainActivity : AppCompatActivity() {
    private lateinit var timeText: TextView
    private lateinit var tiredButton: Button
    private var minutesCount = 0

    private lateinit var batteryReceiver: BroadcastReceiver
    private lateinit var timeReceiver: BroadcastReceiver

    private val toastText = "Ждун устал созерцать"
    private val lowBatteryText = "накормите Ждуна,силы на исходе!"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        timeText = findViewById<TextView>(R.id.timeText)
        tiredButton = findViewById<Button>(R.id.tiredButton)

        tiredButton.setOnClickListener {
            unregisterReceivers()
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }

        timeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                minutesCount++
                updateTextView()
            }
        }
        registerReceiver(timeReceiver, IntentFilter(Intent.ACTION_TIME_TICK))

        batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPct = level?.times(100)?.div(scale ?: 1)

                if (batteryPct != null && batteryPct < 15) {
                    timeText.text = lowBatteryText
                } else {
                    updateTextView()
                }
            }
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onResume() {
        super.onResume()
        updateTextView()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextView() {
        timeText.text = "Время созерцания: $minutesCount мин."
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceivers()
    }

    private fun unregisterReceivers() {
        unregisterReceiver(timeReceiver)
        unregisterReceiver(batteryReceiver)
    }
}