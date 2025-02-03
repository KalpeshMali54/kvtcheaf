package com.example.kvtcheaf

import android.R.layout
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY = "com.example.kvtcheaf.MainActivity.KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_contain)

        val imageView = findViewById<ImageView>(R.id.imageView2)
        val imageView1 = findViewById<ImageView>(R.id.imageView3)
        val imageView2 = findViewById<ImageView>(R.id.imageView4)
        val imageView3 = findViewById<ImageView>(R.id.imageView5)
        val imageView4 = findViewById<ImageView>(R.id.imageView6)
        val imageView5 = findViewById<ImageView>(R.id.imageView7)
        val imageView6 = findViewById<ImageView>(R.id.imageView8)

        imageView.setOnClickListener {
            intent = Intent(this, cheaf1::class.java)
            startActivity(intent)
        }
        imageView1.setOnClickListener {
            intent = Intent(this, cheaf2::class.java)
            startActivity(intent)
        }
        imageView2.setOnClickListener {
            intent = Intent(this, chef3::class.java)
            startActivity(intent)
        }
        imageView3.setOnClickListener {
            intent = Intent(this, chef4::class.java)
            startActivity(intent)
        }
        imageView4.setOnClickListener {
            intent = Intent(this, chef5::class.java)
            startActivity(intent)
        }
        imageView5.setOnClickListener {
            intent = Intent(this, chef6::class.java)
            startActivity(intent)
        }
        imageView6.setOnClickListener {
            intent = Intent(this, chef7::class.java)
            startActivity(intent)
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart -> {
                intent = Intent(this, addChart::class.java)
                startActivity(intent)
            }

            R.id.myorder -> {
                intent = Intent(this, order::class.java)
                startActivity(intent)
            }


            else -> super.onOptionsItemSelected(item)
        } as Boolean
    }

}




