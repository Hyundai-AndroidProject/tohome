package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyundai_to_home.adapter.StoreRecyclerViewAdapter
import com.example.hyundai_to_home.adapter.WaitingRecyclerViewAdapter
import com.example.hyundai_to_home.databinding.ActivityWaitingListBinding
import com.example.hyundai_to_home.db.*

/**
 * 클래스 설명 : 사용자의 웨이팅 목록을 보여주는 클래스
 *
 * @author  이름1, 이름2
 * 이름1 - 기능1 구현
 * 이름2 - 기능2 구현
 * 이름3 - 기능3 구현
 */

class WaitingListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWaitingListBinding

    private lateinit var db: AppDatabase
    private lateinit var waitingDao: WaitingDao
    private lateinit var storeDao: StoreDao
    private lateinit var storeList: ArrayList<Store>
    private lateinit var waitingList: ArrayList<Waiting>

    private lateinit var adapter: WaitingRecyclerViewAdapter

    private var store: Store? = null
    /**
     * 메서드 설명 : A 기능을 한다
     * A+B 연산을 한다
     *
     * @param   파라미터 값
     * @return  반환 값
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // DB 인스턴스를 가져오고 DB작업을 할 수 있는 DAO를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()
        binding.userName.text = MyApplication.email
        getAllWaitingList()

        binding.btnBack.setOnClickListener {
            finish()
        }

        /*Thread {
            store = waitingDao.getWaitingOne()
            println("store: $store")
        }.start()*/
    }

    private fun getAllWaitingList() {
        val memberId = MyApplication.email!!
        Thread {
            storeList = ArrayList(waitingDao.getWaitingAll(memberId))
            waitingList = ArrayList(waitingDao.getWaiting(memberId))
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        // 리사이클러뷰 설정
        runOnUiThread {
            adapter = WaitingRecyclerViewAdapter(storeList, waitingList) // ❷ 어댑터 객체 할당
            binding.waitingRecyclerView.adapter = adapter // 리사이클러뷰 어댑터로 위에서 만든 어댑터 설정
            binding.waitingRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // 레이아웃 매니저 설정
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllWaitingList()
    }
}