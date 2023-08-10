package com.example.adminpanel.data.presenter_impl

import com.example.adminpanel.data.utilities.VotingFirebaseManager
import com.example.adminpanel.domain.entities.Question
import com.example.adminpanel.domain.entities.Variant
import com.example.adminpanel.domain.presenter.AddVotingFragmentContract
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class AddVotingFragmentPresenterImpl(val view: AddVotingFragmentContract.View): AddVotingFragmentContract.Presenter{
    private val votingFirebaseManager = VotingFirebaseManager()

    override fun addToFireBaseVoting(question: String, variantsList: ArrayList<String>) {
        val date = Date()
        val timestamp = Timestamp(date.time)

        val number = UUID.randomUUID().toString()

        val voting = Question(
            1,
            number,
            question,
            true,
            timestamp.toString(),
        )

        votingFirebaseManager.addQuestion(voting)

        addAllVariantsToFirebase(variantsList, number)
    }

    private fun addAllVariantsToFirebase(arrayList: ArrayList<String>, number: String) {
        for((count, textVariant) in arrayList.withIndex()){
            val variant = Variant(count + 1, number, textVariant)
            votingFirebaseManager.addVariant(variant)
        }
    }

}