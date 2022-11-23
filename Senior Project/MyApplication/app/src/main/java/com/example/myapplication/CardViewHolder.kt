package com.example.myapplication

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardCellBinding
import com.google.firebase.storage.FirebaseStorage
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: BookClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindBook(ImageURL: String)
    {
        val yo = R.drawable.tmom
        var storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val httpsReference = storage.getReferenceFromUrl(ImageURL)
        Log.d("kitty", "omsode")
        Log.d("kitty", httpsReference.toString())
        val ONE_MEGABYTE: Long = 1024 * 1024 * 10
        val textDef = "Example"
        httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            Log.d("kitty", "YES!")
            val image: Bitmap? =  BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            cardCellBinding.cover.setImageBitmap(image)
            cardCellBinding.title.text = textDef
            cardCellBinding.author.text = textDef

//            cardCellBinding.cardView.setOnClickListener{
//                clickListener.onClick(book)
//            }
            // Data for "images/island.jpg" is returned, use this as needed
        }.addOnFailureListener {
            Log.d("kitty","didnttWork")
        }

    }
}