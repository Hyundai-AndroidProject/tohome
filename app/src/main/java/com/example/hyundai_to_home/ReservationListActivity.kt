package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityReservationListBinding

class ReservationListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityReservationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityReservationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}