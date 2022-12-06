package com.example.hyundai_to_home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyundai_to_home.adapter.CalendarAdapter
import com.example.hyundai_to_home.databinding.ActivityReservationBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.StoreDao
import com.example.hyundai_to_home.db.StoreEntity
import com.example.hyundai_to_home.listner.OnDayListener
import com.example.hyundai_to_home.util.CalendarUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ReservationActivity : AppCompatActivity(), OnDayListener {

    lateinit var binding: ActivityReservationBinding

    private lateinit var db:AppDatabase
    private lateinit var storeDao: StoreDao
    private lateinit var store: StoreEntity

    // lateinit var calender: Calendar
    private val _store = MutableStateFlow<StoreEntity?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding 초기화
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        storeDao = db.StoreDao()

        //StoreListActivity 으로부터 넘긴 데이터를 받는다. - 승하
        getOneStore(intent.getIntExtra("store_id",0))
        println(intent.getIntExtra("store_id",0))

        // 화면 설정
        setMonthView()

        // 이전달 버튼 이벤트
        binding.btnPre.setOnClickListener {
            CalendarUtil.selectedDate.add(Calendar.MONTH,-1)
            setMonthView()
        }

        // 다음달 버튼 이벤트
        binding.btnNext.setOnClickListener {
            CalendarUtil.selectedDate.add(Calendar.MONTH,1)
            setMonthView()
        }
    }

    // 날짜 화면에 보여주기
    private fun setMonthView() {
        binding.monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)

        // 날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray()

        // 어댑터 초기화
        val adapter = CalendarAdapter(dayList,this)

        // 레이아웃 설정 (열 7개 )
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        // 레이아웃 적용
        binding.recyDate.layoutManager = manager

        // 어댑터 적용
        binding.recyDate.adapter = adapter
    }

    // 날짜 타입 설정
    private fun monthYearFromDate(calendar: Calendar): String {
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1

        return "$year.$month"
    }

    // 날짜 생성
    private fun dayInMonthArray(): ArrayList<Date> {
        var dayList = ArrayList<Date>()
        var monthCalendar = CalendarUtil.selectedDate.clone() as Calendar

        // 1일로 셋팅
        monthCalendar[Calendar.DAY_OF_MONTH] = 1

        // 해당 달의 1일의 요일
        val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK] -1

        // 요일 숫자만큼 이전 날짜로 설정
        // 예 : 6월 1일이 수요일이면 3만큼 이전날짜 셋팅
        monthCalendar.add(Calendar.DAY_OF_MONTH,-firstDayOfMonth)

        while(dayList.size < 35) {
            dayList.add(monthCalendar.time)

            // 1일씩 늘린다
            monthCalendar.add(Calendar.DAY_OF_MONTH,1)
        }
        return dayList
    }
/*
    private fun yearMonthFromDate(date : LocalDate) : String {
        var formatter = DateTimeFormatter.ofPattern("yyyy. MM월")

        return date.format(formatter)
    }

    override fun onDayClick(dayText: String) {
        var yearMonthDay = yearMonthFromDate(selectedDate) + " " + dayText + "일"
        Toast.makeText(this, yearMonthDay,Toast.LENGTH_SHORT).show()
    }*/

    override fun onDayClick(dayText: String) {
        // var yearMonthDay = monthYearFromDate(CalendarUtil.selectedDate)
        Toast.makeText(this, dayText, Toast.LENGTH_SHORT).show()
    }

    private fun getOneStore(storeNum:Int){
        Thread {
            store = storeDao.getStoreOne(storeNum)
            println(store)
            runOnUiThread {
                Glide.with(this)
                    .load(store.storeImage)
                    .into(binding.storeImage)
            }
            binding.storeName.text = store.storeName
            binding.storeContent.text = store.storeContent
        }.start()
    }

}