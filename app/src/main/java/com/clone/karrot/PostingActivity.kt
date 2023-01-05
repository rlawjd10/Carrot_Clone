package com.clone.karrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clone.karrot.Adapter.PostingAdapter
import com.clone.karrot.data.Posting
import com.clone.karrot.databinding.ActivityPostingBinding

class PostingActivity : AppCompatActivity() {

    private lateinit var binding: PostingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)
    }

}