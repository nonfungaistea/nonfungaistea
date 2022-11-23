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

class EmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.logpass)
        val new_email = findViewById<EditText>(R.id.new_email)
        val new_email_confirm = findViewById<EditText>(R.id.new_email_confirm)
        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener { changeemail(email.text.toString(), password.text.toString(),
            new_email.text.toString(), new_email_confirm.text.toString() ) }
        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }
    }
    private fun changeemail(email: String, password: String, new_email: String, new_email_confirm: String) {

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

        if (new_email.isEmpty()){
            findViewById<EditText>(R.id.new_email).error = "Please enter a new email"
            findViewById<EditText>(R.id.new_email).requestFocus()
            return
        }

        if (!(Patterns.EMAIL_ADDRESS.matcher(new_email).matches())){
            findViewById<EditText>(R.id.new_email).error = "Please provide valid new email"
            findViewById<EditText>(R.id.new_email).requestFocus()
            return
        }

        if (new_email_confirm.isEmpty()){
            findViewById<EditText>(R.id.new_email_confirm).error = "Please confirm your new email"
            findViewById<EditText>(R.id.new_email_confirm).requestFocus()
            return
        }

        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (new_email == new_email_confirm) {
                    user.updateEmail(new_email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "User email updated.")
                                startActivity(Intent(this@EmailActivity, MainActivity::class.java))
                                Toast.makeText(
                                    this@EmailActivity,
                                    "Updated email successfully.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@EmailActivity,
                                    "Update email failed.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    findViewById<EditText>(R.id.new_email_confirm).error = "New email doesn't match"
                    findViewById<EditText>(R.id.new_email_confirm).requestFocus()
                }
            }else {
                findViewById<EditText>(R.id.email).error = "Verify credentials"
                findViewById<EditText>(R.id.logpass).error = "Verify credentials"
                findViewById<EditText>(R.id.email).requestFocus()
                findViewById<EditText>(R.id.logpass).requestFocus()
                Toast.makeText(
                    this@EmailActivity,
                    "Verify credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}