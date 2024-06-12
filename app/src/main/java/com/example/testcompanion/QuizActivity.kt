package com.example.testcompanion

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.testcompanion.databinding.ActivityQuizBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.StructuredAggregationQuery.Aggregation.Count
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false
    private var timeRemaining: Long = 0
    private val quizQuestions = ArrayList<QuizQuestion>()
    private var currentItemPosition = 0
    private lateinit var dialog: Dialog
    private lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        appDatabase = (applicationContext as MainActivity).appDatabase

        appDatabase = Constant.appDatabase

        if (Constant.isCheckingAnswers){
            binding.tvQuestionNo.text = (Constant.QuestionNo + 1).toString()
        }
        if (!Constant.PrepareMode){
            disableButton()
        }
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadQuizQuestions()
        quizAdapter = QuizAdapter(quizQuestions,this)
        binding.viewPager.adapter = quizAdapter

        binding.viewPager.isUserInputEnabled = false
        //
            binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Constant.universalIndex = position
                    if (!Constant.isCheckingAnswers){
                        binding.tvQuestionNo.text = (position+1).toString()
                    }
                    Constant.attempted = false
                    if (!Constant.PrepareMode){
                        disableButton()
                    }
                }
            })
        if (Constant.isCheckingAnswers){
            binding.viewPager.setCurrentItem(Constant.checkingQuestion, true)
        }
        //
        binding.btnNext.setOnClickListener {
            if (Constant.attempted && !Constant.PrepareMode){
                val nextItemPosition = currentItemPosition + 1
                if (quizQuestions.size-1==nextItemPosition){
                    binding.btnNext.text = "Finish"
                }
                Constant.totalQuestionsAttempted++
                if (nextItemPosition < quizAdapter.itemCount) {
                    binding.viewPager.setCurrentItem(nextItemPosition, true) // true for smooth scrolling
                    currentItemPosition = nextItemPosition
                }else{
                    countDownTimer.cancel()

//                    storeProgressInDatabase(Constant.totalQuestionsAttempted)

                    val intent = Intent(this,AnswerSheet::class.java)
                    startActivity(intent)
                }
                Constant.flag = false
                if (currentItemPosition>=1 && Constant.PrepareMode){
                    binding.btnBack.visibility = View.VISIBLE
                }
                quizAdapter.notifyItemChanged(Constant.universalIndex)
            }else if(Constant.PrepareMode){
                val nextItemPosition = currentItemPosition + 1
                if (quizQuestions.size-1==nextItemPosition){
                    binding.btnNext.text = "Finish"
                }
                Constant.totalQuestionsAttempted++
                if (nextItemPosition < quizAdapter.itemCount) {
                    binding.viewPager.setCurrentItem(nextItemPosition, true) // true for smooth scrolling
                    currentItemPosition = nextItemPosition
                }else{
//                    countDownTimer.cancel()
//                    val intent = Intent(this,AnswerSheet::class.java)
//                    startActivity(intent)
                    openPrepCompletedDialog()
                }
                Constant.flag = false
                if (currentItemPosition>=1 && Constant.PrepareMode){
                    binding.btnBack.visibility = View.VISIBLE
                }
                quizAdapter.notifyItemChanged(Constant.universalIndex)
            }else{

            }

        }
        binding.btnBack.setOnClickListener {
            val nextItemPosition = currentItemPosition - 1
            binding.viewPager.setCurrentItem(nextItemPosition, true) // true for smooth scrolling
            currentItemPosition = nextItemPosition
            if (currentItemPosition==0){
                binding.btnBack.visibility = View.GONE
            }
            Constant.goingBack = true
            quizAdapter.notifyItemChanged(Constant.universalIndex)
        }
        if (Constant.PrepareMode){
            binding.tagMode.text = "Prepare"
        }else{
            binding.tagMode.text = Constant.remainingTime
        }

    }

    private fun storeProgressInDatabase(progress: Int) {

        GlobalScope.launch {
            val progressEntity = Progress(
                category = Constant.Category,
                subcategory = Constant.Subject,
                section = Constant.SectionsName,
                questionsAttempted = progress
            )
            appDatabase.progressDao().insertProgress(progressEntity)
        }

    }

    private fun loadQuizQuestions() {
        val fireStore = FirebaseFirestore.getInstance()
//        val quizCollection = fireStore.collection("GAT").document("GAT").collection("Computer Science") // Replace with your collection name
        val quizCollection = fireStore.collection(Constant.Category).document(Constant.Subject).collection("Sections").document(Constant.SectionsName).collection("MCQs")
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
                    Constant.universalQuiz.add(quizQuestion)
                }

                // Notify the adapter that data has changed
                quizAdapter.notifyDataSetChanged()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.viewPager.visibility = View.VISIBLE
                if (!Constant.PrepareMode && !Constant.isCheckingAnswers){
                    countDownTimer = object : CountDownTimer(60000, 1000){ // 40 minutes in milliseconds
                        override fun onTick(millisUntilFinished: Long) {
                            //remaining time in minutes and milliseconds
                            val minutes = millisUntilFinished / 60000
                            val seconds = (millisUntilFinished % 60000) / 1000
                            timeRemaining = millisUntilFinished
                            binding.tagMode.text = String.format("%02d:%02d", minutes, seconds)
                            Constant.remainingTime = String.format("%02d:%02d", minutes, seconds)
                        }

                        override fun onFinish() {
                            binding.tagMode.text = "00:00"
                            val intent = Intent(applicationContext,AnswerSheet::class.java)
                            startActivity(intent)
                        }

                    }.start()
                    isTimerRunning = true
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors here
            }
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
            val minutes = timeRemaining / 60000
            val seconds = (timeRemaining % 60000) / 1000
            binding.tagMode.text = String.format("%02d:%02d", minutes, seconds)
        Constant.remainingTime = String.format("%02d:%02d", minutes, seconds)
    }

    private fun resumeTimer(){
        isTimerRunning = true
        countDownTimer = object : CountDownTimer(timeRemaining, 1000){ // 40 minutes in milliseconds
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding.tagMode.text = String.format("%02d:%02d", minutes, seconds)
                Constant.remainingTime = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.tagMode.text = "00:00"
                val intent = Intent(applicationContext,AnswerSheet::class.java)
                startActivity(intent)
            }

        }.start()

    }

    private fun cancelTimer() {
        countDownTimer.cancel()
        binding.tagMode.text = "00:00"
        isTimerRunning = false
        timeRemaining = 0
    }

    private fun openExitCustomDialog(){
        dialog.setContentView(R.layout.exit_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnExit =   dialog.findViewById<LinearLayout>(R.id.btnExit)
        val btnResume = dialog.findViewById<LinearLayout>(R.id.btnResume)
        dialog.setCancelable(false)
        dialog.show()
        btnResume.setOnClickListener {
            dialog.dismiss()
        }
        btnExit.setOnClickListener {
            Constant.universalQuiz.clear()
            storeProgressInDatabase(Constant.totalQuestionsAttempted)
            Constant.totalQuestionsAttempted = 0
            dialog.dismiss()
            finish()
        }

        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                // Dismiss the dialog and return true to consume the event
                dialog.dismiss()
                true
            } else {
                // Return false to allow other key events to be processed
                false
            }
        }

        // Set an OnDismissListener to resume the timer when the dialog is dismissed
        dialog.setOnDismissListener {
            if (!Constant.PrepareMode){
                resumeTimer()
            }
        }
    }

    private fun openPrepCompletedDialog(){
        dialog.setContentView(R.layout.prep_completed_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnRetake =   dialog.findViewById<LinearLayout>(R.id.btnRetake)
        val btnTest = dialog.findViewById<LinearLayout>(R.id.btnTest)
        dialog.setCancelable(false)
        dialog.show()
        btnRetake.setOnClickListener {
            binding.viewPager.setCurrentItem(0, true)
            currentItemPosition = 0
            binding.btnBack.visibility = View.GONE
            binding.btnNext.text = "Next"
            dialog.dismiss()
        }
        btnTest.setOnClickListener {
            Constant.PrepareMode = false
            finish()
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }

    fun disableButton(){
        binding.btnNext.background = resources.getDrawable(R.drawable.disable_button_design)
    }

    fun enableButton(){
        binding.btnNext.background = resources.getDrawable(R.drawable.button_design)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        if (!Constant.isCheckingAnswers){
            Constant.selectedOptions.clear()
        }
        Constant.universalIndex = 0
        if (!Constant.isCheckingAnswers){
            if (isTimerRunning){
                pauseTimer()
                openExitCustomDialog()
            }else{
                openExitCustomDialog()
            }
        }else{
            Constant.universalQuiz.clear()
            Constant.totalQuestionsAttempted = 0
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.universalQuiz.clear()
        Constant.totalQuestionsAttempted = 0
        if (!Constant.PrepareMode && !Constant.isCheckingAnswers){
            if(timeRemaining>0){
                countDownTimer.cancel()
            }
        }
    }
}