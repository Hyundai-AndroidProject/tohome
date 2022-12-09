package com.example.hyundai_to_home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hyundai_to_home.databinding.ActivityWaitingBinding
import com.example.hyundai_to_home.db.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
/**
 * 클래스 설명 : 웨이팅 예약을 진행하는 클래스
 *
 * @author  신기원
 * 신기원 - 버튼 클릭시 웨이팅 예약을 하기 위해 DB에 입력 데이터 저장
 */
class WaitingActivity: AppCompatActivity(){
    private lateinit var binding: ActivityWaitingBinding

    private lateinit var db : AppDatabase
    private lateinit var storeDao: StoreDao
    private lateinit var store: Store


    private lateinit var waitingDao :WaitingDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        waitingDao = db.waitingDao()
        storeDao = db.StoreDao()

        //StoreListActivity 으로부터 넘긴 데이터를 받는다. - 승하
        getOneStore(intent.getIntExtra("store_id",0))
//        println(intent.getIntExtra("store_id",0))

        binding.icMinus.setOnClickListener {
            minus()
        }

        binding.icPlus.setOnClickListener {
            plus()
        }

        //intent를 사용해 다음 액티비티로 넘어가는 리스너 구현

        binding.btnWaitingComplete.setOnClickListener {
            //개인정보에 대한 체크 박스의 확인
            if(binding.check1.isChecked && binding.check2.isChecked){
                val memberId = MyApplication.email!!
                val storeId = getIntent().getIntExtra("store_id", 0)
                Thread{
                    //해당 식당에 웨이팅 예약 내역이 있으면 웨이팅 불가
                    if(waitingDao.findWaitingById(memberId, storeId )!= null){
                        runOnUiThread{
                            Toast.makeText(this, "이미 해당 식당에 웨이팅하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }else{
                        insertWaiting()
                        runOnUiThread{
                            Toast.makeText(this, "웨이팅예약이 확정되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(this, WaitingCompleteActivity::class.java)
                        intent.putExtra("memberId",memberId)
                        intent.putExtra("storeId", storeId)
                        startActivity(intent)
                    }
                }.start()

            } else {
                Toast.makeText(this, "정보 제공 동의를 체크해주세요.", Toast.LENGTH_SHORT).show()
            }

        }
    }
    //웨이팅 데이터 추가시 db에 저장
    private fun insertWaiting() {
        var intent : Intent = getIntent()

        //Firebase에 저장된 회원ID 값
        val memberId = MyApplication.email!!
        val memberName = binding.memberName.text.toString();
        val memberPhone =binding.memberPhone.text.toString()
        val storeId = intent.getIntExtra("store_id", 0)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateAndtime: String = LocalDateTime.now().format(formatter)
        //val onlyDate: LocalDate = LocalDate.now()

        val waitingNum = binding.num.text.toString()

//        val waitingState = WaitingState.예약완료


        Thread {
            waitingDao.insertWaiting(Waiting(null, memberId, memberName, memberPhone, storeId, dateAndtime, waitingNum, "예약완료"))

        }.start()

    }

    private fun getOneStore(storeNum:Int){
        Thread {
            store = storeDao.getStoreOne(storeNum)
            //println(store)
            runOnUiThread {
                Glide.with(this)
                    .load(store.storeImage)
                    .into(binding.storeImage)
            }
            binding.storeName.text = store.storeName
            binding.storeContent.text = store.storeContent
        }.start()
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

}