package com.example.hyundai_to_home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityLoginBinding
/**
 * 클래스 설명 : 로그인 페이지
 *
 * @author  김민규
 * 김민규 - Firebase 를 이용한 로그인 기능 구현
 * 김민규 - 회원가입, 아이디찾기, 상단 뒤로가기 이미지 클릭시 이동
 * 김민규 - 아이디와 비밀번호 입력창에 아무것도 입력하지 않았을 때의 예외처리
 */
@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityLoginBinding
private var backKeyPressedTime : Long = 0
class LoginActivity :  AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val terms = binding.terms
        val preview = binding.preview

        terms.setOnClickListener { // 회원가입을 눌렀을 때, 이용약관으로 이동한다.
            val intent = Intent(this,TermsActivity::class.java)
            startActivity(intent)
        }

        preview.setOnClickListener{// 상단 뒤로가기 이미지를 눌렀을 때 메인액티비티로 이동한다.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnFindID.setOnClickListener{// 아이디 찾기로 이동한다.
            val intent = Intent(this,FindActivity::class.java )
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.LoginText.text.toString() // 아이디
            val password = binding.editTextTextPassword.text.toString() // 비밀번호
            if(email.isEmpty()){ //아이디 입력칸에 아무것도 입력되지 않았을 때 알림창을 띄운다.
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(password.isEmpty()){//비밀번호 입력칸에 아무것도 입력되지 않았을 때 알림창을 띄운다.
                Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else{
                MyApplication.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task ->
                        binding.LoginText.text.clear()
                        binding.editTextTextPassword.text.clear()
                        if(task.isSuccessful){
                            if(MyApplication.checkAuth()){//로그인이 성공한 경우
                                print("로그인성공")
                                MyApplication.email = email
                                val intent = Intent(this, ServiceActivity::class.java)
                                startActivity(intent)

                            }else {// 회원가입 이후 수신된 이메일에 링크를 눌러 인증을 하지않고 로그인을 한 경우
                                print("인증안됨")
                                Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()

                            }
                        }else {//아이디와 비밀번호가 일치하지 않은 경우
                            print("로그인실패")
                            Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
    override fun onBackPressed() { // 뒤로가기 버튼을 두번 누르면 앱이 종료되는 기능
        if(System.currentTimeMillis() > backKeyPressedTime + 2500){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다",Toast.LENGTH_SHORT).show()
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime +2500){
            finishAffinity()
        }
    }

}

