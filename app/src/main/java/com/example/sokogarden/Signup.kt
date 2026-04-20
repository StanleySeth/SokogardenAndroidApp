package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find all views by use of their IDs
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val phone = findViewById<EditText>(R.id.phone)
        val signupButton = findViewById<Button>(R.id.signupBtn)
        val signinTextView = findViewById<TextView>(R.id.signintxt)

        // Set the formatted text for signinTextView
        signinTextView.text = Html.fromHtml(getString(R.string.signin_text), Html.FROM_HTML_MODE_LEGACY)

        // Below when a person clicks the TextView, the user is navigated to the signin page
        signinTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signin::class.java)
            startActivity(intent)
        }

        // onClick of the button signup, we need to interact with the API that we need to pass your data
        signupButton.setOnClickListener {
            // Validate entries
            val uName = username.text.toString().trim()
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()
            val phn = phone.text.toString().trim()

            if (uName.isEmpty()) {
                username.error = "Please enter your username"
            } else if (mail.isEmpty()) {
                email.error = "Please enter your email"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.error = "Please enter a valid email address"
            } else if (pass.isEmpty()) {
                password.error = "Please enter your password"
            } else if (phn.isEmpty()) {
                phone.error = "Please enter your phone number"
            } else {
                // If all fields are input and valid, proceed to API call
                val api = "https://stanleyseth.alwaysdata.net/api/signup"
                val data = RequestParams()
                data.put("username", uName)
                data.put("email", mail)
                data.put("password", pass)
                data.put("phone", phn)

                val helper = ApiHelper(applicationContext)
                helper.post(api, data)

                // Optional: Clear fields after successful submission attempt
                email.text.clear()
                password.text.clear()
                username.text.clear()
                phone.text.clear()
            }
        }
    }
}
