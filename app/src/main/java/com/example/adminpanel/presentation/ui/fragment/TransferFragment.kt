package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.TransferFragmentPresenterImpl
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.presenter.TransferFragmentContract
import com.example.adminpanel.presentation.ui.fragment.SuccessfulFragment.Companion.SUCCESS_OPERATION_DATA
import com.example.adminpanel.presentation.ui.fragment.SuccessfulFragment.Companion.SUCCESS_OPERATION
import kotlinx.android.synthetic.main.fragment_transfer.view.*


class TransferFragment : Fragment(), TransferFragmentContract.View {

    lateinit var buttonBack: ImageButton
    lateinit var textUserName: TextView
    lateinit var editSum: EditText
    lateinit var buttonSend: Button


    private var person: Person? = null
    private var sum:Double = 0.0

    private lateinit var transferPresenter: TransferFragmentPresenterImpl


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            person = requireArguments().getParcelable(PERSON_RECEIVER)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_transfer, container, false)

        buttonBack = view.imageButtonBack
        textUserName = view.text_user_field
        editSum = view.editTextSum
        buttonSend = view.button_send

        transferPresenter = TransferFragmentPresenterImpl(this)

        textUserName.text = showCardNumber(person!!.number) + " - \n" + person!!.firstName + " " + person!!.lastName

        editSum.setText(sum.toString(), TextView.BufferType.EDITABLE)

        buttonBack.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transactionFragment = fragmentManager.beginTransaction()

            transactionFragment.replace(R.id.fl_layout, HomeFragment())
            transactionFragment.commit()
        }

        buttonSend.setOnClickListener{
            transferPresenter.enterOperation(editSum.text.toString().toDouble(), person!!, requireContext())
        }

        return view
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


    companion object {
        const val PERSON_RECEIVER = "person_receiver"
        const val RECEIVER_SUM = "receiver_sum"

    }

    override fun showMessage() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.app_name)
            .setMessage(R.string.failure_payment)
            .setIcon(R.drawable.ic_launcher_foreground)
            .setNegativeButton("Cancel", null)
        dialog.show()
    }

    override fun finishOperation(operation: Operation, person: Person) {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.setReorderingAllowed(true)
        transactionFragment.addToBackStack(null)

        val fragment = SuccessfulFragment()
        val bundle = Bundle()
        bundle.putParcelable(SUCCESS_OPERATION_DATA, person)
        bundle.putParcelable(SUCCESS_OPERATION, operation)
        fragment.arguments = bundle

        transactionFragment.replace(R.id.fl_layout, fragment)
        transactionFragment.commit()
    }


}

