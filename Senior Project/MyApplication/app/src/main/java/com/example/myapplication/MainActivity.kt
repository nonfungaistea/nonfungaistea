package com.example.myapplication


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val dark_mode = pref.getString("pref_key_night", "false")
        Toast.makeText(this,dark_mode,Toast.LENGTH_LONG).show()
        if (dark_mode.equals("on")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        mAuth = FirebaseAuth.getInstance()
        onClick()
    }

    private fun onClick() {
        binding.register.setOnClickListener{
            this.startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.buttonLogin.setOnClickListener{
            userLogin()
        }

        binding.forgotPassword.setOnClickListener{
            this.startActivity(Intent(this, ForgotPasswordActivity::class.java))

        }


    }

    private fun resetPassword() {
        TODO("Not yet implemented")
    }

    private fun userLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (email.isEmpty()){
            binding.emailInput.error = "Email is required"
            binding.passwordInput.requestFocus()
            return
        }

        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            binding.emailInput.error = "Please provide valid email"
            binding.emailInput.requestFocus()
            return
        }

        if (password.isEmpty()){
            binding.passwordInput.error = "Password is required"
            binding.passwordInput.requestFocus()
            return
        }

        if (password.length < 6){
            binding.passwordInput.error = "Minimum password length should be 6"
            binding.passwordInput.requestFocus()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                val extras = Bundle()
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtras(extras)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Failed to login. Verify credentials.", Toast.LENGTH_LONG).show()
            }
        }

    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        NightMode = AppCompatDelegate.getDefaultNightMode()
//        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE)
//        editor = sharedPreferences.edit()
//        editor.putInt("NightModeInt", NightMode)
//        editor.apply()
//    }

}