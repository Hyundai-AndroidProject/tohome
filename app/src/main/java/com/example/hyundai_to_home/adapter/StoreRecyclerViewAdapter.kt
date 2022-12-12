package com.example.hyundai_to_home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyundai_to_home.ReservationActivity
import com.example.hyundai_to_home.WaitingActivity
import com.example.hyundai_to_home.databinding.ItemStoreBinding
import com.example.hyundai_to_home.db.Store
/**
 * 클래스 설명 : 예약 리스트를 띄우기 위한 어댑터
 *
 * @author  정승하
 * 정승하 - 식당 리스트를 띄우기 위한 어댑터 구현
 * 정승하 - 예약, 웨이팅 버튼으로 예약, 웨이팅 액티비티으로 전환하도록 구현
 */
class StoreRecyclerViewAdapter(
    val storeList: ArrayList<Store>
) : RecyclerView.Adapter<StoreRecyclerViewAdapter.Holder>() {

    inner class Holder(
        private val binding: ItemStoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(item: Store) {
            binding.storeName.text = item.storeName
            binding.storeContent.text = item.storeContent

            println("image: ${item.storeImage}")

            Glide.with(context)
                .load(item.storeImage)
                .into(binding.storeImage)

            binding.btnReservation.setOnClickListener {
                val intent = Intent(context, ReservationActivity::class.java).apply {
                    putExtra("store_id", item.storeId)
                }
                context.startActivity(intent)
            }
            binding.btnWaiting.setOnClickListener {
                val intent = Intent(context, WaitingActivity::class.java).apply {
                    putExtra("store_id", item.storeId)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // item_store.xml 바인딩 객체 생성
        val binding: ItemStoreBinding =
            ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(storeList[position])
    }

    override fun getItemCount(): Int {
        // 리사이클러뷰 아이템 개수는 할 일 리스트의 크기
        return storeList.size
    }
}
