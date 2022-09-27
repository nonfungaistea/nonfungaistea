package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private var boardSize: BoardSize = BoardSize.HARD
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumPair: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvBoard = findViewById(R.id.rvBoard)

        val plusImage = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        rvBoard.adapter = VisualCardAdapter(this,boardSize,plusImage)    //8 Rows
        rvBoard.setHasFixedSize(true)   // Size is always going to be defined when it boots up no matter how many it has, but with true it makes app more efficient.
        rvBoard.layoutManager = GridLayoutManager(this,boardSize.getWidth()) // 2 Columns
        //setContentView(R.layout.activity_dashboard)
    }
}