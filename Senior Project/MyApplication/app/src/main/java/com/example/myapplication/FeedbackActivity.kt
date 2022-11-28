package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.time.LocalDateTime


class FeedbackActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        val current = LocalDateTime.now()
        val reference = FirebaseStorage.getInstance().reference.child("feedback")
        val feedback = findViewById<EditText>(R.id.feedback_message)
        val submit = findViewById<Button>(R.id.submit)
        val back = findViewById<ImageButton>(R.id.backToSettings)
        submit.setOnClickListener {
            reference.child("$current.txt").putBytes(feedback.text.toString().toByteArray())
        }
        back.setOnClickListener { onBackPressed() }
    }
}