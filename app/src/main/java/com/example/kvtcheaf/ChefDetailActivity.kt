package com.example.kvtcheaf

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChefDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.DONUT)
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
            var intent= Intent(this, order::class.java)
            startActivity(intent)
        }

        messageButton.setOnClickListener {
            Toast.makeText(this, "Messaging ${name}", Toast.LENGTH_SHORT).show()
            val phoneNumber = "919549165951" // Replace with your business number in international format
            val url = "https://wa.me/$phoneNumber"

            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                intent.setPackage("com.whatsapp") // Optional: forces open in WhatsApp only
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}