package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyundai_to_home.adapter.ReservationRecyclerViewAdapter
import com.example.hyundai_to_home.databinding.ActivityReservationListBinding
import com.example.hyundai_to_home.db.*

class ReservationListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityReservationListBinding

    private lateinit var db: AppDatabase
    private lateinit var reservationDao: ReservationDao
    private lateinit var storeDao: StoreDao
    private lateinit var storeList: ArrayList<Store>
    private lateinit var reservationList: ArrayList<Reservation>

    private lateinit var adapter: ReservationRecyclerViewAdapter

    private var store: Store? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityReservationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // DB 인스턴스를 가져오고 DB작업을 할 수 있는 DAO를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        reservationDao = db.ReservationDao()

        getAllReservationList()

        /*Thread {
            store = waitingDao.getWaitingOne()
            println("store: $store")
        }.start()*/
    }

    private fun getAllReservationList() {
        Thread {
            storeList = ArrayList(reservationDao.getReservationAll())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        // 리사이클러뷰 설정
        runOnUiThread {
            adapter = ReservationRecyclerViewAdapter(storeList) // ❷ 어댑터 객체 할당
            binding.reservationRecyclerView.adapter = adapter // 리사이클러뷰 어댑터로 위에서 만든 어댑터 설정
            binding.reservationRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // 레이아웃 매니저 설정
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllReservationList()
    }
}

