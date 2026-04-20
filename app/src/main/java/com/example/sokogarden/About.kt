package com.example.sokogarden

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.AdapterView
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class About : AppCompatActivity() {

    lateinit var tts: TextToSpeech
    private var availableVoices: List<Voice> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView = findViewById<TextView>(R.id.aboutTxt)
        val btnListen = findViewById<Button>(R.id.btnListen)
        val voiceSpinner = findViewById<Spinner>(R.id.voiceSpinner)

        tts = TextToSpeech(applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.ENGLISH

                availableVoices = tts.voices
                    ?.filter { voice ->
                        voice.locale.language == Locale.ENGLISH.language
                    }
                    ?.sortedWith(compareBy({ detectGender(it) }, { it.name }))
                    ?: emptyList()

                runOnUiThread {
                    val voiceNames = availableVoices.map { formatVoiceName(it) }
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item,
                        voiceNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    voiceSpinner.adapter = adapter

                    if (availableVoices.isNotEmpty()) {
                        tts.voice = availableVoices[0]
                    }
                }
            }
        }

        voiceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (availableVoices.isNotEmpty()) {
                    tts.voice = availableVoices[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnListen.setOnClickListener {
            val text = textView.text.toString()
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun detectGender(voice: Voice): String {
        val name = voice.name.lowercase()
        return when {
            name.contains("female")                            -> "Female"
            name.contains("male") && !name.contains("female") -> "Male"
            name.contains("#f")                                -> "Female"
            name.contains("#m")                                -> "Male"
            name.contains("_f_") || name.endsWith("_f")       -> "Female"
            name.contains("_m_") || name.endsWith("_m")       -> "Male"
            else                                               -> "Unknown"
        }
    }

    private fun formatVoiceName(voice: Voice): String {
        val locale = voice.locale.displayName
        val gender = detectGender(voice)
        val networkTag = if (voice.isNetworkConnectionRequired) " \uD83C\uDF10" else ""
        val index = voice.name.filter { it.isDigit() }.firstOrNull()?.toString() ?: ""
        return when (gender) {
            "Female" -> "\u2640 $locale · Female${if (index.isNotEmpty()) " $index" else ""}$networkTag"
            "Male"   -> "\u2642 $locale · Male${if (index.isNotEmpty()) " $index" else ""}$networkTag"
            else     -> "$locale · Voice${if (index.isNotEmpty()) " $index" else ""}$networkTag"
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}