package com.example.hyundai_to_home.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyundai_to_home.databinding.ItemReservationBinding
import com.example.hyundai_to_home.db.*
import com.example.hyundai_to_home.listner.OnClickListener
/**
 * 클래스 설명 : 예약 리스트를 띄우기 위한 어댑터
 *
 * @author  정승하, 박서은
 * 정승하 - 예약 리스트를 띄우기 위한 어댑터 구현
 * 박서은 - 예약 리스트 클릭시 상세 내역 조회를 위한 기능 구현
 */
class ReservationRecyclerViewAdapter(
    val storeList: ArrayList<Store>, val reservationlist : ArrayList<Reservation> , private val onClickListener: OnClickListener
) : RecyclerView.Adapter<ReservationRecyclerViewAdapter.Holder>() {

    private var reservationID = -1

    inner class Holder(
        private val binding: ItemReservationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(item: Store) {

            binding.storeName.text = item.storeName
            binding.storeContent.text = item.storeContent

            println("image: ${item.storeImage}")

            Glide.with(context)
                .load(item.storeImage)
                .into(binding.storeImage)
        }

        fun bind2(item: Reservation) {
            binding.storeSate.text = item.reservationSate
            reservationID = item.reservationID!!
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // item_store.xml 바인딩 객체 생성
        val binding: ItemReservationBinding =
            ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(storeList[position])
        holder.bind2(reservationlist[position])

        holder.itemView.setOnClickListener {
            var reservationId = reservationlist[position].reservationID
            var storeId = reservationlist[position].storeId

            Log.i("어댑터 reservationID",reservationId.toString())
            Log.i("어댑터 storeId",storeId.toString())

            if (storeId != null && reservationId != null) {
                onClickListener.OnReservationClick(storeId,reservationId)
            }
        }
    }

    override fun getItemCount(): Int {
        // 리사이클러뷰 아이템 개수는 할 일 리스트의 크기
        return reservationlist.size
    }
}