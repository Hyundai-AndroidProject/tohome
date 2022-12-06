package com.example.hyundai_to_home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityDepartmentListBinding
import com.example.hyundai_to_home.databinding.ActivityWaitingBinding

class DepartmentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDepartmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityDepartmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}