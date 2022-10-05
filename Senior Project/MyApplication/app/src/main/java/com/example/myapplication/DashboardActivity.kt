package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityDashboardBinding


class DashboardActivity() : AppCompatActivity()  {
    private var boardSize: BoardSize = BoardSize.HARD
    private lateinit var binding: ActivityDashboardBinding
//    private lateinit var rvBoard: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val plusImage = DEFAULT_ICONS.take(boardSize.getNumPairs())
        binding.rvBoard.adapter = VisualCardAdapter(this,boardSize,plusImage)    //8 Rows
        binding.rvBoard.setHasFixedSize(true)   // Size is always going to be defined when it boots up no matter how many it has, but with true it makes app more efficient.
        binding.rvBoard.layoutManager = GridLayoutManager(this,boardSize.getWidth()) // 2 Columns
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