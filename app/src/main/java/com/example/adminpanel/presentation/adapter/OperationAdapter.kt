package com.example.adminpanel.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.domain.entities.Operation
import java.text.NumberFormat
import java.util.*

class OperationAdapter(
    val context: Context,
    private val dataset: List<Operation>
): RecyclerView.Adapter<OperationAdapter.OperationViewHolder>(){

    class OperationViewHolder(view: View): RecyclerView.ViewHolder(view){
        val numberSend:TextView = view.findViewById(R.id.textSendNum)
        val numberReceive:TextView = view.findViewById(R.id.textRecieveNum)
        val balanceOp:TextView = view.findViewById(R.id.textBalanceOperation)
        val time:TextView = view.findViewById(R.id.textTimeOperation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.operations_item, parent, false)
        return OperationViewHolder(adapterLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val item = dataset[position]

        holder.numberSend.text = "From " + item.send
        holder.numberReceive.text = "To " + item.receive
        holder.balanceOp.text = NumberFormat.getCurrencyInstance(Locale.US).format(item.sum)
        holder.time.text = item.time
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}