package com.example.adminpanel.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.TransactionFragmentPresenterImpl
import com.example.adminpanel.data.utilities.room.UserDataApplication
import com.example.adminpanel.domain.presenter.HomeFragmentContract
import com.example.adminpanel.domain.presenter.TransactionFragmentContract
import com.example.adminpanel.presentation.adapter.OperationAdapter
import kotlinx.coroutines.launch
import java.util.*


class TransactionFragment() : Fragment(), TransactionFragmentContract.View {

    lateinit var transactionFragmentPresenter: TransactionFragmentPresenterImpl
    lateinit var recyclerViewOperation: RecyclerView
    lateinit var operationAdapter: OperationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_transaction, container, false)


        transactionFragmentPresenter = TransactionFragmentPresenterImpl(
            this,
            (activity?.application as UserDataApplication).database.adminDao()
        )

        recyclerViewOperation = view!!.findViewById(R.id.operations_recycler_view)



        recyclerViewOperation.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            transactionFragmentPresenter.operationList.collect(){ it ->

                operationAdapter = OperationAdapter(requireContext(), it.sortedByDescending { it.time })
                recyclerViewOperation.adapter = operationAdapter
            }
        }


        return view
    }

    companion object {

    }
}