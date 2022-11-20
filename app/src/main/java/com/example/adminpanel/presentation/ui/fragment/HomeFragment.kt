package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.HomeFragmentPresenterImpl
import com.example.adminpanel.domain.presenter.HomeFragmentContract
import com.example.adminpanel.domain.use_cases.UserFactory.Companion.ACCOUNT
import java.text.NumberFormat
import java.util.*


class HomeFragment() : Fragment(), HomeFragmentContract.View {

    private lateinit var homeFragmentPresenter: HomeFragmentContract.Presenter

    lateinit var textNumber:TextView
    lateinit var textBalance: TextView
    lateinit var textFullName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeFragmentPresenter = HomeFragmentPresenterImpl(this)


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
        textBalance.text = NumberFormat.getCurrencyInstance(Locale.US).format(ACCOUNT.balance)

        return view.rootView
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