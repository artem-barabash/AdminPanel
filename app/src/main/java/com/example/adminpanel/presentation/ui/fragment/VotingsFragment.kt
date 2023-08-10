package com.example.adminpanel.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.VotingsFragmentPresenterImpl
import com.example.adminpanel.data.utilities.room.UserDataApplication
import com.example.adminpanel.domain.presenter.VotingsFragmentContract
import com.example.adminpanel.presentation.adapter.PersonAdapter
import com.example.adminpanel.presentation.adapter.QuestionAdapter
import kotlinx.coroutines.launch


class VotingsFragment : Fragment(), VotingsFragmentContract.View {
    private lateinit var presenter: VotingsFragmentContract.Presenter
    private lateinit var buttonQuestion: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = VotingsFragmentPresenterImpl(
            this,
            (activity?.application as UserDataApplication).database.adminDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_votings, container, false)

        recyclerView = view.findViewById(R.id.questionRecyclerView)
        buttonQuestion = view.findViewById(R.id.btnAddQuestion)

        buttonQuestion.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transactionFragment = fragmentManager.beginTransaction()

            transactionFragment.replace(R.id.fl_layout, AddVotingFragment())
            transactionFragment.commit()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            //Thread.sleep(3000)

            presenter.getQuestionList().collect() {
                questionAdapter = QuestionAdapter(requireContext(), it)
                recyclerView.adapter = questionAdapter
            }
        }

        return view
    }

    companion object {
    }
}