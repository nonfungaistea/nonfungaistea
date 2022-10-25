package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityForgotpasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity(){
    private lateinit var binding: ActivityForgotpasswordBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        onClick()
    }

    private fun onClick() {
        binding.textNft.setOnClickListener{
            this.startActivity(Intent(this, MainActivity::class.java))
        }

        binding.resetPassword.setOnClickListener{
            resetPassword()
            this.startActivity(Intent(this, MainActivity::class.java))

        }
    }

    private fun resetPassword() {
        val email = binding.email.text.toString().trim()

        if (email.isEmpty()){
            binding.email.error = "Email is required"
            binding.email.requestFocus()
            return
        }

        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            binding.email.error = "Please provide valid email"
            binding.email.requestFocus()
            return
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener{
            if (it.isSuccessful){
                mAuth.uid
                 Toast.makeText(this, "Check your email to reset your password", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_LONG).show()
            }
        }
    }
}