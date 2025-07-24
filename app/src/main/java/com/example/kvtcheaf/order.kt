package com.example.kvtcheaf

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class order : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)


        val button = findViewById<Button>(R.id.b2)
        button.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        val nav=findViewById<BottomNavigationView>(R.id.bottom_navigation1 )
        nav.selectedItemId = R.id.nav_you
        nav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this,"Home Clicked", Toast.LENGTH_SHORT).show()
                    intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_you -> {
                    Toast.makeText(this, "You clicked", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this, order::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_cart->{
                    Toast.makeText(this,"cart clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_menu->{
                    Toast.makeText(this,"menu clicked",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }

        }
    }
}