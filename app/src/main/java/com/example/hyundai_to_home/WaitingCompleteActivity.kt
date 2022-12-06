package com.example.hyundai_to_home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.hyundai_to_home.databinding.ActivityWaitingCompleteBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.WaitingDao

class WaitingCompleteActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWaitingCompleteBinding
    private lateinit var db : AppDatabase
    private lateinit var waitingDao : WaitingDao
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()

        var intent : Intent = getIntent()
        var memberId = intent.getStringExtra("memberId")!!
        var storeId = intent.getIntExtra("storeId", 0)
//        Log.d("ㅅㅄㅂ", intent.getIntExtra("storeId", 0).toString())
        waitingDetail(memberId, storeId)
    }

    private fun waitingDetail(memberId: String, storeId: Int) {
        Thread{
            val waiting = waitingDao.findWaitingById(memberId, storeId)

            binding.waitingNumber.text = waiting.waitingID.toString()
            binding.txtHeadcount.text = waiting.waitingHeadCount
            binding.txtDatetime.text = waiting.waitingDateTime
        }.start()


    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}