package com.clone.karrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clone.karrot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavi()
    }

    //바텀 네비게이션 when() 활용해서 fragment 연결
    private fun initBottomNavi() {
        //초기 화면은 homefragment로 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true;
                }

                R.id.menu_des -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LifeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true;
                }

                R.id.menu_location -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LocationFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true;
                }

                R.id.menu_chat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ChatFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true;
                }

                R.id.menu_person -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, InfoFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true;
                }

            }
            false
        }
    }
}