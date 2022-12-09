package com.example.hyundai_to_home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.hyundai_to_home.databinding.ActivityWaitingCompleteBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.StoreDao
import com.example.hyundai_to_home.db.WaitingDao
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
/**
 * 클래스 설명 : 웨이팅 신청 이후 웨이팅 상세내역을 보여주는 클래스
 *
 * @author  신기원
 * 신기원 -
 */
class WaitingCompleteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWaitingCompleteBinding
    private lateinit var db: AppDatabase
    private lateinit var waitingDao: WaitingDao
    private lateinit var storeDao: StoreDao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()
        storeDao = db.StoreDao()
        val getintent: Intent = getIntent()
        val memberId = getintent.getStringExtra("memberId")!!
        val storeId = getintent.getIntExtra("storeId", 0)
        Log.d("com", memberId)
        waitingDetail(memberId, storeId)

        binding.btnCancel.setOnClickListener(this)
        binding.btnOrderList.setOnClickListener(this)
    }

    /**
     * 메서드 설명 : DB의 Waiiting 테이블에 저장된 값을 가져와 뷰 구성요소에 지정
     *
     * @param   회원 ID, 가게 ID
     * @return  Void
     */
    private fun waitingDetail(memberId: String, storeId: Int) {
        Thread {
            val waiting = waitingDao.findWaitingById(memberId, storeId)

            //같은 식당을 예약한 사람 수
            var waitingNumber = waitingDao.waitingSameStore(storeId)
            binding.waitingNumber.text = waitingNumber.toString()
            binding.txtWaiting.text = "고객님 앞에 ${waitingNumber!! - 1} 명 있습니다."
            binding.txtHeadcount.text = waiting.waitingHeadCount
            binding.txtDatetime.text = waiting.waitingDateTime
            binding.txtStoreContent.text = storeDao.getStoreOne(storeId).storeContent
        }.start()


    }
    /**
     * 메서드 설명 : 뷰에 대한 클릭 이벤트를 처리한다
     * 웨이팅 취소 버튼을 통해 waitingState의 상태를 변경
     * 리스트 목록으로 이동
     * @param   view 구성 요소
     * @return  Void
     */
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_cancel -> {
                val getintent: Intent = getIntent()
                val memberId = getintent.getStringExtra("memberId")!!
                val storeId = getintent.getIntExtra("storeId", 0)

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val dateAndtime: String = LocalDateTime.now().format(formatter)
                Thread{
                    //웨이팅 취소로 상태 변경
                    val waitingObject = waitingDao.findWaitingById(memberId, storeId)
                    waitingObject.waitingState="예약취소"
                    waitingObject.waitingDateTime = dateAndtime
                    waitingDao.waitingCancel(waitingObject)
                }.start()

                val intent = Intent(this, WaitingCancelActivity::class.java)
                intent.putExtra("memberId", memberId)
                intent.putExtra("storeId", storeId)
                startActivity(intent)
            }
            R.id.btn_order_list -> {
                val intent = Intent(this, WaitingListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}