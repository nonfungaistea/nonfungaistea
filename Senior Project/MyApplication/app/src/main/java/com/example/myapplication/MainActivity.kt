package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.register.setOnClickListener(){
            setContentView(R.layout.activity_register)
        }
    }


}