package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    //Filters.xml
    private lateinit var filterBtnsLayout: ConstraintLayout
    private lateinit var filterBackBtn: TextView
    //Brightness.xml
    private lateinit var brightnessSeekbarLayout: ConstraintLayout
    private lateinit var brightnessSeekbarOkView: TextView
    private lateinit var brightnessSeekBar: SeekBar
    //Contrast.xml
    private lateinit var contrastSeekBarLayout: ConstraintLayout
    private lateinit var contrastSeekbarOkView: TextView
    private lateinit var contrastSeekBar: SeekBar





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mySettings()
        onClick()
    }



    private fun onClick() {

        binding.rotateBtn.setOnClickListener{
            //binding.toolsLayout.visibility = View.GONE

        }

        //Filters
        filterBackBtn = findViewById(R.id.filterBackBtn)
        filterBtnsLayout = findViewById(R.id.filterBtnsLayout)
        binding.filterBtn.setOnClickListener{
            filterBtnsLayout.visibility = View.VISIBLE
            binding.toolsLayout.visibility = View.GONE
        }
        filterBackBtn.setOnClickListener {
            filterBtnsLayout.visibility = View.GONE
            binding.toolsLayout.visibility = View.VISIBLE
        }

        //Brightness
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar)
        brightnessSeekbarLayout = findViewById(R.id.brightnessSeekBarLayout)
        brightnessSeekbarOkView = findViewById(R.id.brightnessSeekBarOkView)
        binding.brightnessBtn.setOnClickListener {
            brightnessSeekbarLayout.visibility = View.VISIBLE
            binding.toolsLayout.visibility = View.GONE

        }
        brightnessSeekbarOkView.setOnClickListener {
            brightnessSeekbarLayout.visibility = View.GONE
            binding.toolsLayout.visibility = View.VISIBLE
        }
        //----------------------

        //Contrast
        contrastSeekBar = findViewById(R.id.contrastSeekBar)
        contrastSeekBarLayout = findViewById(R.id.contrastSeekBarLayout)
        contrastSeekbarOkView = findViewById(R.id.contrastSeekBarOkView)
        binding.contrastBtn.setOnClickListener {
            contrastSeekBarLayout.visibility = View.VISIBLE
            binding.toolsLayout.visibility = View.GONE
        }
        contrastSeekbarOkView.setOnClickListener {
            contrastSeekBarLayout.visibility = View.GONE
            binding.toolsLayout.visibility = View.VISIBLE
        }
        //----------------------


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