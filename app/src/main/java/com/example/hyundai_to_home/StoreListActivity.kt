package com.example.hyundai_to_home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hyundai_to_home.adapter.StoreRecyclerViewAdapter
import com.example.hyundai_to_home.databinding.ActivityStoreListBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.StoreDao
import com.example.hyundai_to_home.db.StoreEntity

class StoreListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreListBinding

    private lateinit var db: AppDatabase
    private lateinit var storeDao: StoreDao
    private lateinit var storeList: ArrayList<StoreEntity>
    private lateinit var adapter: StoreRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DB 인스턴스를 가져오고 DB작업을 할 수 있는 DAO를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        storeDao = db.StoreDao()

        getAllStoreList()
    }

    private fun getAllStoreList() {
        Thread {
            storeList = ArrayList(storeDao.getStoreAll())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        // 리사이클러뷰 설정
        runOnUiThread {
            adapter = StoreRecyclerViewAdapter(storeList) // ❷ 어댑터 객체 할당
            binding.storeRecyclerView.adapter = adapter // 리사이클러뷰 어댑터로 위에서 만든 어댑터 설정
            binding.storeRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // 레이아웃 매니저 설정
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllStoreList()
    }
}