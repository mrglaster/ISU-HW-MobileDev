package com.example.geolocation
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.location.Location
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat



class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
        } else {
            handleLocationUpdate()
        }
        Log.d("LOC", "${locationManager.allProviders}")
    }

    private fun handleLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        val prv = locationManager.getBestProvider(Criteria(), true)
        if (prv != null) {
            val location = locationManager.getLastKnownLocation(prv)
            val btn = findViewById<Button>(R.id.updateButton)
            btn.setOnClickListener {
                if (location != null) {
                    displayCoord(location.latitude, location.longitude)
                }
                Log.d("LOC", "location set")
            }
        }
    }

    override fun onLocationChanged(loc: Location) {
        val lat = loc.latitude
        val lng = loc.longitude
        displayCoord(lat, lng)
        Log.d("LOC", "lat $lat long $lng")
    }

    fun displayCoord(latitude: Double, longitude: Double) {
        findViewById<TextView>(R.id.latitude).text = String.format("%.5f", latitude)
        findViewById<TextView>(R.id.longitude).text = String.format("%.5f", longitude)
    }

    override fun onProviderDisabled(provider: String) {
        Log.d("LOC", "Provider $provider disabled")
    }

    override fun onProviderEnabled(provider: String) {
        Log.d("LOC", "Provider $provider enabled")
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("LOC", "Location permission granted")
                handleLocationUpdate()
            } else {
                Log.d("LOC", "Location permission NOT granted")
            }
        }
    }
}