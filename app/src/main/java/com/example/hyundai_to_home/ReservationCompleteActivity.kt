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
        // val memberId = getintent.getStringExtra("memberId")!!
        val memberPhone = getintent.getStringExtra("memberPhone")
        val storeId = getintent.getIntExtra("storeId", 0)
        val memberId = MyApplication.email.toString()
        Log.i("--------------------------id",memberId)
        Log.i("--------------------------storeid",storeId.toString())
        // membername, id 정보 띄우기
        // storeid로 가게 이름 , 위치 가져오기
        // reservation 정보 가져오기
        binding.reservationMemberNameInfo.text = memberId
        binding.reservationMemberPhoneInfo.text = memberPhone

        // 로그인한 memberid로 바꿔야 함
        storeDetail(storeId)
        reservationDetail(memberId,storeId)

        binding.btnStoreList.setOnClickListener{
            val intent = Intent(this, ReservationListActivity::class.java)
            startActivity(intent)
        }

        binding.btnReservationCancel.setOnClickListener {
            deleteReservation(memberId, storeId)
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

    fun reservationDetail(memberId : String, storeId : Int) {
        Thread {
            val reservation = reservationDao.findReservationById(memberId, storeId)

            binding.reservationStateInfo.text = reservation.reservationSate.toString()
            binding.reservationHeadCountInfo.text = "${reservation.reservationHeadCount.toString()} 명"
            binding.reservationNowInfo.text = reservation.reservationRegisterDate
            binding.reservationRequestContentInfo.text = reservation.reservationContent.toString()
            binding.reservationDate.text = reservation.reservationFixedDate.toString()
            binding.reservationTime.text = reservation.reservationFixedTime.toString()
        }.start()
    }

    fun deleteReservation(memberId : String, storeId : Int) {
        Thread {
            val deleteReservation = reservationDao.cancelReservationById(memberId, storeId, "예약 취소")
            runOnUiThread {
                Toast.makeText(this, "예약이 취소 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

}