package com.example.adminpanel.data

import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.entities.User
import com.example.adminpanel.domain.use_cases.UserFactory.Companion.ACCOUNT
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PDFFileManager {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createOperationPDF(operation: Operation, person: Person){
        val document = PdfDocument()
        // crate a page description
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        // start a page
        val page = document.startPage(pageInfo)

        val canvas = page.canvas

        val paint = Paint()

        paint.color = Color.BLACK



        canvas.drawText("The salary", 50f, 20f, paint)


        canvas.drawText("Send: " + "${ACCOUNT.firstName} ${ACCOUNT.lastName} .", 10f, 50f, paint)
        canvas.drawText("Receive: " + showCardNumber(operation.send) + " ${person.firstName} ${person.lastName} .", 10f, 70f, paint)
        canvas.drawText("Sum: $${operation.sum}", 10f, 90f, paint)
        canvas.drawText("Time: ${operation.time}", 10f, 120f, paint)

        document.finishPage(page)

        val file = File(PATH_FILE)

        if(!file.exists()){
            file.mkdirs()
        }

        val pattern = "yyyy-MM-dd HH:mm:ss.SSS"
        val formatter = DateTimeFormatter.ofPattern(pattern)

        val localDateTime = LocalDateTime.parse(operation.correctDateAndTime(operation.time), formatter)

        FILE_NAME_OPERATION = "${operation.send}_${localDateTime.year}_${localDateTime.monthValue}_${localDateTime.dayOfMonth}_${localDateTime.hour}_${localDateTime.minute}.pdf"

        val filePath = File(PATH_FILE, FILE_NAME_OPERATION)

        try {
            document.writeTo(FileOutputStream(filePath))
        }catch (e: IOException){
            Log.e("main", "error $e")
        }
        document.close()
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

    companion object{
        val PATH_FILE: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        lateinit var FILE_NAME_OPERATION: String
    }
}