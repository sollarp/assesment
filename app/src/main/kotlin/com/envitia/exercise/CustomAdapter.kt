package com.envitia.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.envitia.exercise.databinding.ListElementBinding
import com.envitia.exercise.model.TimeAndText

class CustomAdapter(private var timeTextList: ArrayList<TimeAndText>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time = timeTextList[position].time
        val text = timeTextList[position].text
        holder.bind(time, text)
    }

    override fun getItemCount(): Int {
        return timeTextList.size
    }

    fun updateData(newItems: ArrayList<TimeAndText>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = timeTextList.size
            override fun getNewListSize() = newItems.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return timeTextList[oldItemPosition].text == newItems[newItemPosition].text
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return timeTextList[oldItemPosition] == newItems[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        timeTextList.clear()
        timeTextList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ListElementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(time: String, text: String) {
            val formattedText = binding.root.context.getString(
                R.string.time_and_text_format, time, text
            )
            binding.textViewItem.text = formattedText
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, time, Toast.LENGTH_SHORT).show()
            }
        }
    }
}