package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }
    }
}