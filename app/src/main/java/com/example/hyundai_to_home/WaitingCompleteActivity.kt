package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.hyundai_to_home.databinding.ActivityWaitingCompleteBinding

class WaitingCompleteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWaitingCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}