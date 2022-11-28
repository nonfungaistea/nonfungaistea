package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val prefEditor: SharedPreferences.Editor = pref.edit()

        var usernickname = pref.getString("nickname", "(Not Set)")
        setContentView(R.layout.activity_profile)
        val emailtextview = findViewById<TextView>(R.id.emailtv)
        emailtextview.setText("text test")
        val nicknametextview = findViewById<TextView>(R.id.nicknametv)
        if (usernickname.equals("")){
            nicknametextview.setText("Not Set")
        } else {
            nicknametextview.setText(usernickname)
        }

        val back = findViewById<ImageButton>(R.id.backToSettings)
        back.setOnClickListener { onBackPressed() }

        val edittext = EditText(this);

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter your new nickname.")
        builder.setCancelable(true)
        builder.setView(edittext)
        builder.setNeutralButton("Change") {
                dialog, which ->
            prefEditor.putString("nickname", edittext.text.toString()).apply()
            usernickname = pref.getString("nickname", "(Not Set)")
            if (usernickname.equals("")){
                nicknametextview.setText("(Not Set)")
            } else {
                nicknametextview.setText(usernickname)
            }
        }
        val alertDialog = builder.create()

        val changeimage = findViewById<Button>(R.id.change_image)
        val nickname = findViewById<ImageButton>(R.id.nicknamebutton)
        val email = findViewById<ImageButton>(R.id.emailbutton)
        val password = findViewById<ImageButton>(R.id.passwordbutton)
        val logout = findViewById<Button>(R.id.logoutbutton)

        changeimage.setOnClickListener{

        }
        nickname.setOnClickListener{
            alertDialog.show()
        }
        email.setOnClickListener{
           startActivity(Intent(this, EmailActivity::class.java))
        }
        password.setOnClickListener{
            startActivity(Intent(this, PasswordActivity::class.java))
        }
        logout.setOnClickListener{
            startActivity(Intent(this, LogOutActivity::class.java))
        }

    }
}