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

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.logpass)
        val new_pass = findViewById<EditText>(R.id.new_password)
        val new_pass_confirm = findViewById<EditText>(R.id.new_password_confirm)
        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener { changepass(email.text.toString(), password.text.toString(),
            new_pass.text.toString(), new_pass_confirm.text.toString() ) }
        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }
    }
    private fun changepass(email: String, password: String, new_pass: String, new_pass_confirm: String) {

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

        if (new_pass.isEmpty()){
            findViewById<EditText>(R.id.new_password).error = "Please enter a new password"
            findViewById<EditText>(R.id.new_password).requestFocus()
            return
        }

        if (new_pass.length < 6){
            findViewById<EditText>(R.id.new_password).error = "Minimum password length should be 6"
            findViewById<EditText>(R.id.new_password).requestFocus()
            return
        }

        if (new_pass_confirm.isEmpty()){
            findViewById<EditText>(R.id.new_password_confirm).error = "Please confirm your new password"
            findViewById<EditText>(R.id.new_password_confirm).requestFocus()
            return
        }

        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (new_pass == new_pass_confirm) {
                    user.updatePassword(new_pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "User password updated.")
                                startActivity(
                                    Intent(
                                        this@PasswordActivity,
                                        MainActivity::class.java
                                    )
                                )
                                Toast.makeText(
                                    this@PasswordActivity,
                                    "Updated password successfully.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@PasswordActivity,
                                    "Update password failed.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    findViewById<EditText>(R.id.new_password_confirm).error = "New password doesn't match"
                    findViewById<EditText>(R.id.new_password_confirm).requestFocus()
                }
            }else {
                findViewById<EditText>(R.id.email).error = "Verify credentials"
                findViewById<EditText>(R.id.logpass).error = "Verify credentials"
                findViewById<EditText>(R.id.email).requestFocus()
                findViewById<EditText>(R.id.logpass).requestFocus()
                Toast.makeText(
                    this@PasswordActivity,
                    "Verify credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}