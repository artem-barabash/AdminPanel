package com.example.adminpanel.domain.presenter

import android.content.Context
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person

interface TransferFragmentContract {
    interface Presenter{
        fun enterOperation(sum: Double, person: Person, context: Context)
    }

    interface View{
        fun showMessage()
        fun finishOperation(operation: Operation, person: Person)
    }
}