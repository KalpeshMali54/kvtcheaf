package com.example.kvtcheaf

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChefDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_detail)

        val name = intent.getStringExtra("CHEF_NAME")
        val imageResId = intent.getIntExtra("CHEF_IMAGE", R.drawable.cheaf1)

        val chefNameTextView = findViewById<TextView>(R.id.chefNameDetail)
        val chefImageView = findViewById<ImageView>(R.id.chefImageDetail)

        chefNameTextView.text = name
        chefImageView.setImageResource(imageResId)

        // Button click listeners
        val bookButton = findViewById<Button>(R.id.btnBookNow)
        val messageButton = findViewById<Button>(R.id.btnMessage)

        bookButton.setOnClickListener {
            Toast.makeText(this, "Booking ${name}", Toast.LENGTH_SHORT).show()
            // You can launch booking page here
        }

        messageButton.setOnClickListener {
            Toast.makeText(this, "Messaging ${name}", Toast.LENGTH_SHORT).show()
            // You can launch messaging/chat page here
        }
    }
}