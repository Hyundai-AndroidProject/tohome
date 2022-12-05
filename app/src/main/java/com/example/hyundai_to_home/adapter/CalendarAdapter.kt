package com.example.hyundai_to_home.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hyundai_to_home.R
import com.example.hyundai_to_home.util.CalendarUtil
import java.util.Calendar
import java.util.Date

class CalendarAdapter (private val dayList : ArrayList<Date>)
    : RecyclerView.Adapter <CalendarAdapter.ItemViewHolder>(){

        class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val dayText : TextView = itemView.findViewById(R.id.dayText)
        }

    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // 캘린더 각 날짜 뷰 바인딩 객체 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell,parent,false)

        return ItemViewHolder(view)
    }

    // 데이터 설정
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // 날짜 변수에 담기
        var monthDate = dayList[holder.adapterPosition]

        // 초기화
        var dateCalendar = Calendar.getInstance()

        // 날짜 캘린더에 담기
        dateCalendar.time = monthDate

        // 캘린더값 날짜 변수에 담기
        var dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)

        holder.dayText.text = dayNo.toString()

        // 넘어온 날짜
        var iYear = dateCalendar.get(Calendar.YEAR)
        var iMonth = dateCalendar.get(Calendar.MONTH) + 1
        var iDay = dateCalendar.get(Calendar.DAY_OF_MONTH)
        Log.i("1",iYear.toString())
        Log.i("2",iMonth.toString())
        Log.i("3",iDay.toString())

        // 현재 날짜
        var selectYear = CalendarUtil.selectedDate.get(Calendar.YEAR)
        var selectMonth = CalendarUtil.selectedDate.get(Calendar.MONTH) + 1
        var selectDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH)

        if (iYear == selectYear && iMonth == selectMonth) {
            holder.dayText.setTextColor(Color.BLACK)

            // 현재 날짜 비교해서 같다면 배경 색상 변경
            if (selectDay == dayNo) {
                holder.itemView.setBackgroundColor(Color.DKGRAY)
            }
            // 텍스트 색상 지정 (토,일)
            if((position + 1) % 7 == 0) {
                holder.dayText.setTextColor(Color.BLUE)
            } else if (position == 0 || position % 7 == 0) {
                holder.dayText.setTextColor(Color.RED)
            }
        } else {
            holder.dayText.setTextColor(Color.parseColor("#B4B4B4"))

            // 텍스트 색상 지정 (토,일)
            if((position + 1) % 7 == 0) {
                holder.dayText.setTextColor(Color.parseColor("#BEEFFF"))
            } else if (position == 0 || position % 7 == 0) {
                holder.dayText.setTextColor(Color.parseColor("#FFB4B4"))
            }
        }


        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener {
            // 인터페이스를 통해 날짜를 넘겨준다
            var yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"
            Toast.makeText(holder.itemView.context, yearMonDay,Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}