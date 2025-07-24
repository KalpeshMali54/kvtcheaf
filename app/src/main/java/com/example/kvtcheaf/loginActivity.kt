package com.example.kvtcheaf

import android.annotation.SuppressLint
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class loginActivity : AppCompatActivity() {
    lateinit var database:DatabaseReference


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val signup=findViewById<Button>(R.id.btn2)
        val editText=findViewById<TextInputEditText>(R.id.eT1)
        val editText1=findViewById<TextInputEditText>(R.id.et2)
        val editText2=findViewById<TextInputEditText>(R.id.eT3)
        val editText3=findViewById<TextInputEditText>(R.id.eT4)
        val singin=findViewById<Button>(R.id.button2)
        val checkBox=findViewById<CheckBox>(R.id.checkBox)
        val textInputLayout=findViewById<TextInputLayout>(R.id.textInputlayout)
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        fun isValidPassword(password: String): Boolean {
            val passwordPattern = Regex(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
            )
            return password.matches(passwordPattern)
        }

        signup.setOnClickListener{
            val name = editText.text.toString()
                val email = editText1.text.toString()
                val password = editText2.text.toString()
                val ID = editText3.text.toString()

                val User = user(name, email, password, ID)
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && ID.isNotEmpty()) {
                    if (isValidEmail(email)) {
                        if (isValidPassword(password)) {
                            if(checkBox.isChecked) {
                                database = FirebaseDatabase.getInstance().getReference("user")
                                editText.text?.clear()
                                editText1.text?.clear()
                                editText2.text?.clear()
                                editText3.text?.clear()
                                database.child(ID).setValue(User).addOnSuccessListener {
                                    Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT)
                                        .show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("USER_ID", ID)
                                    startActivity(intent)

                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "falid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            else{
                                checkBox.buttonTintList= ColorStateList.valueOf(Color.RED)
                                Toast.makeText(this,"Accept T&C",Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            textInputLayout.error = "Password must be 8+ characters, include upper/lower case, number & special char"
                        }
                    }
                    else{
                        Toast.makeText(this,"Plz enter valid email", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "Plz enter all Details first", Toast.LENGTH_SHORT).show()
                }


        }
        singin.setOnClickListener{
            intent=Intent(this,Singin::class.java)
            startActivity(intent)
        }


    }
}
