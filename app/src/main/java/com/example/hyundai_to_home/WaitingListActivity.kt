package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityWaitingListBinding

class WaitingListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWaitingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}