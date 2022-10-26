package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.logpass)
        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener { deleteaccount(email.text.toString(), password.text.toString()) }
        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }
    }

    private fun deleteaccount(email: String, password: String) {

        if (email.isEmpty()){
            findViewById<EditText>(R.id.email).error = "Please enter your email"
            findViewById<EditText>(R.id.email).requestFocus()
            return
        }

        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            findViewById<EditText>(R.id.email).error = "Please provide valid email"
            findViewById<EditText>(R.id.email).requestFocus()
            return
        }

        if (password.isEmpty()){
            findViewById<EditText>(R.id.logpass).error = "Please enter your password"
            findViewById<EditText>(R.id.logpass).requestFocus()
            return
        }

        if (password.length < 6){
            findViewById<EditText>(R.id.logpass).error = "Minimum password length should be 6"
            findViewById<EditText>(R.id.logpass).requestFocus()
            return
        }

        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "User account deleted.")
                            startActivity(Intent(this@DeleteActivity, MainActivity::class.java))
                            Toast.makeText(
                                this@DeleteActivity,
                                "Deleted account Successfully.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DeleteActivity,
                                "Delete account failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else{
                findViewById<EditText>(R.id.email).error = "Verify credentials"
                findViewById<EditText>(R.id.logpass).error = "Verify credentials"
                findViewById<EditText>(R.id.email).requestFocus()
                findViewById<EditText>(R.id.logpass).requestFocus()
                Toast.makeText(
                    this@DeleteActivity,
                    "Verify credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}