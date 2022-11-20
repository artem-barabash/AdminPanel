package com.example.adminpanel.presentation.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.HomeFragmentManagerPresenter
import com.example.adminpanel.domain.entities.User
import com.example.adminpanel.domain.presenter.HomeFragmentManagerContract
import com.example.adminpanel.domain.use_cases.UserFactory
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
import com.example.adminpanel.presentation.ui.LoginActivity.Companion.TEMP_USER_DATA
import com.example.adminpanel.presentation.ui.fragment.HomeFragment
import com.example.adminpanel.presentation.ui.fragment.TransactionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), HomeFragmentManagerContract.View{

    private lateinit var navigationBar: BottomNavigationView

    private lateinit var homeFragmentManagerPresenter: HomeFragmentManagerPresenter

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(savedInstanceState != null){
            PAGE = savedInstanceState.getInt(KEY_REVENUE, 0)
        }

        sharedPreferences = this.getSharedPreferences(TEMP_USER_DATA, MODE_PRIVATE)

        getUserData()

        homeFragmentManagerPresenter = HomeFragmentManagerPresenter(this)


        navigationBar = findViewById(R.id.bottomNavigationBar)

        changePage(PAGE)

        navigationBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.itemHome -> changePage(0)
                R.id.itemTransactions -> changePage(1)
            }

            return@setOnItemSelectedListener true
        }


    }

    private fun getUserData() {


        val nBalance: String? = sharedPreferences.getString(BALANCE, "")

        val balance: Double = if(nBalance != null && nBalance != "0"){
            nBalance.toDouble()
        }else{
            0.0
        }

        val user = User(
            sharedPreferences.getString(NUMBER_KEY, "").toString(),
            sharedPreferences.getString(PASSWORD, "").toString(),
            sharedPreferences.getString(FIRST_NAME, "").toString(),
            sharedPreferences.getString(LAST_NAME, "").toString(),
            sharedPreferences.getString(PHONE, "").toString(),
            sharedPreferences.getString(EMAIL, "").toString(),
            sharedPreferences.getString(BIRTHDAY, "").toString(),
            sharedPreferences.getString(GENDER, "").toString(),
            sharedPreferences.getString(HOME_ADDRESS, "").toString(),
            balance
        )

        UserFactory(user)

    }

    private fun changePage(changedPage:Int){
        when(changedPage){
            0 -> {
                PAGE = changedPage
                replaceFragment(HomeFragment())
            }
            1 -> {
                PAGE = changedPage
                replaceFragment(TransactionFragment())
            }
        }
    }

    private fun replaceFragment(selected: Fragment){
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fl_layout, selected)
        transaction.commit()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_REVENUE, PAGE)

    }

    companion object{
        const val KEY_REVENUE = "revenue_key"
        var PAGE = 0



    }
}