package com.example.hyundai_to_home

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
/**
 * 클래스 설명 : Firebase 연결 클래스
 *
 * @author  김민규
 * 김민규 - MultiDexApplication()를 이용한 Firebase 연결
 */
class MyApplication : MultiDexApplication() {
    companion object{
        lateinit var auth : FirebaseAuth
        var email : String? = null
        fun checkAuth() : Boolean {
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified
            } ?: let{
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
    }
}