package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.R
import com.example.adminpanel.domain.entities.Person
import kotlinx.android.synthetic.main.fragment_transfer.view.*
import org.parceler.Parcels



class TransferFragment : Fragment() {

    lateinit var buttonBack: ImageButton
    lateinit var textUserName: TextView
    lateinit var editSum: EditText
    lateinit var buttonSend: Button


    private var person: Person? = null
    private var sum:Double = 0.0


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

        textUserName.text = showCardNumber(person!!.number) + " - \n" + person!!.firstName + " " + person!!.lastName

        editSum.setText(sum.toString(), TextView.BufferType.EDITABLE)

        buttonBack.setOnClickListener {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transactionFragment = fragmentManager.beginTransaction()

            transactionFragment.replace(R.id.fl_layout, HomeFragment())
            transactionFragment.commit()
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
}

