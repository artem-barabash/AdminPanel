package com.example.adminpanel.data.utilities

import com.example.adminpanel.data.utilities.room.AdminDao
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.entities.Question
import com.example.adminpanel.domain.entities.Variant
import com.example.adminpanel.domain.use_cases.UserFactory.Companion.ACCOUNT
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class FireBaseManager {
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun retrieveAllPersons(adminDao: AdminDao){
        val personRef = databaseReference.child("User")
        val list = ArrayList<Person>()

        var id = 1

        personRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){


                    for(h in snapshot.children){
                        val balance: Any? = h.child("balance").value

                        val nBalance: Double = if (balance != null && balance.toString() != "0") {
                            balance.toString().toDouble()
                        } else 0.0

                        if (h.key.toString() != ACCOUNT.number){
                            list.add(
                                Person(
                                    id,
                                    h.key.toString(),
                                    h.child("firstName").value.toString(),
                                    h.child("lastName").value.toString(),
                                    nBalance
                                )
                            )
                            id++
                        }


                    }
                }

                adminDao.insertAllPersons(list)

                //println(list)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException()
            }

        })
    }

    fun retrieveAllOperations(adminDao: AdminDao){
        val operationsRef = databaseReference.child("Operation")
        val list = ArrayList<Operation>()

        var id = 0

        operationsRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for(h in snapshot.children){

                        val sum: Any? = h.child("sum").value

                        val nSum: Double = if (sum != null && sum.toString() != "0") {
                            sum.toString().toDouble()
                        } else 0.0

                        list.add(
                            Operation(
                                id,
                                h.child("send").value.toString(),
                                h.child("receive").value.toString(),
                                h.child("time").value.toString(),
                                nSum
                            )
                        )

                    }

                }

                adminDao.insertAllOperations(list)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException()
            }

        })
    }

    fun addOperation(operation: Operation) {
        val operationsRef = databaseReference.child("Operation")

        updateBalanceUser(operation.send, operation.sum, false)

        operationsRef.child(operation.send + "-"+ operation.time.replace('.', ',')).setValue(operation)

        updateBalanceUser(operation.receive, operation.sum, true)
    }

    private fun updateBalanceUser(number: String, sum: Double, operation: Boolean) {
        databaseReference = FirebaseDatabase.getInstance().reference
        val userRef = databaseReference.child("User")
        val query = userRef.orderByKey().equalTo(number)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children) {

                    var balance = itemSnapshot.child("balance").getValue(
                        Double::class.java
                    )!!
                    if (operation) {
                        balance += sum
                    } else {
                        balance -= sum
                    }
                    userRef.child(number).child("balance")
                        .setValue((balance * 100.00).roundToInt() / 100.00)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })
    }


}