package com.clone.karrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone.karrot.Adapter.RegionAdapter
import com.clone.karrot.data.AppDatabase
import com.clone.karrot.databinding.ActivityLocationBinding
import org.json.JSONObject

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    private var RegionDb : AppDatabase?= null
    private lateinit var RegionAdapter: RegionAdapter
    private lateinit var horizonManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_location)

        //json 데이터 가져오기, JSONObject로 접근한다.
        val json = assets.open("regions.json").reader().readText()
        val data = JSONObject(json).getJSONObject("name")

        //adapter 연결
        RegionAdapter = RegionAdapter(data)

        horizonManager = LinearLayoutManager(this@LocationActivity)
        horizonManager.orientation = LinearLayoutManager.HORIZONTAL

        binding.locationRv.apply {
            layoutManager = horizonManager
            adapter = RegionAdapter
        }

        binding.locationRv.adapter = RegionAdapter
        binding.locationRv.layoutManager = LinearLayoutManager(this)

        //새로운 DB 객체를 호출한다.
        RegionDb = AppDatabase.getInstance(this)

        //데이터에 읽고 쓸 때는 스레드 사용
        //main thread에서 DB 접근 불가 -> Room과 관련된 액션은 Thread를 이용해 백그라운드에서 작업
        val r = Runnable {  }

        val thread = Thread(r)
        thread.start()
    }
}