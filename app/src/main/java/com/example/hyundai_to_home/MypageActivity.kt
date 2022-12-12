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



        Thread{
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

        binding.logout.setOnClickListener{
            MyApplication.auth.signOut()
            MyApplication.email = null
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.delete.setOnClickListener{
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

        binding.btnPasswordChange.setOnClickListener{
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
    private fun deleteMember(memberID : String){
        Thread{
            memberDao.deleteMember(memberID)
        }.start()

    }
    private fun changePassword(password:String) {
        FirebaseAuth.getInstance().currentUser!!.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show()
                    MyApplication.auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "비밀번호는 6자리 이상으로 적어주세요", Toast.LENGTH_LONG).show()

                }
            }


    }
}