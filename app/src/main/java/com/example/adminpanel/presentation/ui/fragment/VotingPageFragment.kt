package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.VotingPageFragmentPresenterImpl
import com.example.adminpanel.data.utilities.room.UserDataApplication
import com.example.adminpanel.domain.entities.Question
import com.example.adminpanel.domain.presenter.VotingPageFragmentContract
import com.example.adminpanel.presentation.adapter.VarViewAdapter
import kotlinx.android.synthetic.main.fragment_voting_page.*
import kotlinx.android.synthetic.main.fragment_voting_page.view.*
import kotlinx.coroutines.launch

class VotingPageFragment : Fragment(), VotingPageFragmentContract.View {
    private lateinit var presenter: VotingPageFragmentContract.Presenter

    private var question:Question? = null

    lateinit var textTitle:TextView
    lateinit var textTime:TextView

    lateinit var variantsViewAdapter: VarViewAdapter
    lateinit var recyclerView: RecyclerView

    lateinit var textSumVoting: TextView
    
    lateinit var buttonCloseQuestion: Button

    //TODO get question and get list to recyclerView with answers and statistics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = VotingPageFragmentPresenterImpl(
            this,
            (activity?.application as UserDataApplication).database.adminDao()
        )

        if(arguments != null){
            question = requireArguments().getParcelable(PAGE_QUESTION)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voting_page, container, false)

        textTitle = view!!.textViewTitle
        textTime = view.textViewTime
        recyclerView =  view.variantsRecyclerView
        textSumVoting = view.textSumVoting

        buttonCloseQuestion = view.buttonClose

        textTitle.text = question!!.textQuestion
        textTime.text = "The question is opened " + presenter.parseDate(question!!.time)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        presenter.getAllStatisticsOnQuestion(question!!.number)
            .observe(viewLifecycleOwner, Observer {list ->

                viewLifecycleOwner.lifecycleScope.launch {
                    presenter.getVotingQualityOfQuestion(number = question!!.number).collect(){ quality ->
                        textSumVoting.text = "${quality} (100%)"

                        recyclerView.adapter = VarViewAdapter(
                            list.sortedByDescending {item ->  item.quality},
                            quality
                        )
                    }

                }


        })

        buttonCloseQuestion.setOnClickListener {
            presenter.closeQuestion(question!!.number)
            Toast.makeText(this.context, "Question is closed", Toast.LENGTH_SHORT).show()
        }

        return view
    }


    companion object {

        const val PAGE_QUESTION = "PAGE_QUESTION"
    }
}