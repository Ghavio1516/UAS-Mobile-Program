package com.ghavio.sportapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        coroutineScope.launch {
            delay(3000) // 5000 milliseconds (5 seconds) delay
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        // Check if user is already logged in or not
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userEmail = sharedPref.getString("USER_EMAIL", null)

        val intent = if (userEmail != null) {
            // If user email is found, navigate to MainActivity
            Intent(this, MainActivity::class.java)
        } else {
            // Otherwise, navigate to LoginActivity
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
