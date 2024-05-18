package com.example.crypto_curences

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var dogeUsd: TextView
    private lateinit var dogeRub: TextView
    private lateinit var btcUsd: TextView
    private lateinit var btcRub: TextView
    private lateinit var updateButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dogeUsd = findViewById(R.id.dogeUsd)
        dogeRub = findViewById(R.id.dogeRub)
        btcUsd = findViewById(R.id.btcUsd)
        btcRub = findViewById(R.id.btcRub)
        updateButton = findViewById(R.id.button)
        update()
        updateButton.setOnClickListener {
            update()
        }
    }


    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun update() {
        GlobalScope.launch(Dispatchers.IO) {
            val url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=DOGE,BTC&tsyms=USD,RUB"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val json = JSONObject(response.body.string())
                    val doge: JSONObject = json.getJSONObject("DOGE")
                    val btc: JSONObject = json.getJSONObject("BTC")

                    withContext(Dispatchers.Main) {
                        dogeUsd.text = "DOGE/USD: ${doge.getDouble("USD")}"
                        dogeRub.text = "DOGE/RUB: ${doge.getDouble("RUB")}"
                        btcUsd.text = "BTC/USD: ${btc.getDouble("USD")}"
                        btcRub.text = "BTC/RUB:  ${btc.getDouble("RUB")}"
                    }
                }
            } catch (e: IOException) {
                Toast.makeText(
                    applicationContext,
                    "DATA NOT AVAILABEL!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}