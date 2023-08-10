package com.example.adminpanel.data.presenter_impl

import com.example.adminpanel.data.utilities.room.AdminDao
import com.example.adminpanel.data.utilities.VotingFirebaseManager
import com.example.adminpanel.domain.entities.Question
import com.example.adminpanel.domain.presenter.VotingsFragmentContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class VotingsFragmentPresenterImpl(
    view: VotingsFragmentContract.View,
    private val adminDao: AdminDao,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : VotingsFragmentContract.Presenter, CoroutineScope{

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val fireBaseManager = VotingFirebaseManager()

    init {
        coroutineContext.let {
            adminDao.deleteAllVariants()

            adminDao.deleteAllQuestions()

            adminDao.deleteAllAnswers()

            fireBaseManager.retrieveAllQuestion(adminDao)
            fireBaseManager.retrieveAllVariants(adminDao)
            fireBaseManager.retrieveAllAnswers(adminDao)
        }
    }

    override fun getQuestionList(): Flow<List<Question>> = adminDao.selectAllQuestions()
}