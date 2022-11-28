package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivitySearchBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult


class SearchActivity : AppCompatActivity(), BookClickListener
{

    val listImages: MutableList<String> = mutableListOf()
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
            val wow = currentUser.uid
            val database = Firebase.database.reference
            val storageReference = FirebaseStorage.getInstance().getReference("images");
            storageReference.listAll().addOnSuccessListener { listResult ->
                for (item in listResult.items) {
                    item.downloadUrl.addOnSuccessListener { uri -> // Do whatever you need here.
                        Log.w("kitty", "YOYOdownloadUrl:$uri")
                    }.addOnFailureListener {
                        // Handle any errors
                    }
                }
            }
            val databaseReferencee = database.child("users").child(wow).child("images")
            var counter = 0
            databaseReferencee.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("kitty", "Starting With... Size " + listImages.size.toString())

                    for (ds in snapshot.children){
                        Log.d("kitty", "New Snapshot...")
                        if (counter == 0){
                            Log.d("kitty", "Should be null")
                        } else {
                            Log.d("kitty", "DSVALUE: " + ds.value.toString())
                            listImages.add(ds.value.toString())
                        }
                        counter += 1
                        binding.recyclerView.apply {
                            layoutManager = GridLayoutManager(applicationContext, 2)
                            adapter = CardAdapter(listImages, mainActivity)
                            Log.d("Kitty", "Going inside with: " + listImages.size)
                        }
                    }
                    Log.d("kitty", "Ending... Size " + listImages.size.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
    override fun onClick(string: String) {
        Log.d("kitty", "Book Clicked!"+ string)
    }
}