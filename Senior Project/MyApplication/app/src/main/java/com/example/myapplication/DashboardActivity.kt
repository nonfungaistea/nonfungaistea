package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityDashboardBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigationrail.NavigationRailView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DashboardActivity() : AppCompatActivity(), BookClickListener  {

    private lateinit var binding: ActivityDashboardBinding
    lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    val listImages: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainActivity = this
        val currentUser: FirebaseUser? = Firebase.auth.currentUser
        listImages.clear()
        if(currentUser == null){
            Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()
        } else{
            val wow = currentUser.uid
            val database = Firebase.database.reference
            val databaseReferencee = database.child("users").child(wow).child("images")
            var counter = 0
            databaseReferencee.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    Log.d("kitty", "Starting With... Size " + listImages.size.toString())

                    for (ds in snapshot.children){
//                        Log.d("kitty", "New Snapshot...")
                        if (counter == 0){
//                            Log.d("kitty", "Should be null")
                        } else {
                            Log.d("zadi", "DSVALUE: " + ds.value.toString())
                            listImages.add(ds.value.toString())
                        }
                        counter += 1
                        binding.recyclerView.apply {
                            layoutManager = GridLayoutManager(applicationContext, 2)
                            adapter = CardAdapter(listImages, mainActivity)
//                            Log.d("Kitty", "Going inside with: " + listImages.size)
                        }
                    }
//                    Log.d("kitty", "Ending... Size " + listImages.size.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        val navRailFab: FloatingActionButton = findViewById(R.id.nav_rail_fab)

        navRailFab.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.activity_picture, null)
            val customDialog = AlertDialog.Builder(this@DashboardActivity)
                .setView(dialogView)
                .show()
            val btDismiss:Button = dialogView.findViewById(R.id.close)
            btDismiss.setOnClickListener {
                customDialog.dismiss()
            }
            imageView = dialogView.findViewById(R.id.imageView)
            var buttonLoad: Button = dialogView.findViewById(R.id.loadPicture)
            var buttonCustomize: Button = dialogView.findViewById(R.id.customize!!)
            buttonLoad.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
                galleryIntent.type = "image/*"
                openSomeActivityForResult(galleryIntent)
                showHide(buttonLoad)
                showHide(buttonCustomize)
            }

            buttonCustomize.setOnClickListener{
                val extras = Bundle()
                val intent = Intent(this, Artwork::class.java)
                extras.putString("KEY", imageUri.toString())
                intent.putExtras(extras)
                startActivity(intent)
            }
        }

        val navigationRail: NavigationRailView = findViewById(R.id.navigationRail)
        navigationRail.setOnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.thumbsUp -> {
                val intent = Intent(this, LikeActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.files -> {
                Toast.makeText(this, "Files", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.images -> {
                Toast.makeText(this, "Images", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.settings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.profile -> {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }
    }

    private fun openSomeActivityForResult(gallery: Intent) {
        resultLauncher.launch(gallery)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data?.data != null) {
            imageUri= result.data?.data
            imageView.setImageURI(imageUri)
        }
    }

    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }

    override fun onClick(imageDBLink: String) {
        Log.d("kitty", "Book Clicked!@!" + imageDBLink)
        val dialogView = layoutInflater.inflate(R.layout.activity_mintpop, null)
        val customDialog = AlertDialog.Builder(this@DashboardActivity)
            .setView(dialogView)
            .show()
        val btDismiss:Button = dialogView.findViewById(R.id.mintClose)
        btDismiss.setOnClickListener {
            customDialog.dismiss()
        }
        var buttonLoad: Button = dialogView.findViewById(R.id.mintPicture)
        buttonLoad.setOnClickListener {
            val currentUser: FirebaseUser? = Firebase.auth.currentUser
            if(currentUser == null){
                Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()
            } else {
                val wow = currentUser.uid
                val database = Firebase.database.reference
                val databaseReferencee = database.child("users").child(wow).child("mintLink")
                databaseReferencee.setValue(imageDBLink)
            }
        }
    }
}
