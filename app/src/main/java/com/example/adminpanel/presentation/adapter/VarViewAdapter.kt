package com.example.adminpanel.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.domain.entities.StatisticsItem
import com.example.adminpanel.domain.entities.Variant

class VarViewAdapter(
    private val list: List<StatisticsItem>,
    private val sum: Int
): RecyclerView.Adapter<VarViewAdapter.VarViewHolder>() {

    class VarViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textVariant: TextView = view.findViewById(R.id.textVariant)
        val textQuality:TextView = view.findViewById(R.id.textQuality)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VarViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.variants_view_item, parent, false)
        return VarViewHolder(adapterLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VarViewHolder, position: Int) {
        val item = list[position]

        val sumAndPercent:Double =  (item.quality.toDouble() / sum) * 100.00

        if(item.quality != 0){
            holder.textQuality.text = "${item.quality} - "+
                    String.format("%.2f", sumAndPercent) + "%"
        }else{
            holder.textQuality.text = "0 - 0%"
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}