package com.example.adminpanel.domain.presenter

import androidx.lifecycle.LiveData
import com.example.adminpanel.domain.entities.Answer
import com.example.adminpanel.domain.entities.StatisticsItem
import com.example.adminpanel.domain.entities.Variant
import kotlinx.coroutines.flow.Flow

interface VotingPageFragmentContract {
    interface Presenter{
        fun getAllVariants(numberQuestion: String): Flow<List<Variant>>

        fun getAllStatisticsOnQuestion(number: String): LiveData<List<StatisticsItem>>

        fun getVotingQualityOfQuestion(number: String): Flow<Int>

        fun parseDate(dateTime: String): String?

        fun closeQuestion(number: String)
    }

    interface View{}
}