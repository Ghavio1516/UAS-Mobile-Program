package com.ghavio.sportapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.os.Handler
import android.os.Looper
import android.app.ActivityOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var welcomeTextView: TextView
    private lateinit var detailButton: Button
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var location: LatLng
    private val typingSpeed = 200L // Kecepatan mengetik dalam milidetik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        // Menghubungkan view dengan ID
        welcomeTextView = findViewById(R.id.welcomeTextView)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Ambil username dari SharedPreferences
        val username = sharedPreferences.getString("username", "User") ?: "User"
        val welcomeMessage = "Welcome, $username!"

        // Set animasi mengetik
        animateTyping(welcomeMessage)

       }
    private fun animateTyping(message: String) {
        val handler = Handler(Looper.getMainLooper())
        val delay = typingSpeed
        var index = 0

        handler.post(object : Runnable {
            override fun run() {
                if (index < message.length) {
                    welcomeTextView.text = message.substring(0, index + 1)
                    index++
                    handler.postDelayed(this, delay)
                }
            }
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Contoh lokasi: Jakarta
        location = LatLng(-6.368917764796334, 106.82366418900865)
        googleMap.addMarker(MarkerOptions().position(location).title("PNJ"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
