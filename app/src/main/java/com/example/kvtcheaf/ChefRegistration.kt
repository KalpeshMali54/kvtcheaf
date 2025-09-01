package com.example.kvtcheaf

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ChefRegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance().getReference("chefs")
    private val storage = FirebaseStorage.getInstance().reference

    private lateinit var profileImage: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_registration)

        // Firebase Auth
        auth = FirebaseAuth.getInstance()

        // UI references
        val nameEt = findViewById<TextInputEditText>(R.id.eT1)
        val emailEt = findViewById<TextInputEditText>(R.id.et2)
        val passwordEt = findViewById<TextInputEditText>(R.id.eT3)
        val specialtyEt = findViewById<TextInputEditText>(R.id.eT4)
        val pricingEt = findViewById<TextInputEditText>(R.id.eT5)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)

        val passwordLayout = findViewById<TextInputLayout>(R.id.textInputlayout)
        val signupBtn = findViewById<Button>(R.id.btn2)
        val signinBtn = findViewById<Button>(R.id.button2)

        profileImage = findViewById(R.id.profileImage)
        val pickImageBtn = findViewById<ImageButton>(R.id.pickImageBtn)

        // ✅ Image picker
        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                // Preview using Glide
                Glide.with(this).load(it).circleCrop().into(profileImage)
            }
        }

        pickImageBtn.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // ✅ Signup button
        signupBtn.setOnClickListener {
            val name = nameEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()
            val specialty = specialtyEt.text.toString().trim()
            val pricing = pricingEt.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                specialty.isEmpty() || pricing.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!checkBox.isChecked) {
                checkBox.buttonTintList =
                    ColorStateList.valueOf(resources.getColor(android.R.color.holo_red_dark))
                Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Register with FirebaseAuth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid ?: return@addOnSuccessListener

                    // ✅ Upload photo if selected
                    if (selectedImageUri != null) {
                        val ref = storage.child("chef_profiles/$uid.jpg")
                        ref.putFile(selectedImageUri!!)
                            .addOnSuccessListener {
                                ref.downloadUrl.addOnSuccessListener { photoUrl ->
                                    saveChef(uid, name, email, specialty, pricing, photoUrl.toString())
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Photo upload failed", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        saveChef(uid, name, email, specialty, pricing, null)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Signup failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // ✅ Signin redirect
        signinBtn.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }
    }

    private fun saveChef(
        uid: String,
        name: String,
        email: String,
        specialty: String,
        pricing: String,
        photoUrl: String?
    ) {
        val chef = ChefProfile(name, email, specialty, pricing, photoUrl)
        database.child(uid).setValue(chef).addOnSuccessListener {
            Toast.makeText(this, "Chef Registered Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to save chef", Toast.LENGTH_SHORT).show()
        }
    }
}

// ✅ Chef model
data class ChefProfile(
    val name: String? = null,
    val email: String? = null,
    val specialty: String? = null,
    val pricing: String? = null,
    val photoUrl: String? = null
)
