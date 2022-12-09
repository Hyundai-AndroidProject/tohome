package com.example.hyundai_to_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hyundai_to_home.databinding.ActivityWaitingCancelActiivityBinding
import com.example.hyundai_to_home.databinding.ActivityWaitingCompleteBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.WaitingDao
/**
 * 클래스 설명 : 웨이팅 신청 이후 웨이팅 상세내역을 보여주는 클래스
 *
 * @author  신기원
 * 신기원 - 웨이팅 취소가된 waiting 내역 조회 기능 생성
 */
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

        binding.btnOrderList.setOnClickListener {
            val intent = Intent(this, WaitingListActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * 메서드 설명 : DB의 Waiting 테이블에 저장된 값을 가져와 뷰 구성요소에 지정
     *
     * @param   회원 ID, 가게 ID
     * @return  Void
     */

    private fun waitingCancelDetail(memberId: String, storeId: Int) {
        Thread {
            val waiting = waitingDao.findWaitingById(memberId, storeId)

            binding.waitingNumber.text = waitingDao.waitingSameStore(storeId).toString()
            binding.txtHeadcount.text = waiting.waitingHeadCount
            binding.txtDatetime.text = waiting.waitingDateTime
        }.start()
    }
}