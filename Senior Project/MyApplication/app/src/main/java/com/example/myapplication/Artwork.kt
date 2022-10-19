package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.LightingColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.databinding.ActivityArtworkBinding


class Artwork : AppCompatActivity() {
    private lateinit var binding: ActivityArtworkBinding
    //Filters.xml
    private lateinit var filterBtnsLayout: ConstraintLayout
    private lateinit var filterBackBtn: TextView
    //Filter Btns
    private lateinit var greyBtn: ImageView
    //Brightness.xml
    private lateinit var brightnessSeekbarLayout: ConstraintLayout
    private lateinit var brightnessSeekbarOkView: TextView
    private lateinit var brightnessSeekBar: SeekBar
    //Contrast.xml
    private lateinit var contrastSeekBarLayout: ConstraintLayout
    private lateinit var contrastSeekbarOkView: TextView
    private lateinit var contrastSeekBar: SeekBar

    private lateinit var ogBmp: BitmapDrawable

    private lateinit var filtered: String
    private lateinit var filteredBmp: Bitmap







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ogBmp = binding.photoView.drawable as BitmapDrawable
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
        //-------------------------

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

        //Filters
        filters()

        //seek bar listener (brightness and contrast)
        seekBarListeners()

//        binding.addTextBtn.setOnClickListener {
//            //binding.toolsLayout.visibility = View.GONE
//        }
//        binding.shapeBtn.setOnClickListener {
//            //binding.toolsLayout.visibility = View.GONE
//        }
//        binding.brushBtn.setOnClickListener {
//            //binding.toolsLayout.visibility = View.GONE
//        }
    }

    private fun seekBarListeners() {
        //brightnessSeekBar Listener
        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                adjustBrightness(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })

        contrastSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                adjustContrast(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })



    }

    private fun adjustContrast(value: Int) {

    }

    private fun adjustBrightness(value: Int) {
        //Bitmap from original bitmap drawable
        var bmp = ogBmp.bitmap
        if (filteredBmp != null){
            bmp = filteredBmp
        }
        //define a mul
        val mul = 0XFFFFFF
        var initialHex = Tool.hexScale()[value]
        var initialAdd = "0X" + initialHex + initialHex + initialHex
        var add = Integer.decode(initialAdd)
        var outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.width, bmp.height, false).copy(Bitmap.Config.ARGB_8888, true)
        var paint = Paint()
        var colorFilter = LightingColorFilter(mul, add)
        paint.colorFilter = colorFilter

        var canvas = Canvas(outputBitmap)
        canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        binding.photoView.setImageBitmap(outputBitmap)




    }

    private fun filters() {
        //Grey scale filter btn
         greyBtn = findViewById(R.id.greyBtn)

        //General filter button
        filterBtn(greyBtn, Filter.grey)

        greyBtn.setOnClickListener {
            //filter photo to grey scale
            filter(Filter.grey)
        }
    }

    private fun filter(filter: String){
        //create a bitmap from our original bitmap drawable
        val bmp = ogBmp.bitmap
        //Generate an output bitmap from the above bitmap
        val outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.width, bmp.height, false).copy(Bitmap.Config.ARGB_8888, true)
        //Define a paint for styling and coloring the bitmap
        val paint = Paint()
        //Canvas to draw our bitmap on
        val canvas = Canvas(outputBitmap)

        //filtering the photo to grey scale
        if (filter.equals(Filter.grey)){
            //color matrix to filter to grey scale
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)

            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = colorFilter

            //draw out bitmap
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        //Set the output bitmap to imageview
        binding.photoView.setImageBitmap(outputBitmap)
    }

    private fun filterBtn(btn: ImageView?, filter: String) {
        //Get bitmap drawable from the imageview btn
        val dBmp = btn?.drawable as BitmapDrawable

        //getBitmap from above drawable bitmap
        val bmp = dBmp.bitmap

        //Generate an output bitmap from the above bitmap
        val outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.width, bmp.height, false).copy(Bitmap.Config.ARGB_8888, true)
        //Define a paint for styling and coloring the bitmap
        val paint = Paint()
        //Canvas to draw out bitmap on
        val canvas = Canvas(outputBitmap)

        //filtering the photo to grey scale
        if (filter.equals(Filter.grey)){
            //color matrix to filter to grey scale
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)

            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = colorFilter

            //draw out bitmap
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        //Set the output bitmap to the actual button instead of the whole image
        btn.setImageBitmap(outputBitmap)

        // save filtered resources
        filteredBmp = outputBitmap
        filtered = filter

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