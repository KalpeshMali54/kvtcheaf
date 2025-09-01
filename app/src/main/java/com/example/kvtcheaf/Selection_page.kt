package com.example.kvtcheaf

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

 class Selection_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_selection_page)
        val cardUser = findViewById<CardView>(R.id.cardUser)
        val cardChef = findViewById<CardView>(R.id.cardChef)

        cardUser.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java)) // go to User signup
        }

        cardChef.setOnClickListener {
            startActivity(Intent(this, ChefRegistrationActivity::class.java)) // go to Chef signup
        }

    }
}