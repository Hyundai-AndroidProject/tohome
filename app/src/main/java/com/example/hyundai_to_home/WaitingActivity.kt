package com.example.hyundai_to_home


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityWaitingBinding

class WaitingActivity: AppCompatActivity(){
    private lateinit var binding: ActivityWaitingBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //intent를 사용해 다음 액티비티로 넘어가는 리스너 구현
        binding.btnWaitingComplete.setOnClickListener {
            val intent = Intent(this, WaitingCompleteActivity::class.java)
            startActivity(intent)
        }
    }
}