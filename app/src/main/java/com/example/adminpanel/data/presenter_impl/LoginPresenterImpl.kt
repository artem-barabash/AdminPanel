package com.example.adminpanel.data.presenter_impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.adminpanel.R
import com.example.adminpanel.domain.entities.User
import com.example.adminpanel.domain.presenter.LoginContract
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.BALANCE
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.BIRTHDAY
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.EMAIL
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.FIRST_NAME
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.GENDER
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.HOME_ADDRESS
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.LAST_NAME
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.NUMBER_KEY
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.PASSWORD
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.PHONE

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.*
import org.checkerframework.checker.guieffect.qual.UI
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class LoginPresenterImpl(
    private val view: LoginContract.View,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : LoginContract.Presenter,
    CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = uiContext + job

   lateinit var auth: FirebaseAuth

   public fun todo():Int{
       return 2+2
   }

    override fun exitAccount(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        //todo

        //databaseReference = FirebaseDatabase.getInstance().reference

        if(email.isNotEmpty() && password.isNotEmpty()){
            if(email == "admin@gmail.com"){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){

                            coroutineContext.let {
                                view.retrieveUserDataFromFireBase(email, password)

                                Thread.sleep(2000)

                                view.enterToHomeActivity()
                                view.setTextViewError(R.string.empty)
                                view.clearFields()

                            }


                        }
                    }.addOnFailureListener{
                        view.setTextViewError(R.string.failure_auth)
                    }
            }else{
                view.setTextViewError(R.string.failure_auth)
            }
        }else{
            view.setTextViewError(R.string.message_empty_fields)
        }
    }




    override fun isOnline(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                true
            }else{
                false
            }
        }else{

            return false
        }

    }

}