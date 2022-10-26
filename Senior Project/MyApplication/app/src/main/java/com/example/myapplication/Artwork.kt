package com.example.myapplication

import android.R.attr.bitmap
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityArtworkBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class Artwork : AppCompatActivity() {
    private lateinit var binding: ActivityArtworkBinding
    //Filters.xml
    private lateinit var filterBtnsLayout: ConstraintLayout
    private lateinit var filterBackBtn: TextView
    //Filter Btns
    private lateinit var greyBtn: ImageView
    private lateinit var ogPhotobtn: ImageView
    private lateinit var redBtn: ImageView
    private lateinit var greenBtn: ImageView
    private lateinit var blueBtn: ImageView
    private lateinit var redGreenBtn: ImageView
    private lateinit var redBlueBtn: ImageView
    private lateinit var greenBlueBtn: ImageView
    private lateinit var sepiaBtn: ImageView
    private lateinit var binaryBtn: ImageView
    private lateinit var invertBtn: ImageView
    //Brightness.xml
    private lateinit var brightnessSeekbarLayout: ConstraintLayout
    private lateinit var brightnessSeekbarOkView: TextView
    private lateinit var brightnessSeekBar: SeekBar
    //Contrast.xml
    private lateinit var contrastSeekBarLayout: ConstraintLayout
    private lateinit var contrastSeekbarOkView: TextView
    private lateinit var contrastSeekBar: SeekBar
    //saveArt
    private lateinit var saveButton:Button
    private lateinit var ogbmp: BitmapDrawable
    private lateinit var editImage: ImageView
    private lateinit var stream: InputStream
    private var uri:Uri? = null
    private lateinit var filtered: String
    var filteredBmp: Bitmap? = null

    private lateinit var image:ImageView
    //val path = File("res/drawable/default_bg.webp")
//    val extras = intent.extras
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val imageString = intent.getStringExtra("KEY")
        uri = Uri.parse(imageString)
        binding = ActivityArtworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        stream = uri?.let { contentResolver.openInputStream(it) }!!
        val inputStream = contentResolver.openInputStream(uri!!)
        ogbmp = Drawable.createFromStream(inputStream, imageString) as BitmapDrawable

        //ogBmp = uri as BitmapDrawable

        editImage=findViewById(R.id.photoView)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.greyBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.ogBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.redBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.blueBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.greenBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.redGreenBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.redBlueBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.greenBlueBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.sepiaBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.binaryBtn)
        editImage.setImageDrawable(ogbmp)
        editImage=findViewById(R.id.invertBtn)
        editImage.setImageDrawable(ogbmp)
        onClick()
        //save to gallery
        //saveFile()
        }



//    fun getArtwork(){
//        ogBmp =
//    }

//    fun getExtra(){
//
//        if (extras != null && extras.containsKey("KEY")) {
//            uri= Uri.parse(extras.getString("KEY"));
//        }
//    }



    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
    fun saveFile() {
        saveButton = findViewById(R.id.saveBtn)
        saveButton.setOnClickListener {
            bitmapToFile(findViewById(R.id.ogBtn), "Ham")
        }
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
        // btimap from original bitmap drawable
        var bmp = ogbmp.bitmap
        if (filteredBmp != null)
            bmp = filteredBmp
        //define a mull
        val mul = 0X000000
        val initialHex = Tool.hexScale()[value]
        val initialAdd = "0X" + initialHex + initialHex + initialHex
        val add = Integer.decode(initialAdd)

        val outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.width, bmp.height, false).copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()
        val colorFilter = LightingColorFilter(add, mul)
        paint.colorFilter = colorFilter

        val canvas = Canvas(outputBitmap)
        canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        binding.photoView.setImageBitmap(outputBitmap)
    }

    private fun adjustBrightness(value: Int) {
        //Bitmap from original bitmap drawable
        var bmp = ogbmp.bitmap
        if (filteredBmp != null){
            bmp = filteredBmp
        }
        //define a mul
        val mul = 0XFFFFFF
        val initialHex = Tool.hexScale()[value]
        val initialAdd = "0X" + initialHex + initialHex + initialHex
        val add = Integer.decode(initialAdd)
        val outputBitmap = Bitmap.createScaledBitmap(bmp, bmp.width, bmp.height, false).copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()
        val colorFilter = LightingColorFilter(mul, add)
        paint.colorFilter = colorFilter

        val canvas = Canvas(outputBitmap)
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
        //Reset button
        ogPhotobtn = findViewById(R.id.ogBtn)
        ogPhotobtn.setOnClickListener {
            filteredBmp = null
            filtered = null.toString()

            binding.photoView.setImageDrawable((ogbmp))
            brightnessSeekBar.progress = 0
            contrastSeekBar.progress = 255
        }

        //Red btn
        redBtn = findViewById(R.id.redBtn)
        filterBtn(redBtn, Filter.red)
        redBtn.setOnClickListener {filter(Filter.red)}

        //Green btn
        greenBtn = findViewById(R.id.greenBtn)
        filterBtn(greenBtn, Filter.green)
        greenBtn.setOnClickListener {filter(Filter.green)}

        //Blue btn
        blueBtn = findViewById(R.id.blueBtn)
        filterBtn(blueBtn, Filter.blue)
        blueBtn.setOnClickListener {filter(Filter.blue)}

        redGreenBtn = findViewById(R.id.redGreenBtn)
        filterBtn(redGreenBtn, Filter.redGreen)
        redGreenBtn.setOnClickListener {filter(Filter.redGreen)}

        redBlueBtn = findViewById(R.id.redBlueBtn)
        filterBtn(redBlueBtn, Filter.redBlue)
        redBlueBtn.setOnClickListener {filter(Filter.redBlue)}

        greenBlueBtn = findViewById(R.id.greenBlueBtn)
        filterBtn(greenBlueBtn, Filter.greenBlue)
        greenBlueBtn.setOnClickListener {filter(Filter.greenBlue)}

        sepiaBtn = findViewById(R.id.sepiaBtn)
        filterBtn(sepiaBtn, Filter.sepia)
        sepiaBtn.setOnClickListener {filter(Filter.sepia)}

        binaryBtn = findViewById(R.id.binaryBtn)
        filterBtn(binaryBtn, Filter.binary)
        binaryBtn.setOnClickListener {filter(Filter.binary)}

        invertBtn = findViewById(R.id.invertBtn)
        filterBtn(invertBtn, Filter.invert)
        invertBtn.setOnClickListener {filter(Filter.invert)}


    }






    private fun filter(filter: String){
        //create a bitmap from our original bitmap drawable
        val bmp = ogbmp.bitmap
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

        if (filter.equals(Filter.red)){
            val mul = 0XFF0000
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.green)){
            val mul = 0X00FF00
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.blue)){
            val mul = 0X0000FF
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.redGreen)){
            val mul = 0XFFFF00
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.redBlue)){
            val mul = 0XFF00FF
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.greenBlue)){
            val mul = 0X00FFFF
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.sepia)){
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            val colorScale = ColorMatrix()
            colorScale.setScale(1F, 1F,0.8F, 1F)
            colorMatrix.postConcat(colorScale)
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.setColorFilter(colorFilter)
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.binary)){
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            val m = 255F
            val t = -255 * 128F
            val threshold = ColorMatrix(floatArrayOf(
                m, 0F, 0F, 1F, t,
                0F, m, 0F, 1F, t,
                0F, 0F, m, 1F, t,
                0F, 0F, 0F, 1F, 0F
            ))

            colorMatrix.postConcat(threshold)
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.setColorFilter(colorFilter)
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)

        }

        if (filter.equals(Filter.invert)){
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            colorMatrix.set(floatArrayOf(
                -1F, 0F, 0F, 0F, 255F,
                0F, -1F, 0F, 0F, 255F,
                0F, 0F, -1F, 0F, 255F,
                0F, 0F, 0F, 1F, 0F
            ))
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.setColorFilter(colorFilter)
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        //Set the output bitmap to imageview
        binding.photoView.setImageBitmap(outputBitmap)
        filteredBmp = outputBitmap
        filtered = filter

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
        if (filter.equals(Filter.red)){
            val mul = 0XFF0000
            val add = 0X000000
            var colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        if (filter.equals(Filter.green)){
            val mul = 0X00FF00
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.blue)){
            val mul = 0X0000FF
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        if (filter.equals(Filter.redGreen)){
            val mul = 0XFFFF00
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }

        if (filter.equals(Filter.redBlue)){
            val mul = 0XFF00FF
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        if (filter.equals(Filter.greenBlue)){
            val mul = 0X00FFFF
            val add = 0X000000
            val colorFilter = LightingColorFilter(mul, add)
            paint.colorFilter = colorFilter
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        if (filter.equals(Filter.sepia)){
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            val colorScale = ColorMatrix()
            colorScale.setScale(1F, 1F,0.8F, 1F)
            colorMatrix.postConcat(colorScale)
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.setColorFilter(colorFilter)
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        if (filter.equals(Filter.binary)){
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            val m = 255F
            val t = -255 * 128F
            val threshold = ColorMatrix(floatArrayOf(
                m, 0F, 0F, 1F, t,
                0F, m, 0F, 1F, t,
                0F, 0F, m, 1F, t,
                0F, 0F, 0F, 1F, 0F
            ))

            colorMatrix.postConcat(threshold)
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.setColorFilter(colorFilter)
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)

        }
        if (filter.equals(Filter.invert)){
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            colorMatrix.set(floatArrayOf(
                -1F, 0F, 0F, 0F, 255F,
                0F, -1F, 0F, 0F, 255F,
                0F, 0F, -1F, 0F, 255F,
                0F, 0F, 0F, 1F, 0F
            ))
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.setColorFilter(colorFilter)
            canvas.drawBitmap(outputBitmap, 0F, 0F, paint)
        }
        //Set the output bitmap to the actual button instead of the whole image
        btn.setImageBitmap(outputBitmap)

    }
}