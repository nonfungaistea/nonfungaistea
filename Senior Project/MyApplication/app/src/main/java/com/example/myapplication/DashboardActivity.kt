package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import android.widget.Toast
import com.example.myapplication.databinding.ActivityDashboardBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigationrail.NavigationRailView


class DashboardActivity() : AppCompatActivity()  {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navRailFab: FloatingActionButton = findViewById(R.id.nav_rail_fab)
        navRailFab.setOnClickListener {
            Toast.makeText(this, "FAB Clicked!", Toast.LENGTH_SHORT).show()
            this.startActivity(Intent(this, GetPicture::class.java))
        }
        val navigationRail: NavigationRailView = findViewById(R.id.navigationRail)
        navigationRail.setOnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.files -> {
                Toast.makeText(this, "Files", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.images -> {
                Toast.makeText(this, "Images", Toast.LENGTH_SHORT).show()
                true
            }

            else -> false
        }
    }

        //setContentView(R.layout.activity_dashboard)

        mySettings()
    }

    private fun mySettings(){

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val signature = prefs.getString("signature", "")

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
    //if (position == 1) this.startActivity(Intent(this, CreateNFT::class.java))
}