package com.clone.karrot.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clone.karrot.data.Region
import com.clone.karrot.databinding.ItemLocationBinding
import org.json.JSONObject


class RegionAdapter(private val datas: JSONObject): RecyclerView.Adapter<RegionAdapter.ViewHolder>(){

    inner class ViewHolder(private val viewBinding: ItemLocationBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(region: JSONObject) {
            val name = region.getString("name")
            viewBinding.locationPlusTv.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas)
    }

    override fun getItemCount(): Int = datas.length()


}