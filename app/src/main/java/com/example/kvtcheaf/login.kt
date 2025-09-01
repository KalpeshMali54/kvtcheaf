package com.example.kvtcheaf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth



class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_singin)

        auth = FirebaseAuth.getInstance()

        val Email = findViewById<TextInputEditText>(R.id.et2)
        val password = findViewById<TextInputEditText>(R.id.eT3)
        val singin = findViewById<Button>(R.id.btn1)
        val singup=findViewById<Button>(R.id.btn2)

        val term = findViewById<TextView>(R.id.textView3)

        // If already logged in → go to Main


        term.setOnClickListener {
            startActivity(Intent(this, terms::class.java))
        }

        singin.setOnClickListener {

                val email = Email.text.toString().trim()
                val pass = password.text.toString().trim()

                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (pass.isEmpty()) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Firebase Authentication login
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }

        }
        singup.setOnClickListener {
            startActivity(Intent(this, Selection_page::class.java))
        }
    }
}
