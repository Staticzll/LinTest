package com.lin.test

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.lin.test.bean.ActivityBean
import com.lin.test.databinding.ItemActivityBinding

/**
 * created by zll on 2024/5/30 16:23
 */
class ActivityAdapter : RecyclerView.Adapter<ActivityAdapter.VH>() {
    private val mItems = mutableListOf<ActivityBean>()

    fun add(activityBean: ActivityBean) {
        mItems.add(activityBean)
        notifyItemChanged(mItems.size - 1)
    }

    fun addAll(all: Collection<ActivityBean>) {
        val position = mItems.size - 1
        mItems.addAll(all)
        notifyItemRangeChanged(position, all.size)
    }

    inner class VH(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = it.tag as ActivityBean
                start(it.context, item.clazz)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = mItems.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mItems.get(position)
        holder.binding.apply {
            root.text = item.name
            root.tag = item
        }
    }

    private fun start(context: Context, clazz: Class<*>) {
        val intent = Intent(context, clazz)
        context.startActivity(intent)
    }
}