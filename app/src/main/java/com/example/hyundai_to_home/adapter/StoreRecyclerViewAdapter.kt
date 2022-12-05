package com.example.hyundai_to_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hyundai_to_home.StoreListActivity
import com.example.hyundai_to_home.databinding.ItemStoreBinding
import com.example.hyundai_to_home.db.StoreEntity

class StoreRecyclerViewAdapter(
    private val storeList: ArrayList<StoreEntity>,
    storeListActivity: StoreListActivity
) :
    RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        val storeName = binding.storeName
        val storeContent = binding.storeContent

        // 뷰 바인딩에서 기본적으로 제공하는 root 변수는 레이아웃의 루트 레이아웃을 의미합니다.
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // item_store.xml 바인딩 객체 생성
        val binding: ItemStoreBinding =
            ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeData = storeList[position]

        // 식당 이름
        holder.storeName.text = storeData.storeName.toString()
        // 식당 정보
        holder.storeContent.text = storeData.storeContent

    }

    override fun getItemCount(): Int {
        // 리사이클러뷰 아이템 개수는 할 일 리스트의 크기
        return storeList.size
    }
}