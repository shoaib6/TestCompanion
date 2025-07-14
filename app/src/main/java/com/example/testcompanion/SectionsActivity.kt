package com.example.testcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testcompanion.Adapters.SectionsAdapter
import com.example.testcompanion.ConstantVariables.Constant
import com.example.testcompanion.RoomDatabase.AppDatabase
import com.example.testcompanion.databinding.ActivitySectionsBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SectionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionsBinding
    private lateinit var sectionsAdapter: SectionsAdapter
    private val sectionNames = ArrayList<String>()
    private lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)
        binding = ActivitySectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.subjectName.text = Constant.Subject
        appDatabase = Constant.appDatabase
        loadSectionNames()

    }

    private fun loadSectionNames() {
        val db = FirebaseFirestore.getInstance()
        val sectionsCollectionRef = db.collection(Constant.Category)
            .document(binding.subjectName.text.toString())
            .collection("Sections")

// Query the "Sections" subcollection
        sectionsCollectionRef.get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot.isEmpty) {
                    // 🚫 No sections found
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.sectionsRecyclerview.visibility = View.GONE
//                    Toast.makeText(this, "No sections found for this subject.", Toast.LENGTH_SHORT).show()
                    binding.noDataAvailable.visibility = View.VISIBLE
                    return@addOnSuccessListener
                }

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

    suspend fun getProgress(sectionName: String): Float{
        return withContext(Dispatchers.IO) {
           val progress = appDatabase.progressDao().getProgress(Constant.Category, Constant.Subject, sectionName)
            progress?.questionsAttempted?.toFloat() ?:0.0f
        }
    }

    override fun onResume() {
        super.onResume()
        binding.sectionsRecyclerview.adapter?.notifyDataSetChanged()
        Constant.selectedOptions.clear()
    }

}