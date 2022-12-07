package com.example.hyundai_to_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyundai_to_home.databinding.ActivityWaitingCancelActiivityBinding
import com.example.hyundai_to_home.databinding.ActivityWaitingCompleteBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.WaitingDao

class WaitingCancelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaitingCancelActiivityBinding
    private lateinit var db: AppDatabase
    private lateinit var waitingDao: WaitingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingCancelActiivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()
        val getintent: Intent = getIntent()
        val memberId = getintent.getStringExtra("memberId")!!
        val storeId = getintent.getIntExtra("storeId", 0)

        waitingCancelDetail(memberId, storeId)
    }

    private fun waitingCancelDetail(memberId: String, storeId: Int) {
        Thread {
            val waiting = waitingDao.findWaitingById(memberId, storeId)

            binding.waitingNumber.text = waiting.waitingID.toString()
            binding.txtHeadcount.text = waiting.waitingHeadCount
            binding.txtDatetime.text = waiting.waitingDateTime
        }.start()
    }
}