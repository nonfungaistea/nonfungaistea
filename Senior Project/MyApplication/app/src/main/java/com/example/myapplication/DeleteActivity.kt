package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
    }

    private fun deleteaccount(email: String, password: String) {
        val user = FirebaseAuth.getInstance().currentUser

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider.getCredential(email, password)

        // Prompt the user to re-provide their sign-in credentials
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "User account deleted.")
                        startActivity(Intent(this@DeleteActivity, MainActivity::class.java))
                        Toast.makeText(
                            this@DeleteActivity,
                            "Deleted User Successfully,",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}