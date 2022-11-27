package com.example.adminpanel.domain.presenter

import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person

interface TransferFragmentContract {
    interface Presenter{
        fun enterOperation(sum: Double, person: Person)
    }

    interface View{
        fun showMessage()
        fun finishOperation(operation: Operation, person: Person)
    }
}