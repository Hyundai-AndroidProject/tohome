package com.example.hyundai_to_home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hyundai_to_home.adapter.CalendarAdapter
import com.example.hyundai_to_home.databinding.ActivityReservationBinding
import com.example.hyundai_to_home.db.*
import com.example.hyundai_to_home.listner.OnDayListener
import com.example.hyundai_to_home.util.CalendarUtil
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ReservationActivity : AppCompatActivity(), OnDayListener {

    lateinit var binding: ActivityReservationBinding

    private lateinit var db: AppDatabase
    private lateinit var storeDao: StoreDao
    private lateinit var store: Store

    lateinit var reservationFixedDate: String
    lateinit var reservationFixedTime: String
    var pnum: Int = 1


    private lateinit var reservationDao: ReservationDao
    private lateinit var reservation: Reservation

    // lateinit var calender: Calendar
    private val _store = MutableStateFlow<Store?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding 초기화
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        storeDao = db.StoreDao()
        reservationDao = db.ReservationDao()

        //StoreListActivity 으로부터 넘긴 데이터를 받는다. - 승하
        getOneStore(intent.getIntExtra("store_id", 0))
        println(intent.getIntExtra("store_id", 0))

        // 화면 설정
        setMonthView()

        // 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            finish()
        }

        // 이전달 버튼 이벤트
        binding.btnPre.setOnClickListener {
            CalendarUtil.selectedDate.add(Calendar.MONTH, -1)
            setMonthView()
        }

        // 다음달 버튼 이벤트
        binding.btnNext.setOnClickListener {
            CalendarUtil.selectedDate.add(Calendar.MONTH, 1)
            setMonthView()
        }

        // 인원수 변경 버튼
        binding.icMinus.setOnClickListener {
            minus()
        }

        binding.icPlus.setOnClickListener {
            plus()
        }


        var count = 0
        var beforeselect = 0
        var times =
            arrayOf(binding.linear1, binding.linear2, binding.linear3, binding.linear4, binding.linear5,binding.linear6)

        binding.linear1.setOnClickListener {
            // binding.view1.setTextColor(Color.BLACK)
            times[beforeselect].setBackgroundColor(Color.parseColor("#DFDDDC"))
            binding.linear1.setBackgroundColor(Color.parseColor("#BC8F8F"))
            beforeselect = 0
            reservationFixedTime = "10:30"
        }
        binding.linear2.setOnClickListener {
            times[beforeselect].setBackgroundColor(Color.parseColor("#DFDDDC"))
            binding.linear2.setBackgroundColor(Color.parseColor("#BC8F8F"))
            beforeselect = 1
            reservationFixedTime = "11:00"
        }
        binding.linear3.setOnClickListener {
            times[beforeselect].setBackgroundColor(Color.parseColor("#DFDDDC"))
            binding.linear3.setBackgroundColor(Color.parseColor("#BC8F8F"))
            beforeselect = 2
            reservationFixedTime = "11:30"

        }
        binding.linear4.setOnClickListener {
            times[beforeselect].setBackgroundColor(Color.parseColor("#DFDDDC"))
            binding.linear4.setBackgroundColor(Color.parseColor("#BC8F8F"))
            beforeselect = 3
            reservationFixedTime = "17:00"

        }

        binding.linear5.setOnClickListener {
            times[beforeselect].setBackgroundColor(Color.parseColor("#DFDDDC"))
            binding.linear5.setBackgroundColor(Color.parseColor("#BC8F8F"))
            beforeselect = 4
            reservationFixedTime = "17:30"

        }
        binding.linear6.setOnClickListener {
            times[beforeselect].setBackgroundColor(Color.parseColor("#DFDDDC"))
            binding.linear6.setBackgroundColor(Color.parseColor("#BC8F8F"))
            beforeselect = 5
            reservationFixedTime = "18:00"

        }

        binding.btnConfirm.setOnClickListener {

            if (binding.check1.isChecked && binding.check2.isChecked) {
                insertReservation()
                runOnUiThread {
                    Toast.makeText(this, "예약이 확정되었습니다.", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, ReservationCompleteActivity::class.java)
                intent.putExtra("memberId", binding.memberName.text.toString())
                intent.putExtra("storeId", getIntent().getIntExtra("store_id", 0))
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun minus() {
        var pnum = Integer.parseInt(binding.num.text.toString())
        if (pnum - 1 > 0) {
            var tmp = pnum - 1
            binding.num.text = tmp.toString()
        }
    }

    private fun plus() {
        var pnum = Integer.parseInt(binding.num.text.toString())
        var tmp = pnum + 1
        binding.num.text = tmp.toString()
    }

    // 날짜 화면에 보여주기
    private fun setMonthView() {
        binding.monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)

        // 날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray()

        // 어댑터 초기화
        val adapter = CalendarAdapter(dayList, this)

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
        val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK] - 1

        // 요일 숫자만큼 이전 날짜로 설정
        // 예 : 6월 1일이 수요일이면 3만큼 이전날짜 셋팅
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dayList.size < 35) {
            dayList.add(monthCalendar.time)

            // 1일씩 늘린다
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
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
        reservationFixedDate = dayText

        if (binding.chooseDate.visibility == View.VISIBLE) {
            binding.chooseDate.visibility = View.GONE
        } else {
            binding.chooseDate.visibility = View.VISIBLE

        }

        var times = arrayOf(binding.view1, binding.view2, binding.view3, binding.view4, binding.view5, binding.view6)
        var timesText = arrayOf(binding.view1Text, binding.view2Text, binding.view3Text, binding.view4Text, binding.view5Text, binding.view6Text)
        // 시간 설정
        val storeId = intent.getIntExtra("store_id", 0)
        for (a in 0..times.size - 1) {
            var num: Int = 0

            var newText = times[a].text.toString()
            Thread {
                num = reservationDao.countReservation(
                    storeId,
                    times[a].text.toString(),
                    "예약 확정",
                    reservationFixedDate
                )
                Log.i("------------------시간 인원-------------", num.toString())
                Log.i("------------------시간------------------", times[a].text.toString())
                Log.i("가게id", storeId.toString())
                Log.i("-----------------선택 날짜--------------", reservationFixedDate)

                runOnUiThread {
                    if (num > 1) {
                        timesText[a].text = "예약 마감"
                        times[a].text = newText
                    } else {
                        timesText[a].text = "(" + num.toString()+"명 예약 중)"
                    }
                }

            }.start()
        }
    }

    private fun getOneStore(storeNum: Int) {
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


    // 예약 데이터 추가시 db에 저장
    private fun insertReservation() {
        var intent: Intent = intent
        val memberId = binding.memberName.text.toString()
        val storeId = intent.getIntExtra("store_id", 0)
        val reservationHeadCount = binding.num.text.toString()
        val requestContent = binding.requestContent.text.toString()

        // 오늘 날짜
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val reservationRegisterDate: String = LocalDateTime.now().format(formatter)

        // 예약한 날짜
        val reservationFixedDate = reservationFixedDate.toString()
        val reservationFixedTime = reservationFixedTime.toString()
        val reservationSate = "예약 확정" // 1: 예약 확정 / 2: 예약 취소 / 3: 입장 완료

        val reservation = Reservation(
            null,
            memberId,
            storeId,
            reservationHeadCount,
            requestContent,
            reservationRegisterDate,
            reservationFixedDate,
            reservationFixedTime,
            reservationSate
        )

        Thread {
            reservationDao.insertReservationDB(
                reservation
            )
        }.start()

        Log.i("예약 데이터", reservation.toString())
    }

    // 시간을 입력 받아서 text 변경
/*    private fun setTime(times : Array<Binding>) {
        val storeId = intent.getIntExtra("store_id", 0)


        for (a in 0..times.size) {
            var num: Int = 0
            Thread {
                num = reservationDao.countReservation(storeId, times[a].text.toString())
            }.start()
            if (num > 1) {
                times[a].setText("예약 마감")
            }
        }
    }*/
}