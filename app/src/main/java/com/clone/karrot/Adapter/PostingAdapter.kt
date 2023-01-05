package com.clone.karrot.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clone.karrot.data.Posting
import com.clone.karrot.databinding.ItemTransactionBinding

class PostingAdapter: RecyclerView.Adapter<PostingAdapter.ViewHolder>() {

    var itemList = mutableListOf<Posting>()
    inner class ViewHolder(private val viewBinding: ItemTransactionBinding)
        : RecyclerView.ViewHolder(viewBinding.root) {
            fun bind(Posting: Posting) {
                viewBinding.textUsedItemTitle.text = Posting.title
                viewBinding.textUsedItemPrice.text = Posting.price
                viewBinding.textUsedItemRegion.text = Posting.region
                viewBinding.textUsedItemTimePassed.text = Posting.time
                Glide.with(itemView.context)
                    .load(Posting.image)
                    .into(viewBinding.imageUsedItem)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}