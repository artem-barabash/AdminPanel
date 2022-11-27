package com.example.adminpanel.data.presenter_impl

import com.example.adminpanel.data.utilities.FireBaseManager
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import com.example.adminpanel.domain.presenter.TransferFragmentContract
import com.example.adminpanel.domain.use_cases.UserFactory.Companion.ACCOUNT
import java.sql.Timestamp
import java.util.*

class TransferFragmentPresenterImpl(val view: TransferFragmentContract.View) : TransferFragmentContract.Presenter{

    override fun enterOperation(sum: Double, person: Person) {
        val fireBaseManager = FireBaseManager()


        if(sum > 0 && sum <= ACCOUNT.balance){
            ACCOUNT.balance -= sum

            val date = Date()
            val timestamp = Timestamp(date.time)

            val operation = Operation(1, ACCOUNT.number, person.number, timestamp.toString(), sum)

            fireBaseManager.addOperation(operation)

            Thread.sleep(1500)

            view.finishOperation(operation, person)
        }else{
            view.showMessage()
        }
    }


}