package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivitySearchBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class SearchActivity : AppCompatActivity(), BookClickListener
{

    val listImages: MutableList<String> = mutableListOf()
    val listImages1: MutableList<String> = mutableListOf()
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("kitty", "SnapShot: First List Size" + listImages.size.toString())
        val mainActivity = this
        val currentUser: FirebaseUser? = Firebase.auth.currentUser
        listImages.clear()
        if(currentUser == null){
            Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()
        } else{
            val storageReference = FirebaseStorage.getInstance().getReference("images");
            storageReference.listAll().addOnSuccessListener { listResult ->



                for (item in listResult.items) {
                    item.downloadUrl.addOnSuccessListener { uri -> // Do whatever you need here.
                        Log.w("kitty", "YOYOdownloadUrl:$uri")
                        listImages.add(uri.toString())
                        binding.recyclerView.apply {
                            layoutManager = GridLayoutManager(applicationContext, 2)
                            adapter = CardAdapter(listImages, mainActivity)
                            Log.d("Kitty", "Going inside with: " + listImages.size)
                        }
                    }.addOnFailureListener {
                        // Handle any errors
                    }
                }
            }
        }
    }
    override fun onClick(string: String) {
        Log.d("kitty", "Book Clicked!!" + string)
        val dialogView = layoutInflater.inflate(R.layout.activity_like_cover, null)
        val customDialog = AlertDialog.Builder(this@SearchActivity)
            .setView(dialogView)
            .show()
        var buttonLoad: Button = dialogView.findViewById(R.id.likePicture)
        buttonLoad.setOnClickListener addOnSuccessListener@{
            val currentUser: FirebaseUser? = Firebase.auth.currentUser
            if(currentUser == null){
                Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()
            } else{
                listImages1.clear()
                val wow = currentUser.uid
                val database = Firebase.database.reference
                val databaseReferencee = database.child("users").child(wow).child("likedImages")
                val key = databaseReferencee.push().key
                Log.d("kitty", "$databaseReferencee/$key")
                Log.d("kitty", "$key")
                if (key == null){
                    Log.d("kitty", "Couldn't get push key for posts")
                    return@addOnSuccessListener
                }
                val childUpdates = hashMapOf<String, Any>(
                    "users/$wow/likedImages/$key" to string
                )
                val checkerr = database.updateChildren(childUpdates)
                checkerr.addOnFailureListener{
                    Log.d("kitty", "worksNOT")
                }.addOnSuccessListener {
                    Log.d("kitty", "works?")
                }
            }
        }
    }
}