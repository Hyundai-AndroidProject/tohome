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

        binding.btnDuplicate.setOnClickListener {
            if(binding.EmailEditView.text.toString().equals("")){
                binding.checkIDText.text = "아이디를 입력해주세요."
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


        binding.options.setOnClickListener{
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
        binding.MemberGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioFemale -> mGender = "여성"
                R.id.radioMale -> mGender = "남성"
            }
        }
        binding.signBtn.setOnClickListener {
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
            if(mName.isEmpty()){
                Toast.makeText(this,"이름을 입력해주세요",Toast.LENGTH_SHORT).show()
                println("이름을 입력해주세요")
            }
            else if(email.isEmpty()){
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show()
                println("아이디를 입력해주세요")
            }
            else if(binding.checkIDText.text.equals("중복된 아이디 입니다.")){
                Toast.makeText(this,"아이디 중복을 확인해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(password.isEmpty()){
                Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(password.length<6){
                Toast.makeText(this,"비밀번호 조건을 확인해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mPNumber.isEmpty()){
                Toast.makeText(this,"전화번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mAddress.isEmpty()){
                Toast.makeText(this,"주소를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mBirth.isEmpty()){
                Toast.makeText(this,"생년월일을 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(mBirth.length != 8){
                Toast.makeText(this,"생년월일은 8자리로 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
            else if(!binding.radioFemale.isChecked && !binding.radioMale.isChecked){
                Toast.makeText(this,"성별을 선택해주세요",Toast.LENGTH_SHORT).show()
            }
            else{
                MyApplication.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.EmailEditView.text.clear()
                        binding.PasswordEditView.text.clear()
                        if (task.isSuccessful) {
                            MyApplication.auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    if (sendTask.isSuccessful) {
                                        Toast.makeText(
                                            baseContext, "회원가입에서 성공, 전송된 메일을 확인해 주세요",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this, LoginActivity::class.java)
                                        startActivity(intent)

                                        Thread {
                                            memberDao.insertMember(member)
                                        }.start()
                                    } else {
                                        Toast.makeText(
                                            baseContext,
                                            "메일 발송 실패",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                    }
                                }
                        } else {
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



