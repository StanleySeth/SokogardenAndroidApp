package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        Find the button by use of their id
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val signinBtn = findViewById<Button>(R.id.signinBtn)

//        Create the intents of the two activities
        signupBtn.setOnClickListener {
        val intent = Intent(applicationContext, Signup::class.java)
            startActivity(intent)
        }

        signinBtn.setOnClickListener {
            val intent = Intent(applicationContext, Signin::class.java)
            startActivity(intent)
        }

//        Find the recyclerview and progressbar by use of their id
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val progressBar = findViewById<ProgressBar>(R.id.progressbar)

//        Specify the API URL endpoint for fetching the products from alwaysdata
        val url = "https://sethstanley.alwaysdata.net/api/get_products"

//        import the helper class
        val helper = ApiHelper(applicationContext)

//        Inside the helper class, we call the loadProducts function
        helper.loadProducts(url, recyclerView, progressBar)





    }
}