package com.example.adminpanel.data.presenter_impl

import android.content.Context
import com.example.adminpanel.data.notification.FCMSender
import com.example.adminpanel.data.utilities.FireBaseManager
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.presenter.TransferFragmentContract
import com.example.adminpanel.domain.use_cases.UserFactory.Companion.ACCOUNT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Timestamp
import java.text.NumberFormat
import java.util.*

class TransferFragmentPresenterImpl(val view: TransferFragmentContract.View) : TransferFragmentContract.Presenter{

    override fun enterOperation(sum: Double, person: Person, context: Context) {
        val fireBaseManager = FireBaseManager()

        val userRf = FirebaseDatabase.getInstance().reference.child("User")
        userRf.orderByKey().equalTo(ACCOUNT.number)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(itemSnapshot in snapshot.children){
                        val balance = itemSnapshot.child("balance").getValue(Double::class.java)

                        if(sum > 0 && sum <= balance!!){
                            val date = Date()
                            val timestamp = Timestamp(date.time)

                            val operation = Operation(1, ACCOUNT.number, person.number, timestamp.toString(), sum)

                            fireBaseManager.addOperation(operation)

                            sendNotification(sum, person.number, context)

                            Thread.sleep(1500)

                            view.finishOperation(operation, person)
                        }else{
                            view.showMessage()
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException()
                }
            })

    }

    private fun sendNotification(sum: Double, number: String, context: Context) {
        val userRf = FirebaseDatabase.getInstance().reference.child("User")

        userRf.orderByKey().equalTo(number)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (itemSnapshot in snapshot.children){
                        val token = itemSnapshot.child("token").getValue(String::class.java)

                        FCMSender.pushNotification(
                            context,
                            token,
                            sum
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException()
                }

            })
    }


}