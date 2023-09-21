package com.example.testcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testcompanion.databinding.ActivitySubjectsBinding
import com.google.firebase.firestore.FirebaseFirestore

class SubjectsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubjectsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)
        binding = ActivitySubjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCategoryName = intent.getStringExtra("categoryName")
        binding.selectedCourseName.text = selectedCategoryName
        val subjectsName = ArrayList<String>()
        val subjectsImageList = ArrayList<Int>()
        subjectsImageList.add(R.drawable.general_knowledge_icon)
        subjectsImageList.add(R.drawable.computer_science_icon)
        subjectsImageList.add(R.drawable.physics_icon)
        subjectsImageList.add(R.drawable.chemistry_icon)
        subjectsImageList.add(R.drawable.biology_icon)
        subjectsImageList.add(R.drawable.english_icon)
        subjectsImageList.add(R.drawable.math_icon)
        subjectsImageList.add(R.drawable.islamic_studies_icon)
        subjectsImageList.add(R.drawable.geography_icon)
        subjectsImageList.add(R.drawable.current_affairs_icon)
        subjectsName.add("G Knowledge")
        subjectsName.add("Computer")
        subjectsName.add("Physics")
        subjectsName.add("Chemistry")
        subjectsName.add("Biology")
        subjectsName.add("English")
        subjectsName.add("Mathematics")
        subjectsName.add("Islamic Studies")
        subjectsName.add("Geography")
        subjectsName.add("Current Affairs")


        val layoutManager = GridLayoutManager(this, 2) // Set the number of columns
        binding.subjectsRecyclerView.layoutManager = layoutManager
        val adapter = SubjectsAdapter(subjectsName,subjectsImageList,this)
        binding.subjectsRecyclerView.adapter = adapter
        binding.btnPrepare.setOnClickListener {
            binding.btnPrepare.background = getDrawable(R.drawable.selected_switch_btn_design)
            binding.btnTest.background = null
            binding.tvModePrepare.setTextColor(resources.getColor(R.color.white))
            binding.tvModeTest.setTextColor(resources.getColor(R.color.lite_black))
        }
        binding.btnTest.setOnClickListener {
            binding.btnTest.background = getDrawable(R.drawable.selected_switch_btn_design)
            binding.btnPrepare.background = null
            binding.tvModePrepare.setTextColor(resources.getColor(R.color.lite_black))
            binding.tvModeTest.setTextColor(resources.getColor(R.color.white))
        }

    }

    fun goToQuizActivity(){
        val intent = Intent(this,QuizActivity::class.java)
        startActivity(intent)
    }

}