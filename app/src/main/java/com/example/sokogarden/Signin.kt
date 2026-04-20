package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Patterns
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

        // Find views
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinButton = findViewById<Button>(R.id.signinBtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

        // Set the formatted text for signupTextView
        signupTextView.text = Html.fromHtml(getString(R.string.signup_text), Html.FROM_HTML_MODE_LEGACY)

        // On the TextView set onClick listener to open the signup activity
        signupTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signup::class.java)
            startActivity(intent)
        }

        // onClick of the button signin, we need to interact with the API
        signinButton.setOnClickListener {
            // Validate entries
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()

            if (mail.isEmpty()) {
                email.error = "Please enter your email"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.error = "Please enter a valid email address"
            } else if (pass.isEmpty()) {
                password.error = "Please enter your password"
            } else {
                // If all fields are input and valid, proceed to API call
                val api = "https://stanleyseth.alwaysdata.net/api/signin"
                val data = RequestParams()
                data.put("email", mail)
                data.put("password", pass)

                val helper = ApiHelper(applicationContext)
                helper.post_login(api, data)
            }
        }
    }
}
