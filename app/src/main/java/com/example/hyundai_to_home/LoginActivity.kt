package com.example.hyundai_to_home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hyundai_to_home.databinding.ActivityLoginBinding



@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityLoginBinding
class LoginActivity :  AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val terms = binding.terms
        val preview = binding.preview

        terms.setOnClickListener {
            val intent = Intent(this,TermsActivity::class.java)
            startActivity(intent)
        }
        preview.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email = binding.LoginText.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    binding.LoginText.text.clear()
                    binding.editTextTextPassword.text.clear()
                    if(task.isSuccessful){
                        if(MyApplication.checkAuth()){
                            print("로그인성공")
                            MyApplication.email = email
                            val intent = Intent(this, ServiceActivity::class.java)
                            startActivity(intent)
                            //changeVisibility("login")
                        }else {
                            print("인증안됨")
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()

                        }
                    }else {
                        print("로그인실패")
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    }

    /* fun changeVisibility(mode: String) {
         if (mode === "login") {
             binding2.run {
                 mainText.text = "${MyApplication.email} 님 반갑습니다."
                 btnLogin.visibility = View.GONE
                 //logoutBtn.visibility= View.VISIBLE
                 //goSignInBtn.visibility= View.GONE
                 //googleLoginBtn.visibility= View.GONE
                 //authEmailEditView.visibility= View.GONE
                 //authPasswordEditView.visibility= View.GONE
                 //signBtn.visibility= View.GONE
                 //loginBtn.visibility= View.GONE
             }

         }else if(mode === "logout"){
             binding.run {
                 //authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                 //logoutBtn.visibility = View.GONE
                 //goSignInBtn.visibility = View.VISIBLE
                 //googleLoginBtn.visibility = View.VISIBLE
                 //authEmailEditView.visibility = View.VISIBLE
                 //authPasswordEditView.visibility = View.VISIBLE
                 //signBtn.visibility = View.GONE
                 //loginBtn.visibility = View.VISIBLE
             }
         }else if(mode === "signin"){
             binding.run {
                 logoutBtn.visibility = View.GONE
                 goSignInBtn.visibility = View.GONE
                 googleLoginBtn.visibility = View.GONE
                 authEmailEditView.visibility = View.VISIBLE
                 authPasswordEditView.visibility = View.VISIBLE
                 signBtn.visibility = View.VISIBLE
                 loginBtn.visibility = View.GONE
             }
         }*/

