package com.example.hyundai_to_home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityWaitingBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.Waiting
import com.example.hyundai_to_home.db.WaitingDao
import com.example.hyundai_to_home.db.WaitingState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WaitingActivity: AppCompatActivity(){
    private lateinit var binding: ActivityWaitingBinding
    private lateinit var db : AppDatabase
    private lateinit var waitingDao :WaitingDao
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()

        //intent를 사용해 다음 액티비티로 넘어가는 리스너 구현
        binding.btnWaitingComplete.setOnClickListener {
            insertWaiting()
            val intent = Intent(this, WaitingCompleteActivity::class.java)
            startActivity(intent)
        }
    }
    //웨이팅 데이터 추가시 db에 저장
    private fun insertWaiting() {
        var intent : Intent = getIntent()
        val memberId = binding.memberName.text.toString()
        val memberPhone =binding.memberPhone.text.toString()
        val storeId = intent.getIntExtra("STORE_ID", 0).toString()
        val waitingCount = binding.num.text.toString()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateAndtime: String = LocalDateTime.now().format(formatter)
        //val onlyDate: LocalDate = LocalDate.now()

        val waitingNum = binding.num.text.toString()

//        val waitingState = WaitingState.예약완료

        //개인정보에 대한 체크 박스의 확인
        if(binding.check1.isChecked && binding.check2.isChecked){
            Thread {
                waitingDao.insertWaiting(Waiting(null, memberId, storeId, dateAndtime, waitingNum, "예약완료"))
                runOnUiThread{
                    Toast.makeText(this, "웨이팅예약이 확정되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }.start()
            val wait = Waiting(null, memberId, storeId, dateAndtime, waitingNum, "예약완료")
            Log.d("waiting", wait.toString())
        } else {
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        }
    }

}