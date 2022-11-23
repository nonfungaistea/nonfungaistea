package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        onClick()
   }

    private fun onClick() {
        binding.textNft.setOnClickListener(){
            this.startActivity(Intent(this, MainActivity::class.java))
        }
        binding.registUser.setOnClickListener(){
            registerUser()
        }
    }

    private fun registerUser() {
        val fullname = binding.fullName.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (fullname.isEmpty()){
            binding.fullName.error = "Full name is required"
            binding.fullName.requestFocus()
            return
        }

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

        if (password.isEmpty()){
            binding.password.error = "Password is required"
            binding.password.requestFocus()
            return
        }

        if (password.length < 6){
            binding.password.error = "Minimum password length should be 6"
            binding.password.requestFocus()
            return
        }
        // Create an Instance and create a register a user with an email and password
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                // If the registration of the user is successfully done
                if (it.isSuccessful) {
                    // Create a user object
                    val user = User(fullname, email, password)
                    // Notify the user that he was registered successfully
                    Toast.makeText(this, "User was registered successfully", Toast.LENGTH_LONG).show()
                    // Locates the directory for the new user and adds him below his ID with all of his information
                    database = Firebase.database.reference
                    mAuth.uid?.let { it1 ->
                        database.child("users").child(it1).setValue(user)
                                // When the user is added into the database go back to the main screen
                            .addOnCompleteListener{
                                this.startActivity(Intent(this, MainActivity::class.java))
                            }
                    }
                }else{
                    Toast.makeText(this, "Failed to register user", Toast.LENGTH_LONG).show()
                }
            }
    }
}