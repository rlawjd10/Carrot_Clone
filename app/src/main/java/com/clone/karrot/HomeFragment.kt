package com.clone.karrot

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone.karrot.Adapter.PostingAdapter
import com.clone.karrot.data.Posting
import com.clone.karrot.databinding.ActivityPostingBinding
import com.clone.karrot.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val mDate = mutableListOf<Posting>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container, false)

        binding.mainFab.setOnClickListener {
            val intent = Intent(this.requireContext(), PostingActivity::class.java)
            startActivity(intent)
        }

        initPostRecyclerView()
        return binding.root
    }

    fun initPostRecyclerView() {
        val adapter = PostingAdapter()

        adapter.itemList = mDate.apply {
            add(Posting(R.drawable.christmas, "크리스마스 트리", "14,500", "끌올 12분 전", "강릉시 포남동"))
            add(Posting(R.drawable.bap, "햇반 팝니다.", "2,500", "10시간 전", "견소동"))
            add(Posting(R.drawable.ddippu, "포켓몬 띠부씰", "30,000", "15분 전", "강릉시 견소동"))
            add(Posting(R.drawable.duck, "오리 입양해가세여", "5,500", "끌올 1일 전", "강릉시 교남1동"))
            add(Posting(R.drawable.lemona, "레모나 급처해요", "500", "3시간 전", "강릉시 포남동"))
            add(Posting(R.drawable.planner, "플래너 팔아요. 1등급 쌉가능", "9,500", "21분 전", "입양동"))
        }
        binding.homePostRv.adapter = adapter
        binding.homePostRv.layoutManager = LinearLayoutManager(this.context)

    }

}