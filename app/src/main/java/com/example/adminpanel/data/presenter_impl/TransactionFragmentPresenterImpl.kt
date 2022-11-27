package com.example.adminpanel.data.presenter_impl

import com.example.adminpanel.data.utilities.room.AdminDao
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.presenter.TransactionFragmentContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class TransactionFragmentPresenterImpl(
    view: TransactionFragmentContract.View,
    private val adminDao: AdminDao,
    private val uiContext: CoroutineContext = Dispatchers.Main
): TransactionFragmentContract.Presenter {



    override fun getOperationList(): Flow<List<Operation>> = adminDao.selectAllOperations()


}