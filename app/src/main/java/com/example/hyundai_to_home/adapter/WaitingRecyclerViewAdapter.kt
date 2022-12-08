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
 * Serialize 기능을 사용하여 file에 object 저장?
 * 해당 클래스에 대한 기능과 용도를 적어줍니다.
 *
 * @author
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
