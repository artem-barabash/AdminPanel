package com.example.adminpanel.domain.presenter

import android.content.Context
import kotlinx.coroutines.Job

interface LoginContract {

    interface Presenter{
        fun exitAccount(email: String, password: String)

        fun isOnline(context: Context): Boolean
    }
    interface View{
        fun enterToHomeActivity()

        fun setTextViewError(str: Int)

        fun clearFields()

        fun retrieveUserDataFromFireBase(email: String, password: String)
    }

}