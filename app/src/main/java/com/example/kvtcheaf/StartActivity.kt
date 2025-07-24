package com.example.kvtcheaf

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        Handler().postDelayed({
            val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

            val intent = if (isLoggedIn) {
                val userId = sharedPreferences.getString("userId", null)
                Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_ID", userId)
                }
            } else {
                Intent(this, loginActivity::class.java)  // Make sure this is capital "L"
            }

            startActivity(intent)
            finish()

        },2000)

    }
}
