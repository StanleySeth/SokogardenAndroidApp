package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sokogarden.MainActivity
import com.example.sokogarden.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Logo and title animations
        val logo = findViewById<ImageView>(R.id.ivLogo)
        val title = findViewById<TextView>(R.id.tvWelcome)

        val popIn = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.anim_title)

        logo.startAnimation(popIn)
        title.startAnimation(slideUp)

        // Bouncing dots animations
        val dot1 = findViewById<View>(R.id.dot1)
        val dot2 = findViewById<View>(R.id.dot2)
        val dot3 = findViewById<View>(R.id.dot3)

        val dotAnim1 = AnimationUtils.loadAnimation(this, R.anim.anim_dot).apply { startOffset = 0 }
        val dotAnim2 = AnimationUtils.loadAnimation(this, R.anim.anim_dot).apply { startOffset = 200 }
        val dotAnim3 = AnimationUtils.loadAnimation(this, R.anim.anim_dot).apply { startOffset = 400 }

        dot1.startAnimation(dotAnim1)
        dot2.startAnimation(dotAnim2)
        dot3.startAnimation(dotAnim3)

        // Navigate to MainActivity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}