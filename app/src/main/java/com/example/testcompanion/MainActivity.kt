package com.example.testcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.testcompanion.Adapters.CategoryAdapter
import com.example.testcompanion.ConstantVariables.Constant
import com.example.testcompanion.RoomDatabase.AppDatabase
import com.example.testcompanion.RoomDatabase.Converters
import com.example.testcompanion.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
        Constant.appDatabase = appDatabase

        drawerLayout= findViewById(R.id.drawerLayout)
        val customHamburgerIcon: ImageView = findViewById(R.id.customHamburgerIcon)

        val navView: NavigationView = findViewById(R.id.nav_view)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open,
            R.string.close
        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val categoryName = ArrayList<String>()
        val courseImageList = ArrayList<Int>()
        courseImageList.add(R.drawable.gat_icon)
        courseImageList.add(R.drawable.nat_icon)
        courseImageList.add(R.drawable.ppsc_icon)
        courseImageList.add(R.drawable.mdcat_icon)
        courseImageList.add(R.drawable.psa_icon)
        categoryName.add("GAT")
        categoryName.add("NTS")
        categoryName.add("PPSC")
        categoryName.add("MDCAT")
        categoryName.add("PSA")
        val adapter = CategoryAdapter(categoryName,courseImageList,this)
        binding.recyclerView.adapter = adapter

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        customHamburgerIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> {
                    // Handle Profile menu item click
                    // Replace with your logic
                }
                // Add more menu item handling as needed
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    fun goToTestActivity(categoryName: String) {
        Constant.Category = categoryName
        val intent = Intent(this,SubjectsActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        binding.searchField.text.clear()
        super.onResume()
    }

}