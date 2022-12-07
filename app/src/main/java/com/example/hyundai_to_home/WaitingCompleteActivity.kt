package com.example.hyundai_to_home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.hyundai_to_home.databinding.ActivityWaitingCompleteBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.WaitingDao
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WaitingCompleteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWaitingCompleteBinding
    private lateinit var db: AppDatabase
    private lateinit var waitingDao: WaitingDao


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()
        val getintent: Intent = getIntent()
        val memberId = getintent.getStringExtra("memberId")!!
        val storeId = getintent.getIntExtra("storeId", 0)

        waitingDetail(memberId, storeId)

        binding.btnCancel.setOnClickListener(this)
    }

    private fun waitingDetail(memberId: String, storeId: Int) {
        Thread {
            val waiting = waitingDao.findWaitingById(memberId, storeId)

            binding.waitingNumber.text = waiting.waitingID.toString()
            binding.txtWaiting.text = "고객님 앞에 ${waiting.waitingID!! - 1} 명 있습니다."
            binding.txtHeadcount.text = waiting.waitingHeadCount
            binding.txtDatetime.text = waiting.waitingDateTime
        }.start()


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_cancel -> {
                val getintent: Intent = getIntent()
                val memberId = getintent.getStringExtra("memberId")!!
                val storeId = getintent.getIntExtra("storeId", 0)

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val dateAndtime: String = LocalDateTime.now().format(formatter)
                Thread{
                    val waitingObject = waitingDao.findWaitingById(memberId, storeId)
                    waitingObject.waitingState="예약취소"
                    waitingDao.waitingCancel(waitingObject)
                }.start()

                val intent = Intent(this, WaitingCancelActivity::class.java)
                intent.putExtra("memberId", memberId)
                intent.putExtra("storeId", storeId)
                startActivity(intent)
            }
        }
    }
}