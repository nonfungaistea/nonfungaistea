package com.example.myapplication
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityLikeBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LikeActivity : AppCompatActivity(), BookClickListener
{

    val listImages: MutableList<String> = mutableListOf()
    val listImages1: MutableList<String> = mutableListOf()
    private lateinit var binding: ActivityLikeBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainActivity = this
        val currentUser: FirebaseUser? = Firebase.auth.currentUser
        listImages.clear()
        if(currentUser == null){
            Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()
        } else{
            val wow = currentUser.uid
            val database = Firebase.database.reference
            val databaseReferencee = database.child("users").child(wow).child("likedImages")
            var counter = 0
            databaseReferencee.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        if (counter == 0){
                        } else {
                            Log.d("kitty1", "DSVALUE: " + ds.value.toString())
                            listImages.add(ds.value.toString())
                        }
                        counter += 1
                        binding.recyclerView.apply {
                            layoutManager = GridLayoutManager(applicationContext, 2)
                            adapter = CardAdapter(listImages, mainActivity)
                        }
                    }
//                    Log.d("kitty", "Ending... Size " + listImages.size.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
    override fun onClick(string: String) {
        Log.d("kitty", "Book Clicked!!" + string)
        val dialogView = layoutInflater.inflate(R.layout.activity_like_cover, null)
        val customDialog = AlertDialog.Builder(this@LikeActivity)
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