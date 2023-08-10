package com.example.adminpanel.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.presentation.ui.fragment.TransferFragment
import com.example.adminpanel.presentation.ui.fragment.TransferFragment.Companion.PERSON_RECEIVER
import com.example.adminpanel.presentation.ui.fragment.TransferFragment.Companion.RECEIVER_SUM
import kotlinx.android.synthetic.main.person_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class PersonAdapter(
    private val context: Context,
    private val dataset:List<Person?>
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(){


    class PersonViewHolder(view: View): RecyclerView.ViewHolder(view){
        val fullName: TextView = view.findViewById(R.id.textNameUser)
        val numberUser: TextView = view.findViewById(R.id.textNumber)
        val balanceUser:TextView = view.findViewById(R.id.textUserBalance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(adapterLayout)
    }

    @SuppressLint("SetTextI18n", "SourceLockedOrientationActivity")
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = dataset[position]

        holder.fullName.text = item!!.firstName + " " + item.lastName
        holder.numberUser.text = showCardNumber(item.number)
        holder.balanceUser.text = NumberFormat.getCurrencyInstance(Locale.US).format(item.balance)

        holder.itemView.setOnClickListener {


                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val transactionFragment = fragmentManager.beginTransaction()

                val fragment = TransferFragment()
                val bundle = Bundle()

                bundle.putParcelable(PERSON_RECEIVER, item)


                fragment.arguments = bundle

                transactionFragment.setReorderingAllowed(true)
                transactionFragment.addToBackStack("tag")


                transactionFragment.replace(R.id.fl_layout, fragment)
                transactionFragment.commit()


        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private fun showCardNumber(number: String?): String? {
        val arrNumber = number?.split("".toRegex())?.toTypedArray()
        val sb = StringBuilder()
        if (arrNumber != null) {
            for (i in arrNumber.indices) {
                sb.append(arrNumber[i])
                if (i % 4 == 0 && i != arrNumber.size - 1) sb.append(" ")
            }
        }
        return sb.toString()
    }
}