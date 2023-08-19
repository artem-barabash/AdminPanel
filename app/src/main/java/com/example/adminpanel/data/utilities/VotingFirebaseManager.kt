package com.example.adminpanel.data.utilities

import com.example.adminpanel.data.utilities.room.AdminDao
import com.example.adminpanel.domain.entities.Question
import com.example.adminpanel.domain.entities.Variant
import com.google.firebase.database.*
import org.mockito.stubbing.Answer
import java.util.*

class VotingFirebaseManager {
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addQuestion(question: Question){
        val questionsRef = databaseReference.child("Voting/Question")

        questionsRef.child(question.number).setValue(question)
    }
    fun addVariant(variant: Variant) {
        val variantsRef = databaseReference.child("Voting/Variant")

        variantsRef.child(UUID.randomUUID().toString()).setValue(variant)
    }

    fun retrieveAllQuestion(adminDao: AdminDao){
        val questionRef = databaseReference.child("Voting/Question")
        val list = ArrayList<Question>()

        var id = 1

        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {


                    for (h in snapshot.children) {

                        val isOpen = h.child("open").value.toString() == "true"
                        list.add(
                            Question(
                                id,
                                h.child("number").value.toString(),
                                h.child("textQuestion").value.toString(),
                                isOpen,
                                h.child("time").value.toString()
                            )
                        )
                        id++
                    }
                }

                adminDao.insertAllQuestions(list)
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }

    fun retrieveAllVariants(adminDao: AdminDao){
        val questionRef = databaseReference.child("Voting/Variant")
        val list = ArrayList<Variant>()

        var id = 1

        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {


                    for (h in snapshot.children) {
                        list.add(
                            Variant(
                                id,
                                h.child("numberQuestion").value.toString(),
                                h.child("text").value.toString()
                            )
                        )
                        id++
                    }
                }

                adminDao.insertAllVariants(list)
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }

    fun retrieveAllAnswers(adminDao: AdminDao){
        val answerRef = databaseReference.child("Voting/Answer")
        val list = ArrayList<com.example.adminpanel.domain.entities.Answer>()

        var id = 1

        answerRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                    for (h in snapshot.children) {
                        list.add(
                            com.example.adminpanel.domain.entities.Answer(
                                id,
                                h.child("userNumberKey").value.toString(),
                                h.child("numberQuestion").value.toString(),
                                h.child("textAnswer").value.toString(),
                                h.child("dateTime").value.toString(),
                            )
                        )
                        id++
                    }
                }

                adminDao.insertAllAnswers(list)
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }

    fun closeQuestion(number: String) {
        val questionRef = databaseReference.child("Voting/Question")

        questionRef.orderByKey().equalTo(number)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(itemSnapshot in snapshot.children){
                        questionRef.child(number).child("open").setValue("false")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println(error.message)
                }
            })
    }
}