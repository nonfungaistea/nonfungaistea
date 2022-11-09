package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        val loggingout = findViewById<Button>(R.id.logoutbutton)
        loggingout.setOnClickListener { logoutaccount() }
        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }
    }

    private fun logoutaccount() {
        Toast.makeText(
            this@LogOutActivity,
            "Logging Out",
            Toast.LENGTH_LONG
        ).show()
        FirebaseAuth.getInstance().signOut()
        val logoutIntent = Intent(this, MainActivity::class.java)
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(logoutIntent)
    }
}