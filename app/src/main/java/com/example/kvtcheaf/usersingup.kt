package com.example.kvtcheaf

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

private val TextView.message: String
    get() = this.text.toString()

class loginActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private var selectedImageUri: Uri? = null

    private lateinit var profileImage: ImageView
    private lateinit var pickImageBtn: ImageButton

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("user")
        storage = FirebaseStorage.getInstance()

        val signup = findViewById<Button>(R.id.btn2)
        val editText = findViewById<TextInputEditText>(R.id.eT1) // name
        val editText1 = findViewById<TextInputEditText>(R.id.et2) // email
        val editText2 = findViewById<TextInputEditText>(R.id.eT3) // password
        val editText3 = findViewById<TextInputEditText>(R.id.eT4) // ID
        val signin = findViewById<Button>(R.id.button2)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputlayout)

        profileImage = findViewById(R.id.profileImage)
        pickImageBtn = findViewById(R.id.pickImageBtn)

        // pick image
        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                profileImage.setImageURI(it)
            }
        }

        pickImageBtn.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            val passwordPattern = Regex(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
            )
            return password.matches(passwordPattern)
        }

        signup.setOnClickListener {
            val name = editText.text.toString().trim()
            val email = editText1.text.toString().trim()
            val password = editText2.text.toString().trim()
            val ID = editText3.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && ID.isNotEmpty()) {
                if (isValidEmail(email)) {
                    if (isValidPassword(password)) {
                        if (checkBox.isChecked) {
                            // ✅ Step 1: create account in FirebaseAuth
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { authResult ->
                                    val uid = authResult.user?.uid ?: return@addOnSuccessListener

                                    // ✅ Step 2: upload photo if selected
                                    if (selectedImageUri != null) {
                                        val ref = storage.reference.child("profile_photos/$uid.jpg")
                                        ref.putFile(selectedImageUri!!)
                                            .addOnSuccessListener {
                                                ref.downloadUrl.addOnSuccessListener { photoUrl ->
                                                    saveUser(uid, ID, name, email, photoUrl.toString())
                                                }
                                            }
                                    } else {
                                        saveUser(uid, ID, name, email, null)
                                    }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Signup failed: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            checkBox.buttonTintList = ColorStateList.valueOf(Color.RED)
                            Toast.makeText(this, "Accept T&C", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        textInputLayout.error =
                            "Password must be 8+ characters, include upper/lower case, number & special char"
                    }
                } else {
                    Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        signin.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }
    }

    private fun saveUser(uid: String, ID: String, name: String, email: String, photoUrl: String?) {
        val user = User(name, email, ID, photoUrl)
        database.child(uid).setValue(user).addOnSuccessListener {
            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USER_ID", uid)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to save user", Toast.LENGTH_SHORT).show()
        }
    }
}

// ✅ New User model (without storing password in DB)
data class User(
    val name: String? = null,
    val email: String? = null,
    val ID: String? = null,
    val photoUrl: String? = null
)
