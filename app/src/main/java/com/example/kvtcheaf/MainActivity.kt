package com.example.kvtcheaf


import Chef
import ChefAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class MainActivity : AppCompatActivity() {
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


        val userId = intent.getStringExtra("USER_ID")
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
            Chef("Chef 8", R.drawable.cheaf3)
        )

        recyclerView = findViewById(R.id.chefRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = ChefAdapter(chefList) { selectedChef ->
            val intent = Intent(this, ChefDetailActivity::class.java)
            intent.putExtra("CHEF_NAME", selectedChef.name)
            intent.putExtra("CHEF_IMAGE", selectedChef.imageResId)
            startActivity(intent)
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








