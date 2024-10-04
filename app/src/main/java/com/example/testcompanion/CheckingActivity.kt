package com.example.testcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testcompanion.Adapters.CheckingAdapter
import com.example.testcompanion.ConstantVariables.Constant
import com.example.testcompanion.databinding.ActivityCheckingBinding

class CheckingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckingBinding
    private lateinit var checkingAdapter: CheckingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking)
        binding = ActivityCheckingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkingAdapter = CheckingAdapter(Constant.universalQuiz,this)
        binding.viewPager.adapter = checkingAdapter
        binding.viewPager.isUserInputEnabled = false

    }
}