package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.HomeFragmentPresenterImpl
import com.example.adminpanel.data.utilities.FireBaseManager
import com.example.adminpanel.data.utilities.room.UserDataApplication
import com.example.adminpanel.domain.presenter.HomeFragmentContract
import com.example.adminpanel.domain.use_cases.UserFactory.Companion.ACCOUNT
import com.example.adminpanel.presentation.adapter.PersonAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.Format
import java.text.NumberFormat
import java.util.*
import kotlin.coroutines.coroutineContext


class HomeFragment() : Fragment(), HomeFragmentContract.View {

    private lateinit var  homeFragmentPresenter: HomeFragmentPresenterImpl

    lateinit var textNumber:TextView
    lateinit var textBalance: TextView
    lateinit var textFullName: TextView

    lateinit var recyclerViewUser: RecyclerView
    lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeFragmentPresenter = HomeFragmentPresenterImpl(
            this,
                (activity?.application as UserDataApplication).database.adminDao()
        )
    }

    @SuppressLint("UseRequireInsteadOfGet", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        textNumber = view!!.findViewById(R.id.text_num)
        textBalance = view!!.findViewById(R.id.text_balance)
        textFullName = view!!.findViewById(R.id.text_full_name)

        textNumber.text = showCardNumber(ACCOUNT.number)
        textFullName.text = "${ACCOUNT.firstName} ${ACCOUNT.lastName}"

        /*runBlocking {
            launch {
                getBalance().collect{ textBalance.text = it}
            }

        }*/
        updateBalance()

        recyclerViewUser = view.findViewById(R.id.users_recycler_view)
        recyclerViewUser.layoutManager = LinearLayoutManager(requireContext())


        viewLifecycleOwner.lifecycleScope.launch {
            //Thread.sleep(3000)

            homeFragmentPresenter.personsList.collect() {
                personAdapter = PersonAdapter(requireContext(), it)
                recyclerViewUser.adapter = personAdapter
            }
        }

        return view.rootView
    }

    private fun updateBalance() {
        val userRf = FirebaseDatabase.getInstance().reference.child("User")
        userRf.orderByKey().equalTo(ACCOUNT.number)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(itemSnapshot in snapshot.children){
                        val balance = itemSnapshot.child("balance").getValue(Double::class.java)

                        textBalance.text = NumberFormat.getCurrencyInstance(Locale.US).format(balance)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBalance(): Flow<String> = flow {
        emit(NumberFormat.getCurrencyInstance(Locale.US).format(ACCOUNT.balance))
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