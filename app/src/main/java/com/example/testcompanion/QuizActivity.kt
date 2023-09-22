package com.example.testcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testcompanion.databinding.ActivityQuizBinding
import com.google.firebase.firestore.FirebaseFirestore

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizAdapter: QuizAdapter
    private val quizQuestions = ArrayList<QuizQuestion>()
    private var currentItemPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizAdapter = QuizAdapter(quizQuestions,this)
        binding.viewPager.adapter = quizAdapter
        loadQuizQuestions()
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvQuestionNo.text = (position+1).toString()
            }
        })
        binding.btnNext.setOnClickListener {
            val nextItemPosition = currentItemPosition + 1
            if (nextItemPosition < quizAdapter.itemCount) {
                binding.viewPager.setCurrentItem(nextItemPosition, true) // true for smooth scrolling
                currentItemPosition = nextItemPosition
            }
            Constant.flag = false
            if (currentItemPosition>=1){
                binding.btnBack.visibility = View.VISIBLE
            }
        }
        binding.btnBack.setOnClickListener {
            val nextItemPosition = currentItemPosition - 1
            binding.viewPager.setCurrentItem(nextItemPosition, true) // true for smooth scrolling
            currentItemPosition = nextItemPosition
            if (currentItemPosition==0){
                binding.btnBack.visibility = View.GONE
            }

        }

    }

    private fun loadQuizQuestions() {
        val fireStore = FirebaseFirestore.getInstance()
        val quizCollection = fireStore.collection("GAT").document("GAT").collection("Computer Science") // Replace with your collection name

        quizCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val question = document.getString("Question") ?: ""
                    val options = listOf(
                        document.getString("option1") ?: "",
                        document.getString("option2") ?: "",
                        document.getString("option3") ?: "",
                        document.getString("option4") ?: ""
                    )
                    val answer = document.getString("Answer") ?: ""

                    val quizQuestion = QuizQuestion(question, options, answer)
                    quizQuestions.add(quizQuestion)
                }

                // Notify the adapter that data has changed
                quizAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors here
            }
    }

}