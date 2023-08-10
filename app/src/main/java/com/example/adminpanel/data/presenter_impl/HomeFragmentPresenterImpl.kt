package com.example.adminpanel.data.presenter_impl

import com.example.adminpanel.data.utilities.FireBaseManager
import com.example.adminpanel.data.utilities.room.AdminDao
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.presenter.HomeFragmentContract
import com.example.adminpanel.presentation.adapter.PersonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

import kotlin.coroutines.CoroutineContext

class HomeFragmentPresenterImpl(
    view: HomeFragmentContract.View,
    private val adminDao: AdminDao,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : HomeFragmentContract.Presenter, CoroutineScope{

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val fireBaseManager = FireBaseManager()

    init {

        coroutineContext.let {
            adminDao.deleteAllPersons()

            adminDao.deleteAllOperations()

            fireBaseManager.retrieveAllPersons(adminDao)
            fireBaseManager.retrieveAllOperations(adminDao)
        }

    }


    override fun getPersonsList(): Flow<List<Person>> = adminDao.selectAllPersons()


    //override fun getOperationsList(): Flow<List<Operation>> = adminDao.selectAllOperations()
}