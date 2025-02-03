package com.example.kvtcheaf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class cheaf1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheaf1)
        val btn=findViewById<Button>(R.id.button)
        val btn1=findViewById<Button>(R.id.button3)
        btn.setOnClickListener {
            intent= Intent(this, addChart::class.java)
            startActivity(intent)
        }
        btn1.setOnClickListener {
            intent= Intent(this, BuyNOw::class.java)
            startActivity(intent)
        }
    }
}