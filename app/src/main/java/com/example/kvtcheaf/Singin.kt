package com.example.kvtcheaf

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.kvtcheaf.R.id.btn5
import com.example.kvtcheaf.R.id.checkBox2
import com.example.kvtcheaf.R.id.pass
import com.example.kvtcheaf.R.id.signin
import com.example.kvtcheaf.R.id.textView3
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Singin : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_singin)

        val user_id = findViewById<TextInputEditText>(signin)
        val password=findViewById<TextInputEditText>(pass)
        val singin = findViewById<Button>(btn5)
        val checkBox=findViewById<CheckBox>(checkBox2)
        val term=findViewById<TextView>(textView3)

        term.setOnClickListener{
            intent=Intent(this,terms::class.java)
            startActivity(intent)
        }



        singin.setOnClickListener {
            if(checkBox.isChecked) {
                val id = user_id.text.toString()
                val Password= password.text.toString()
                if (id.isNotEmpty() && Password.isNotEmpty()) {
                    readData(id,Password)

                } else if (id.isEmpty()) {
                    Toast.makeText(this, "Plese enter the user id", Toast.LENGTH_SHORT).show()
                }
                else if (Password.isEmpty()){
                    Toast.makeText(this,"enter the password first",Toast.LENGTH_SHORT).show()
                }


            }
            else{
                checkBox.buttonTintList= ColorStateList.valueOf(Color.RED)
                Toast.makeText(this,"Accept T&C",Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun readData(id: String, password: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        databaseReference.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val storedPassword = it.child("password").value.toString()
                if (storedPassword == password) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("USER_ID", id)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User does not exist. Please sign up first.", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to connect to DB", Toast.LENGTH_SHORT).show()
        }
    }


}


