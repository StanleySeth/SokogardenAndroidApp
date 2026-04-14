
package com.example.sokogarden

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Find the two EditText, a button and a textview by their ID
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinButton = findViewById<Button>(R.id.signinBtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

//        On the TextView set onClick listener to open the signup activity
        signupTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signup::class.java)
            startActivity(intent)
        }
//        onClick of the button signin, we need to interact with the API that we need to pass
        signinButton.setOnClickListener {
//            Specify the API endpoint
            val api = "https://kbenkamotho.alwaysdata.net/api/signin"

//                        Create a request params object
            val data = RequestParams()

//                        Append the email and password to the API

            data.put("email", email.text.toString())
            data.put("password", password.text.toString())

//            Import the API helper
            val helper = ApiHelper(applicationContext)

//            By use of the function post_login inside of the helper class, post your data
            helper.post_login(api, data)




        }




    }
}