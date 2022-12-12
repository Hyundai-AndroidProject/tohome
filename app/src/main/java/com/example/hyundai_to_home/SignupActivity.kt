package com.example.hyundai_to_home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivitySignupBinding
import com.example.hyundai_to_home.db.AppDatabase
import com.example.hyundai_to_home.db.Member
import com.example.hyundai_to_home.db.MemberDao
/**
 * 클래스 설명 : 회원가입 클래스
 *
 * @author  김민규
 * 김민규 - Firebase를 이용한 회원가입기능
 * 김민규 - 아이디 중복체크 기능
 * 김민규 - visibility를 이용하여 추가정보 기입란 생성
 * 김민규 - 값이 들어있지 않는 입력칸을 알려주는 알림창 생성, 제약조건 생성
 */

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var memberDao: MemberDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val goBackTerms = binding.goBackTerms
        var mGender: String? = null

        db = AppDatabase.getInstance(this)!!
        memberDao = db.MemberDao()

        binding.btnDuplicate.setOnClickListener { //아이디 중복체크기능
            if(binding.EmailEditView.text.isEmpty()){
                binding.checkIDText.text = "아이디를 입력해주세요."
                binding.checkIDText.setTextColor(Color.parseColor("#000000"))
            }
            else{
                Thread {
                    val checkId: Int = memberDao.duplicateID(binding.EmailEditView.text.toString())
                    runOnUiThread {
                        println("checkid: $checkId")
                        if (checkId  > 0) {
                            binding.checkIDText.text = "중복된 아이디 입니다."
                            binding.checkIDText.setTextColor(Color.parseColor("#FF0000"))
                        }
                        else{
                            binding.checkIDText.text = "사용가능한 아이디 입니다."
                            binding.checkIDText.setTextColor(Color.parseColor("#0000FF"))
                        }
                    }
                }.start()
            }



        }

        binding.preview.setOnClickListener{
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }


        binding.options.setOnClickListener{ // 숨겨진 입력칸 보여주고 숨기는 기능
            if(binding.AddressText.visibility==View.GONE &&
                binding.memberAddress.visibility==View.GONE &&
                binding.BirthAndGender.visibility==View.GONE &&
                binding.BirthText.visibility==View.GONE){
                binding.AddressText.visibility=View.VISIBLE
                binding.memberAddress.visibility=View.VISIBLE
                binding.BirthAndGender.visibility=View.VISIBLE
                binding.BirthText.visibility=View.VISIBLE
                binding.options.animate().apply {
                    duration=300
                    rotation(180f)
                }
            }
            else{
                binding.AddressText.visibility=View.GONE
                binding.memberAddress.visibility=View.GONE
                binding.BirthAndGender.visibility=View.GONE
                binding.BirthText.visibility=View.GONE
                binding.options.animate().apply {
                    duration=300
                    rotation(0f)
                }
            }
        }
        binding.MemberGender.setOnCheckedChangeListener { group, checkedId -> // 선택한 라디오 버튼에 있는 값을 받아온다.
            when (checkedId) {
                R.id.radioFemale -> mGender = "여성"
                R.id.radioMale -> mGender = "남성"
            }
        }
        binding.signBtn.setOnClickListener { // 회원가입 버튼을 눌렀을 때
            val email = binding.EmailEditView.text.toString()
            val password = binding.PasswordEditView.text.toString()
            val mName = binding.memberName.text.toString()
            val mPNumber = binding.memberPhone.text.toString()
            val mAddress = binding.memberAddress.text.toString()
            val mBirth = binding.memberBirth.text.toString()
            val mGender = mGender.toString()

            val member = Member(
                email,
                mName,
                mPNumber,
                mBirth,
                mAddress,
                mGender
            )
            println("mName.isBlank(): " + mName.isBlank())
            if(mName.isEmpty()){// 이름을 입력하지 않았을 때
                Toast.makeText(this,"이름을 입력해주세요",Toast.LENGTH_SHORT).show()
                println("이름을 입력해주세요")
            }
            else if(email.isEmpty()){// 아이디를 입력하지 않았을 때 
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show()
                println("아이디를 입력해주세요")
            }
            else if(binding.checkIDText.text.equals("중복된 아이디 입니다.")){// 아이디 중복검사를 통과하지 못했을 때
                Toast.makeText(this,"아이디 중복을 확인해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(password.isEmpty()){// 비밀번호를 입력하지 않았을 때
                Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(password.length<6){ // 비밀번호 조건 : 6자리 이상
                Toast.makeText(this,"비밀번호 조건을 확인해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mPNumber.isEmpty()){// 전화번호를 입력하지 않았을 때
                Toast.makeText(this,"전화번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mAddress.isEmpty()){// 주소를 입력하지 않았을 때
                Toast.makeText(this,"주소를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mBirth.isEmpty()){// 생년월일을 입력하지 않았을 때
                Toast.makeText(this,"생년월일을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mBirth.length != 8){// 생년월일을 형식에 맞지 않게 입력했을 때
                Toast.makeText(this,"생년월일은 8자리로 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
            else if(!binding.radioFemale.isChecked && !binding.radioMale.isChecked){ // 성별을 선택하지 않았을 때
                Toast.makeText(this,"성별을 선택해주세요",Toast.LENGTH_SHORT).show()
            }
            else{// 모든 입력칸에 조건에 맞는 값이 들어갔을 때 
                MyApplication.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.EmailEditView.text.clear()
                        binding.PasswordEditView.text.clear()
                        if (task.isSuccessful) {
                            MyApplication.auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    if (sendTask.isSuccessful) {// 입력한 아이디(이메일)에 회원인증 이메일을 보낸다. 
                                        Toast.makeText(
                                            baseContext, "회원가입에서 성공, 전송된 메일을 확인해 주세요",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this, LoginActivity::class.java)
                                        startActivity(intent)

                                        Thread {// Room DB에 값이 정확히 들어간다.
                                            memberDao.insertMember(member)
                                        }.start()
                                    } else {// 메일 발송에 실패했을 경우
                                        Toast.makeText(
                                            baseContext,
                                            "메일 발송 실패",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                    }
                                }
                        } else {// 회원가입에 실패했을 경우
                            Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }


        }
        goBackTerms.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }
}



