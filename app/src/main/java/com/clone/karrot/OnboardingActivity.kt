package com.clone.karrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clone.karrot.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_onboarding)
    }
}