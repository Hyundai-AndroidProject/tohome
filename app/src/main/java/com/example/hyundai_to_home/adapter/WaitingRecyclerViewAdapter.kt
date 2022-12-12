package com.example.hyundai_to_home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyundai_to_home.WaitingCompleteActivity
import com.example.hyundai_to_home.databinding.ItemWaitingBinding
import com.example.hyundai_to_home.db.Store
import com.example.hyundai_to_home.db.Waiting

/**
 * 클래스 설명 : 웨이팅 목록을 보여주기 위한 Adapter 클래스 
 *
 * @author  신기원, 정승하
 * 정승하 - 초기 Waiting 테이블로부터 Join을 통하여 Store 타입의 데이터 반환
 * 신기원 - 기존의 Store 타입이외의 Waiting 타입을 추가, Waiting에 대한 bind함수 생성
 * 신기원 - 아이템 뷰 클릭시 해당 웨이팅 상세내역으로 넘어가는 클릭 이벤트 생성, 아이템 뷰에 waitingState 추가
 * 
 */

class WaitingRecyclerViewAdapter(
    val waitingList: ArrayList<Store>, val waiting : ArrayList<Waiting>
) : RecyclerView.Adapter<WaitingRecyclerViewAdapter.Holder>() {

    inner class Holder(
        private val binding: ItemWaitingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context
        /**
         * 메서드의 기능을 설명 중이다.. 간략하게 적을 것.
         *
         * @ param int a 메서드의 파라미터 설명
         * @ return 반환하는 것 설명
         * @ exception 예외사항
         */
        fun bind(item: Store) {
            binding.storeName.text = item.storeName
            binding.storeContent.text = item.storeContent

            println("image: ${item.storeImage}")

            Glide.with(context)
                .load(item.storeImage)
                .into(binding.storeImage)


        }

        fun bind2(wait: Waiting) {
            binding.storeState.text = wait.waitingState

            binding.itemLayout.setOnClickListener{
                val intent = Intent(context, WaitingCompleteActivity::class.java).apply {
                    putExtra("memberId", wait.memberId)
                    putExtra("storeId", wait.storeId)
                }

                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // item_store.xml 바인딩 객체 생성
        val binding: ItemWaitingBinding =
            ItemWaitingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(waitingList[position])
        holder.bind2(waiting[position])
    }

    override fun getItemCount(): Int {
        // 리사이클러뷰 아이템 개수는 할 일 리스트의 크기
        return waitingList.size
    }
}
