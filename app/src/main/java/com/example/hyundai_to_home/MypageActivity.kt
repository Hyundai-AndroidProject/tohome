package com.example.hyundai_to_home


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityMypageBinding


class MypageActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //intent를 사용해 예약리스트 액티비티로 넘어가는 리스너 구현
        binding.btnReservationList.setOnClickListener {
            val intent = Intent(this, ReservationListActivity::class.java)
            startActivity(intent)
        }

        //intent를 사용해 웨이팅예약리스트 액티비티로 넘어가는 리스너 구현
        binding.btnWaitingList.setOnClickListener {
            val intent = Intent(this, WaitingListActivity::class.java)
            startActivity(intent)
        }
    }
}