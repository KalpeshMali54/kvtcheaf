package com.example.kvtcheaf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class order : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        val tVorder = findViewById<TextView>(R.id.tVorder)

        val orderofCustomber = intent.getStringExtra(MainActivity.KEY)
        tVorder.text = "Your Order \n"+orderofCustomber.toString()+"will be placed"
        val button = findViewById<Button>(R.id.b2)
        button.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}