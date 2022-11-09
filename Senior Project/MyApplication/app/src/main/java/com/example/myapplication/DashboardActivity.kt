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


class DashboardActivity() : AppCompatActivity()  {

    private lateinit var binding: ActivityDashboardBinding
    lateinit var imageView: ImageView
    lateinit var database: DatabaseReference
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Checks to see if the user is logged in
        getData()
        val intent = intent
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            R.id.files -> {
                Toast.makeText(this, "Files", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.images -> {
                Toast.makeText(this, "Images", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.wallet -> {
                Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show()
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
    private fun getData(){
        val currentUser: FirebaseUser? = Firebase.auth.currentUser

        if(currentUser == null){
            Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()

        } else{
            val listImages: MutableList<String> = mutableListOf()
            val wow = currentUser.uid
            val database = Firebase.database.reference
            val databaseReferencee = database.child("users").child(wow).child("images")
            databaseReferencee.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("Heyo", "Heyo $snapshot")
                    for (ds in snapshot.children){
                        listImages.add(ds.value.toString())
                    }
                    Log.d("Heyyo", "Heyoo" + listImages.size.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            //Get Snapshot, duplicate it, after add to it, then set val
            Toast.makeText(this, "Acc Found$currentUser", Toast.LENGTH_SHORT).show()
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

    //if (position == 1) this.startActivity(Intent(this, CreateNFT::class.java))
}