package com.clone.karrot

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.clone.karrot.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private var mIsShowPass = false
    private var auth : FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //login btn is no empty -> change color
        val EmailEditText = findViewById<EditText>(R.id.login_id_et)
        val PwEditText = findViewById<EditText>(R.id.login_password_et)

        EmailEditText.addTextChangedListener (object : TextWatcher {
            // EditText에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (PwEditText.text.toString().isNotEmpty() && binding.loginIdEt.text.toString().isNotEmpty())
                    binding.loginSignInBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
                else
                    binding.loginSignInBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.dark_gray))
            }
            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) { }
        })

        PwEditText.addTextChangedListener (object : TextWatcher {
            // EditText에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (PwEditText.text.toString().isNotEmpty() && binding.loginIdEt.text.toString().isNotEmpty())
                    binding.loginSignInBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
                else
                    binding.loginSignInBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.dark_gray))
            }
            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) { }
        })

        //비밀번호 eye icon -> show/hide
        binding.showPasswordIv.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }

        auth = Firebase.auth
        //로그인 버튼 클릭
        binding.loginSignInBtn.setOnClickListener {
            val email = binding.loginIdEt.text.toString()
            val pwd = binding.loginPasswordEt.text.toString()
            signIn(email, pwd)
        }
    }

    //로그인 함수
    private fun signIn(email:String, pwd:String) {
        if (email.isNotEmpty() && pwd.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, pwd)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "로그인에 성공 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText(
                            baseContext, "로그인에 실패 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    // 유저정보 넘겨주고 메인 액티비티 호출
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    //키보드 내려가기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null && ev != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()

            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    //비밀번호 eye icon -> show/hide
    private fun showPassword(isShow: Boolean) {
        val show = binding.showPasswordIv
        val text = binding.loginPasswordEt

        if (isShow) {
            text.transformationMethod = HideReturnsTransformationMethod.getInstance()
            show.setImageResource(R.drawable.icn_hide_normal)
        }
        else {
            text.transformationMethod = PasswordTransformationMethod.getInstance()
            show.setImageResource(R.drawable.icn_show_normal)
        }
        //cursor가 맨 뒤로 가도록
        text.setSelection(text.text.toString().length)
    }
}