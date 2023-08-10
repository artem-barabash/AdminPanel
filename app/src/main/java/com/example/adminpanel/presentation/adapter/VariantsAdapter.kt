package com.example.adminpanel.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R

class VariantsAdapter(
    private val list: ArrayList<String>
):RecyclerView.Adapter<VariantsAdapter.VariantsViewHolder>() {


    class VariantsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textVariant: TextView = view.findViewById(R.id.textVariant)
        val removeButton:ImageButton = view.findViewById(R.id.btnClose)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.variants_item, parent, false)
        return VariantsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: VariantsViewHolder, position: Int) {
        val item = list[position]

        holder.textVariant.text = item

        holder.removeButton.setOnClickListener {
            list.remove(item)
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }

}