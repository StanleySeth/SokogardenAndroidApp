package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        Find the Views by us of their ids
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val textcost = findViewById<TextView>(R.id.txtProductCost)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)


//        Retrieve the Data passed from the previous activity(MainActivity)
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val product_photo = intent.getStringExtra("product_photo")
        val description = intent.getStringExtra("product_description")


//        Update the TextView with the data passed from the previous activity
        txtname.text = name
        textcost.text = "KES $cost"
        val txtDesc = findViewById<TextView>(R.id.product_description)
        txtDesc.text = description


//        Specify the image url
        val imageUrl = "https://sethstanley.alwaysdata.net/static/images/$product_photo"


        Glide.with(this)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgProduct)

//        Find the EditText and the Button by use of their ids
        val phone = findViewById<EditText>(R.id.txtPhone)
        val btnPay = findViewById<Button>(R.id.btnPay)

//        Set a click listener on the button
        btnPay.setOnClickListener {
//            Specify the api endpoint for making payments
            val api = "https://sethstanley.alwaysdata.net/api/mpesa_payment"

//            Create the requestParams
            val data = RequestParams()


//            Insert data to the requestParams
            data.put("description", description.toString())
            data.put("amount", cost)
            data.put("phone", phone.text.toString().trim())

//            Import the helper class
            val helper = ApiHelper(applicationContext)

//            Access the post function inside the helper class
            helper.post(api, data)

//            Clear the EditText after successful payment
            phone.text.clear()


        }


    }
}