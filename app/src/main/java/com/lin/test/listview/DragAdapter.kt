package com.lin.test.listview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lin.test.databinding.ItemLvBinding

/**
 * created by zll on 2024/7/3 11:15
 */
class DragAdapter(private val list: List<String>) : RecyclerView.Adapter<DragAdapter.VH>() {


    inner class VH(val binding: ItemLvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.itemTv.text = list[position]
    }

}