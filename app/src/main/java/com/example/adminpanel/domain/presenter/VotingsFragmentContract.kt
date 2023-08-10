package com.example.adminpanel.domain.presenter

import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.entities.Question
import kotlinx.coroutines.flow.Flow

interface VotingsFragmentContract {

    interface View{}

    interface Presenter{
        //fun getQuestion(): Flow<List<Question>>
        open fun getQuestionList(): Flow<List<Question>>
    }
}