package com.example.hyundai_to_home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityReservationCompleteBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.ReservationDao
import com.example.hyundai_to_home.db.StoreDao
/**
 * 클래스 설명 : 식당 예약 완료 상세 화면을 띄우는 클래스
 *
 * @author  박서은
 * 박서은 - 식당 정보 & 예약 인원 & 날짜 & 시간을 통해 예약 상세화면 뜨도록 구현
 * 박서은 - reservationid, storeid를 받아와 데이터 조회
 */
class ReservationCompleteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityReservationCompleteBinding
    private lateinit var db : AppDatabase
    private lateinit var storeDao: StoreDao
    private lateinit var reservationDao: ReservationDao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityReservationCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        db = AppDatabase.getInstance(this)!!
        reservationDao = db.ReservationDao()
        storeDao = db.StoreDao()

        val getintent: Intent = getIntent()
        val memberPhone = getintent.getStringExtra("memberPhone")
        val reservationId = getintent.getIntExtra("reservationId", 0)
        val storeId = getintent.getIntExtra("storeId", 0)
        val memberId = MyApplication.email.toString()

        // membername, id 정보 띄우기
        // storeid로 가게 이름 , 위치 가져오기
        // reservation 정보 가져오기
        binding.reservationMemberNameInfo.text = memberId
        binding.reservationMemberPhoneInfo.text = memberPhone

        // 로그인한 memberid로 바꿔야 함
        storeDetail(storeId)
        reservationDetail(reservationId)

        binding.btnStoreList.setOnClickListener{
            val intent = Intent(this, ReservationListActivity::class.java)
            startActivity(intent)
        }

        binding.btnReservationCancel.setOnClickListener {
            deleteReservation(reservationId)
            val intent = Intent(this, StoreListActivity::class.java)
            startActivity(intent)
        }
    }

    fun storeDetail(storeId : Int) {
        Thread {
            val store = storeDao.getStoreOne(storeId)
            binding.storeName.text = store.storeName.toString()
            binding.storeLocation.text = store.storeDepartment.toString()

        }.start()
    }

    fun reservationDetail(reservationId : Int) {
        Thread {
            val reservation = reservationDao.findReservationById(reservationId)
            binding.reservationHeadCountInfo.text = "${reservation.reservationHeadCount.toString()} 명"
            binding.reservationNowInfo.text = reservation.reservationRegisterDate
            binding.reservationRequestContentInfo.text = reservation.reservationContent.toString()
            binding.reservationDate.text = reservation.reservationFixedDate.toString()
            binding.reservationTime.text = reservation.reservationFixedTime.toString()
            binding.reservationStateInfo.text = reservation.reservationSate.toString()
        }.start()
    }

    fun deleteReservation(reservationId : Int) {
        Thread {
            val deleteReservation = reservationDao.cancelReservationById(reservationId, "예약 취소")
            runOnUiThread {
                Toast.makeText(this, "예약이 취소 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}