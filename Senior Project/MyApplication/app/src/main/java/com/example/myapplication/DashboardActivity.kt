package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mySettings()
        onClick()
    }

    private fun onClick() {
        binding.collageBtn.setOnClickListener{
            //binding.toolsLayout.visibility = View.GONE

        }
        binding.filterBtn.setOnClickListener {
            //binding.toolsLayout.visibility = View.GONE
        }
        binding.brightnessBtn.setOnClickListener {
            //binding.toolsLayout.visibility = View.GONE

        }
        binding.contrastBtn.setOnClickListener {
            //binding.toolsLayout.visibility = View.GONE

        }
        binding.addTextBtn.setOnClickListener {
            //binding.toolsLayout.visibility = View.GONE

        }
        binding.shapeBtn.setOnClickListener {
            //binding.toolsLayout.visibility = View.GONE

        }
        binding.brushBtn.setOnClickListener {
            //binding.toolsLayout.visibility = View.GONE

        }


    }

    private fun mySettings(){
        //val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        //val signature = prefs.getString("signature", "")
        //binding.apply {}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings ->{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}