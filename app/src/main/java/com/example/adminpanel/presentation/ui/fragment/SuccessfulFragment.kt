package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.adminpanel.BuildConfig
import com.example.adminpanel.R
import com.example.adminpanel.data.PDFFileManager
import com.example.adminpanel.data.PDFFileManager.Companion.FILE_NAME_OPERATION
import com.example.adminpanel.data.PDFFileManager.Companion.PATH_FILE
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person

import kotlinx.android.synthetic.main.fragment_successfull.view.*
import java.io.File
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SuccessfulFragment : Fragment() {


    private var person: Person? = null
    private var operation: Operation? = null

    lateinit var textNamePerson:TextView
    lateinit var textNumberPerson:TextView
    lateinit var textSumOperation:TextView
    lateinit var textTimeOperation:TextView
    lateinit var textDateOperation:TextView
    lateinit var textDetailShare:TextView

    lateinit var buttonDone:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            person = requireArguments().getParcelable(SUCCESS_OPERATION_DATA)
            operation = requireArguments().getParcelable(SUCCESS_OPERATION)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View =  inflater.inflate(R.layout.fragment_successfull, container, false)

        textNamePerson = view.textDetailName
        textNumberPerson = view.texDetailNumberCard
        textSumOperation = view.textDetailSum
        textTimeOperation = view.textDetailTime
        textDateOperation = view.textDetailDate
        textDetailShare = view.textDetailShare

        buttonDone = view.buttonDone

        init()

        buttonDone.setOnClickListener{
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transactionFragment = fragmentManager.beginTransaction()

            transactionFragment.replace(R.id.fl_layout, HomeFragment())
            transactionFragment.commit()
        }

        textDetailShare.setOnClickListener {
            getOperationInPDF()
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getOperationInPDF() {
        val pdfFileManager = PDFFileManager()

        pdfFileManager.createOperationPDF(operation!!, person!!)
        Thread.sleep(500)
        openPdf(PATH_FILE, FILE_NAME_OPERATION)
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        textNamePerson.text = person!!.firstName + " " + person!!.lastName
        textNumberPerson.text = showCardNumber(person!!.number)
        textSumOperation.text = NumberFormat.getCurrencyInstance(Locale.US).format(operation!!.sum)


        val pattern = "yyyy-MM-dd HH:mm:ss.SSS"
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern(pattern)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val localDateTime = LocalDateTime.parse(operation!!.correctDateAndTime(this.operation!!.time), formatter)

        val dayOfWeek = String.format(
            "%s%s",
            localDateTime.dayOfWeek.toString().substring(0, 1),
            localDateTime.dayOfWeek.toString().substring(1).lowercase(
                Locale.getDefault()
            )
        )
        val month = String.format(
            "%s%s",
            localDateTime.month.toString().substring(0, 1),
            localDateTime.month.toString().substring(1).lowercase(
                Locale.getDefault()
            )
        )

        textTimeOperation.text = "Time\n${localDateTime.hour}.${localDateTime.minute}"
        textDateOperation.text = dayOfWeek + ", " + localDateTime.dayOfMonth + " " + month + " " + localDateTime.year



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

    private fun openPdf(path: String, nameFile: String) {
        val file = File(path, nameFile)
        val intent = Intent(Intent.ACTION_VIEW)

        val uri: Uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file)

        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No application found for pdf reader", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val SUCCESS_OPERATION_DATA = "success_operation_data"
        const val SUCCESS_OPERATION = "success_operation_sum"
    }
}