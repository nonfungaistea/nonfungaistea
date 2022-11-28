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
        //Checks to see if the user is logged in
        //getData()
        val intent = intent
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        populateBooks()
        Log.d("kitty", "SnapShot: First List Size" + listImages.size.toString())
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
    override fun onClick(book: Book)
    {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(BOOK_ID_EXTRA, book.id)
        startActivity(intent)
    }



//    private fun getData(){
//        val currentUser: FirebaseUser? = Firebase.auth.currentUser
//
//        if(currentUser == null){
//            Toast.makeText(this, "No Acc", Toast.LENGTH_SHORT).show()
//
//        } else{
//            val listImages: MutableList<String> = mutableListOf()
//            val wow = currentUser.uid
//            val database = Firebase.database.reference
//            val databaseReferencee = database.child("users").child(wow).child("images")
//            databaseReferencee.addValueEventListener(object: ValueEventListener {
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    Log.d("Heyo", "Heyo $snapshot")
//                    for (ds in snapshot.children){
//                        listImages.add(ds.value.toString())
//                    }
//                    Log.d("Heyyo", "Heyoo" + listImages.size.toString())
//                }
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//
//            //Get Snapshot, duplicate it, after add to it, then set val
//            Toast.makeText(this, "Acc Found$currentUser", Toast.LENGTH_SHORT).show()
//        }
//    }
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

    private fun populateBooks()
    {
        val book1 = Book(
            R.drawable.abtm,
            "Victoria Devine",
            "ArtWork #1",
            "The definitive text on the healing powers of the mind/body connection. In Ageless Body, Timeless Mind, world-renowned pioneer of integrative medicine Deepak Chopra goes beyond ancient mind/body wisdom and current anti-ageing research to show that you do not have to grow old. With the passage of time, you can retain your physical vitality, creativity, memory and self-esteem. Based on the theories of Ayurveda and groundbreaking research, Chopra reveals how we can use our innate capacity for balance to direct the way our bodies metabolize time and achieve our unbounded potential."
        )
        bookList.add(book1)

        val book2 = Book(
            R.drawable.tmom,
            "Amanda Lohrey",
            "ArtWork #2",
            "This is the definitive book on mindfulness from the beloved Zen master and Nobel Peace Prize nominee Thich Nhat Hanh. With his signature clarity and warmth, he shares practical exercises and anecdotes to help us arrive at greater self-understanding and peacefulness, whether we are beginners or advanced students.\n" + "\n" + "Beautifully written, The Miracle of Mindfulness is the essential guide to welcoming presence in your life and truly living in the moment from the father of mindfulness.\n"
        )
        bookList.add(book2)

        val book3 = Book(
            R.drawable.trlt,
            "M. Scott Peck",
            "ArtWork #3",
            "A timeless classic in personal development, The Road Less Travelled is a landmark work that has inspired millions. Drawing on the experiences of his career as a psychiatrist, Scott Peck combines scientific and spiritual views to guide us through the difficult, painful times in life by showing us how to confront our problems through the key principles of discipline, love and grace.Teaching us how to distinguish dependency from love, how to become a more sensitive parent and how to connect with your true self, this incredible book is the key to accepting and overcoming life's challenges and achieving a higher level of self-understanding."
        )
        bookList.add(book3)

        val book4 = Book(
            R.drawable.iewu,
            "Colleen Hoover",
            "ArtWork #4",
            "'A brave and heartbreaking novel that digs its claws into you and doesn't let go, long after you've finished it' Anna Todd, author of the After series\n" + "\n" + "'A glorious and touching read, a forever keeper' USA Today\n" + "\n" + "'Will break your heart while filling you with hope' Sarah Pekkanen, Perfect Neighbors\n",
        )
        bookList.add(book4)

        val book5 = Book(
            R.drawable.ips,
            "Ross Coulthart",
            "ArtWork #5",
            "Investigative journalist Ross Coulthart has been intrigued by UFOs since mysterious glowing lights were reported near New Zealand's Kaikoura mountains when he was a teenager. The 1978 sighting is just one of thousands since the 1940s, and yet research into UFOs is still seen as the realm of crackpots and conspiracy theorists."
        )
        bookList.add(book5)

        val book6 = Book(
            R.drawable.ttmc,
            "Richard Osman",
            "ArtWork #6",
            "In a peaceful retirement village, four unlikely friends meet up once a week to investigate unsolved murders.\n" + "\n" + "But when a brutal killing takes place on their very doorstep, the Thursday Murder Club find themselves in the middle of their first live case.\n" + "\n" + "Elizabeth, Joyce, Ibrahim and Ron might be pushing eighty but they still have a few tricks up their sleeves.",
        )
        bookList.add(book6)

        val book7 = Book(
            R.drawable.wyam,
            "Michael Robotham",
            "ArtWork #7",
            "Philomena McCarthy has defied the odds and become a promising young officer with the Metropolitan Police despite being the daughter of a notorious London gangster. Called to the scene of a domestic assault one day, she rescues a bloodied young woman, Tempe Brown, the mistress of a decorated detective. The incident is hushed up, but Phil has unwittingly made a dangerous enemy with powerful friends.\n"
        )
        bookList.add(book7)
    }


    //if (position == 1) this.startActivity(Intent(this, CreateNFT::class.java))
}