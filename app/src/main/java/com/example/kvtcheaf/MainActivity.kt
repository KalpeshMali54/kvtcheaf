package com.example.kvtcheaf


import Chef
import ChefAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: User
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val KEY = "com.example.kvtcheaf.MainActivity.KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_contain)


        //for logout button
        //val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        //sharedPreferences.edit().clear().apply()
        //val intent = Intent(this, Singin::class.java)
        //startActivity(intent)
        //finish()




        val userId = intent.getStringExtra("USER_ID")
            ?: getSharedPreferences("LoginPrefs", MODE_PRIVATE).getString("userId", null)

        if (userId != null) {
            readdata(userId)
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        val chefList = listOf(
            Chef("Chef 1", R.drawable.cheaf1),
            Chef("Chef 2", R.drawable.cheaf2),
            Chef("Chef 3", R.drawable.cheaf3),
            Chef("Chef 4", R.drawable.cheaf2),
            Chef("Chef 5", R.drawable.cheaf3),
            Chef("Chef 6", R.drawable.cheaf1),
            Chef("Chef 7", R.drawable.cheaf2),
            Chef("Chef 8", R.drawable.cheaf3),
            Chef("Chef9",R.drawable.cheaf2),
            Chef("Kalpesh",R.drawable.pic)
        )

        recyclerView = findViewById(R.id.chefRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = ChefAdapter(chefList) { selectedChef ->
            val intent = Intent(this, ChefDetailActivity::class.java)
            intent.putExtra("CHEF_NAME", selectedChef.name)
            intent.putExtra("CHEF_IMAGE", selectedChef.imageResId)
            startActivity(intent)
        }
        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_you -> {
                    Toast.makeText(this, "You clicked", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this, order::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_cart -> {
                    Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_menu -> {
                    Toast.makeText(this, "Menu clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        val profile=findViewById<ShapeableImageView>(R.id.profile)
        navigationView=findViewById(R.id.nav)
        profile.setOnClickListener {
            drawerLayout = findViewById(R.id.drawer)
            drawerLayout.openDrawer(GravityCompat.START)
            navigationView.setNavigationItemSelectedListener {item ->
                when(item.itemId){
                    R.id.dropdown_menu -> {
                        Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.cart -> {
                        Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.favtourite -> {
                        Toast.makeText(this, "favtourite", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.myorder -> {
                        Toast.makeText(this, "My_Order", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.terms -> {
                        val intent= Intent(this, terms::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.Contactus-> {
                        Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.logout-> {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        finish()
                        true
                    }
                    else -> false
                }
            }


        }
    }

    private fun readdata(userId: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        databaseReference.child(userId).get().addOnSuccessListener {
            if (it.exists()) {
                val Name = findViewById<TextView>(R.id.name)
                val Email = findViewById<TextView>(R.id.Email)

                val userName = it.child("name").value.toString()
                val userEmail = it.child("email").value.toString()

                Name.text = userName
                Email.text = userEmail
            }
        }
    }







}








