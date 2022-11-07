package com.example.myapplication
import android.graphics.drawable.BitmapDrawable
import java.util.LinkedList
data class User(
    var name: String?,
    var email: String?,
    var password: String?,
    var images: LinkedList<BitmapDrawable>
)



