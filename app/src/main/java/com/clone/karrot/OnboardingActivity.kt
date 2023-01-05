package com.clone.karrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clone.karrot.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onboardingStartBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        //로그인 하기
        binding.onboardingLoginTv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}