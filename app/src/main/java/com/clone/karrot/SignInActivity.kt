package com.clone.karrot

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.clone.karrot.databinding.ActivitySignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    //전역변수로 설정
    private lateinit var auth: FirebaseAuth

    lateinit var editText: EditText
    lateinit var _editText: EditText
    lateinit var pwlength: TextView

    private var mSignShowPass = false
    private var mSignShow2Pass = false

    private var agreeChecked = false
    private var agreeChecked2 = false
    //private var agreeChecked3 = false
    private var agreeChecked4 = false

    private var nameCheck = false
    private var emailCheck = false
    private var PWCheck = false
    private var PWConfirmCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        editText = findViewById<EditText>(R.id.singup_password_input_et)
        _editText = findViewById<EditText>(R.id.singup_password_confirm_et)
        pwlength = findViewById<TextView>(R.id.signup_pw_vali_length_tv)

        val asso = findViewById<TextView>(R.id.signup_pw_vali_asso_tv)
        val allCheck = findViewById<ImageButton>(R.id.signup_consent_all_check)

        //password show -> hide
        binding.signupPasswordShowIv.setOnClickListener {
            mSignShowPass = !mSignShowPass
            showPassword(mSignShowPass, binding.signupPasswordShowIv, editText)
        }
        //password confirm show -> hide
        binding.signupPasswordConfirmShowIv.setOnClickListener {
            mSignShow2Pass = !mSignShow2Pass
            showPassword(mSignShow2Pass, binding.signupPasswordConfirmShowIv, binding.singupPasswordConfirmEt)
        }
        //password cross (x) -> erase
        binding.signupPasswordEraseIv.setOnClickListener {
            editText.text.clear()
        }
        //password confirm cross (x) -> erase
        binding.signupPasswordConfirmEraseIv.setOnClickListener {
            binding.singupPasswordConfirmEt.text.clear()
        }


        val nameEditText = findViewById<EditText>(R.id.singup_name_input_et)
        val emailEditText = findViewById<EditText>(R.id.singup_email_input_et)

        //checkname
        nameEditText.addTextChangedListener(object : TextWatcher {
            // EditText에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (checkName(nameEditText.text.toString())) {
                    Log.d("name-check", "196 이름 체크")
                    nameCheck = true
                } else {
                    Log.d("NO - name-check", "199 이름 false 체크")
                    nameCheck = false
                }
            }

            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) { }
        })

        //checkemail
        emailEditText.addTextChangedListener(object : TextWatcher {
            // EditText에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) {
                if (checkEmailAddress(emailEditText.text.toString())) {
                    Log.d("email-check", "218 이메일 체크")
                    emailCheck = true
                } else {
                    Log.d("NO - email-check", "221 이메일 false 체크")
                    emailCheck = false
                }
            }
        })

        //밑줄
        underlineSee(binding.signupInfoSeeTv)
        underlineSee(binding.signupInfoFactTv)

        //전체동의, 이용약관
        allCheck.setOnClickListener {
            agreeChecked = !agreeChecked
            onChangedClick(agreeChecked, allCheck)
            onCheckClick(agreeChecked, allCheck, binding.signupConsentAllTv)
            if (nameCheck && emailCheck && PWCheck && agreeChecked) {
                Log.d("true-check", "전체 체크 237")
                binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
            } else {
                Log.d("false-check", "전체 fasle 240")
                binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
            }
        }
        //개인정보 수집 및 이용 동의
        binding.signupConsentInfoCheck.setOnClickListener {
            agreeChecked2 = !agreeChecked2
            onCheckClick(agreeChecked2, binding.signupConsentInfoCheck, binding.signupConsentInfoTv)
            if (agreeChecked2 && agreeChecked4) {
                Log.d("true-check", "249-두개가 체크되었을 때")
                onCheckClick(true, allCheck, binding.signupConsentAllTv)
                //binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.dark_red))
            }
            if (!agreeChecked2 || !agreeChecked4) {
                onCheckClick(false, allCheck, binding.signupConsentAllTv)
            }
        }
        //허위사실
        binding.signupConsentFactCheck.setOnClickListener {
            agreeChecked4 = !agreeChecked4
            onCheckClick(agreeChecked4, binding.signupConsentFactCheck, binding.signupConsentFactTv)
            if (agreeChecked2 && agreeChecked4) {
                Log.d("true-check", "274-두개가 체크되었을 때")
                onCheckClick(true, allCheck, binding.signupConsentAllTv)
                binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
            }
            if (!agreeChecked2 || !agreeChecked4) {
                onCheckClick(false, allCheck, binding.signupConsentAllTv)
            }
        }


        //sign up
        binding.signupCompleteBtn.setOnClickListener {
            signUp()
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

    //sign up
    private fun signUp() {
        val name = binding.singupNameInputEt.text.toString()
        val email = binding.singupEmailInputEt.text.toString()
        val pwd = binding.singupPasswordInputEt.text.toString()

        auth.createUserWithEmailAndPassword(email,pwd)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this,"회원가입에 성공했습니다!",Toast.LENGTH_SHORT).show()
                } else if(task.exception?.message.isNullOrEmpty()) {
                    // Show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(this,"이미 존재하는 계정이거나, 회원가입에 실패했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
    }


    //비밀번호 show -> hide
    private fun showPassword(isShow: Boolean, show: ImageView, text: EditText) {

        if(isShow) {
            text.transformationMethod = HideReturnsTransformationMethod.getInstance()
            show.setImageResource(R.drawable.icn_hide_normal)
        } else {
            text.transformationMethod = PasswordTransformationMethod.getInstance()
            show.setImageResource(R.drawable.icn_show_normal)
        }
        //cursor 이동
        text.setSelection(text.text.toString().length)
    }

    //밑줄
    private fun underlineSee(view: TextView) {
        val under = SpannableString("보기")
        under.setSpan(UnderlineSpan(), 0, under.length, 0)
        view.text = under
    }

    //dp값으로 변경_비밀번호 유효성 검사에서 margin값을 변경하기 위함
    private fun changeDP(value: Int) : Int {
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }

    //이름 check -> 2글자 이상만 쓰면 OK
    fun checkName(text: String) : Boolean {
        return text.matches("^([^\\s]){2,}\$".toRegex())
    }

    //이메일 check
    fun checkEmailAddress(text: String) : Boolean {
        //return text.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}\$".toRegex())
        return Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    //===Terms of Service===
    //전체동의, 이용약관
    private fun onCheckClick(isAgree: Boolean, show: ImageButton, text: TextView) {

        if (isAgree) {
            text.setTextColor(ContextCompat.getColor(applicationContext, R.color.carrot))
            show.setImageResource(R.drawable.icn_check_02_on)
            if (nameCheck && emailCheck && PWCheck && agreeChecked) {
                Log.d("true-check", "전체 체크400")
                binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
            }
        } else {
            text.setTextColor(ContextCompat.getColor(applicationContext, R.color.dark_gray))
            show.setImageResource(R.drawable.icn_check_02_off)
            binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
        }
    }

    //전체동의를 누르면 다 눌리도록_전체동의만 할 수 있도록.
    //따로 함수를 만든 이유 : 나머지 2개를 다 눌렀을 때도 전체동의가 true가 되게 하기 위해서
    private fun onChangedClick(isAgree: Boolean, show: ImageButton) {

        val cBox1 = findViewById<ImageButton>(R.id.signup_consent_info_check)
        /*val cBox2 = findViewById<ImageButton>(R.id.signup_consent_secret_check)*/
        val cBox3 = findViewById<ImageButton>(R.id.signup_consent_fact_check)

        when (show.id) {
            R.id.signup_consent_all_check -> {
                onCheckClick(isAgree, cBox1, binding.signupConsentInfoTv)
                /*onCheckClick(isAgree, cBox2, binding.signupConsentSecretTv)*/
                onCheckClick(isAgree, cBox3, binding.signupConsentFactTv)
            }
        }
        //binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.dark_red))
        if (nameCheck && emailCheck && PWCheck && agreeChecked) {
            Log.d("true-check", "전체 체크427")
            binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
        } else {
            Log.d("false-check", "전체 체크430")
            binding.signupCompleteBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.carrot))
        }
    }

}