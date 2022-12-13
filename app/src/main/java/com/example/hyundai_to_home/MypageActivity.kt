package com.example.hyundai_to_home


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityMypageBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.Member
import com.example.hyundai_to_home.db.MemberDao
import com.google.firebase.auth.FirebaseAuth
/**
 * 클래스 설명 : 마이페이지 클래스
 *
 * @author  김민규
 * 김민규 - 회원정보 출력
 * 김민규 - 비밀번호 변경기능
 * 신기원 - 예약, 웨이팅 조회로 이동하는 버튼 구현
 * 김민규 - 로그아웃, 회원탈퇴기능
 */

class MypageActivity: AppCompatActivity() {
    private lateinit var db : AppDatabase
    private lateinit var  memberDao: MemberDao


    private lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        memberDao = db.MemberDao()



        Thread{ // 회원정보 조회
            val memberInfo : Member = memberDao.getMemberInfo(MyApplication.email.toString())
            runOnUiThread{
                binding.memberName.text = memberInfo.memberName
                binding.memberId.text = memberInfo.memberId
                binding.memberPhoneNumber.text = memberInfo.memberPhone
                binding.memberAddress.text = memberInfo.memberAddress
                binding.memberBirth.text = memberInfo.memberBirthdate
            }
        }.start()



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

        binding.logout.setOnClickListener{ // 로그아웃 기능
            MyApplication.auth.signOut()
            MyApplication.email = null
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.delete.setOnClickListener{ // 회원탈퇴 기능 --> Firebase와 Room DB 에서 동시에 삭제된다.
            val builder = AlertDialog.Builder(this)
            builder.setTitle("주의!")
            builder.setMessage("정말로 삭제하시겠습니까?")
            builder.setNegativeButton("Yes") { _: DialogInterface, _: Int ->
                MyApplication.auth.currentUser?.delete()
                deleteMember(MyApplication.email.toString())
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            builder.setPositiveButton("NO") { _: DialogInterface, _: Int ->

            }

            builder.show()

        }

        binding.btnPasswordChange.setOnClickListener{ // 비밀번호 변경기능
            val editTextNewPassword = EditText(this)
            editTextNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("패스워드 변경")
            alertDialog.setMessage("변경하고 싶은 패스워드를 입력하세요")
            alertDialog.setView(editTextNewPassword)
            alertDialog.setNegativeButton("변경") { _, _ ->
                changePassword(
                    editTextNewPassword.text.toString()
                )
            }
            alertDialog.setPositiveButton("취소") { dialogInterface, _ -> dialogInterface.dismiss() }
            alertDialog.show()
        }


        binding.preview.setOnClickListener{ 
            onBackPressed()
        }
        binding.preview2.setOnClickListener{
            onBackPressed()
        }


    }

    private fun deleteMember(memberID : String){ // ROOM DB에서 회원을 삭제하는 함수
        Thread{
            memberDao.deleteMember(memberID)
        }.start()

    }

    private fun changePassword(password:String) { // 비밀번호를 변경하는 함수
        FirebaseAuth.getInstance().currentUser!!.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show()
                    MyApplication.auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {// 비밀번호 제약조건 설정
                    Toast.makeText(this, "비밀번호는 6자리 이상으로 적어주세요", Toast.LENGTH_LONG).show()

                }
            }


    }
}