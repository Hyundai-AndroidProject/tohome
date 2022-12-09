package com.example.hyundai_to_home


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityMypageBinding
import com.google.firebase.auth.FirebaseAuth


class MypageActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            finish()
        }

        //intent를 사용해 예약리스트 액티비티로 넘어가는 리스너 구현
        binding.btnReservationList.setOnClickListener {
            val intent = Intent(this, ReservationListActivity::class.java)
            startActivity(intent)
        }

        //intent를 사용해 웨이팅예약리스트 액티비티로 넘어가는 리스너 구현
        binding.btnWaitingList.setOnClickListener {
            val intent = Intent(this, WaitingListActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener{
            MyApplication.auth.signOut()
            finish()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.delete.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("주의!")
            builder.setMessage("진짜 삭제할거야? 진짜?")
            builder.setNegativeButton("응") { dialogInterface: DialogInterface, i: Int ->
                MyApplication.auth.currentUser?.delete()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            builder.setPositiveButton("아니") { dialogInterface: DialogInterface, i: Int ->
                
            }

            builder.show()

        }


        println(MyApplication.email)
    }

}