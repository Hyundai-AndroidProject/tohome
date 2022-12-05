package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityReservationCompleteBinding
import com.example.hyundai_to_home.databinding.ActivityWaitingBinding

class ReservationCompleteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityReservationCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityReservationCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}