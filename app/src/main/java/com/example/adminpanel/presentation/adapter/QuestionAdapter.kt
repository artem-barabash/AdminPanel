package com.example.adminpanel.presentation.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.domain.entities.Question
import com.example.adminpanel.presentation.ui.fragment.VotingPageFragment
import com.example.adminpanel.presentation.ui.fragment.VotingPageFragment.Companion.PAGE_QUESTION

class QuestionAdapter(
    private val context: Context,
    private val dataset:List<Question?>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>(){


    class QuestionViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text:TextView = view.findViewById(R.id.textVariant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.question_item,
            parent, false)

        return QuestionViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = dataset[position]

        holder.text.text = dataset[position]!!.textQuestion

        holder.itemView.setOnClickListener {
            //TODO get question and put
            val bundle = Bundle()
            bundle.putParcelable(PAGE_QUESTION,question)

            enterToFinishFragment(bundle)
        }
    }


    private fun enterToFinishFragment(bundle: Bundle) {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.setReorderingAllowed(true)
        transactionFragment.addToBackStack(null)

        val fragment = VotingPageFragment()
        fragment.arguments = bundle

        transactionFragment.replace(R.id.fl_layout, fragment)

        transactionFragment.commit()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}