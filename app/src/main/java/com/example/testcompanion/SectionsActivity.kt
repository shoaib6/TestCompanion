package com.example.testcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testcompanion.databinding.ActivitySectionsBinding
import com.google.firebase.firestore.FirebaseFirestore

class SectionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionsBinding
    private lateinit var sectionsAdapter: SectionsAdapter
    private val sectionNames = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)
        binding = ActivitySectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.subjectName.text = Constant.Subject
        loadSectionNames()


    }

    private fun loadSectionNames() {
        val db = FirebaseFirestore.getInstance()
        val sectionsCollectionRef = db.collection(Constant.Category)
            .document("General Knowledge")
            .collection("Sections")

// Query the "Sections" subcollection
        sectionsCollectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    // Get the name of each section document
                    val sectionName = document.id
                    // Add the section name to the ArrayList
                    sectionNames.add(sectionName)
                    val layoutManager = GridLayoutManager(this,4)
                    binding.sectionsRecyclerview.addItemDecoration(GridSpacingItemDecoration(this,4,2,true))
                    sectionsAdapter = SectionsAdapter(this,sectionNames)
                    binding.sectionsRecyclerview.layoutManager = layoutManager
                    binding.sectionsRecyclerview.adapter = sectionsAdapter
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.sectionsRecyclerview.visibility = View.VISIBLE
                }

                // After adding all section names, you can use the list for your RecyclerView
                // For example, if you have a RecyclerView adapter:
                // recyclerView.adapter = YourAdapter(sectionNames)

                // Alternatively, you can process the list here
                for (sectionName in sectionNames) {

                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }

    }

    fun goToQuizActivity(position: Int) {
        Constant.flag = false
        Constant.SectionsName = sectionNames[position]
        val intent = Intent(this,QuizActivity::class.java)
        startActivity(intent)
    }

}