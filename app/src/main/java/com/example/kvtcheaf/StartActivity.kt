package com.example.kvtcheaf

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        // Add a small delay for splash effect
        Handler(Looper.getMainLooper()).postDelayed({

            val currentUser = auth.currentUser

            if (currentUser != null) {
                // User already logged in → go to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // No user logged in → go to Login screen
                startActivity(Intent(this, Selection_page::class.java))
            }

            finish() // close splash so user can't return here

        }, 2000) // 2 second splash
    }
}
