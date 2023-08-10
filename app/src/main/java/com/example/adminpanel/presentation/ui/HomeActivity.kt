package com.example.adminpanel.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.HomeFragmentManagerPresenterImpl
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
import com.example.adminpanel.presentation.ui.fragment.TransferFragment
import com.example.adminpanel.presentation.ui.fragment.VotingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), HomeFragmentManagerContract.View{

    private lateinit var navigationBar: BottomNavigationView

    private lateinit var homeFragmentManagerPresenter: HomeFragmentManagerPresenterImpl

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var swipeLayout:SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        swipeLayout = findViewById(R.id.swipe_refresh)

        sharedPreferences = this.getSharedPreferences(TEMP_USER_DATA, MODE_PRIVATE)

        getUserData()

        homeFragmentManagerPresenter = HomeFragmentManagerPresenterImpl(this)


        navigationBar = findViewById(R.id.bottomNavigationBar)

        replaceFragment(HomeFragment())

        navigationBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.itemHome -> replaceFragment(HomeFragment())
                R.id.itemTransactions -> replaceFragment(TransactionFragment())
                R.id.itemVotings -> replaceFragment(VotingsFragment())
            }

            return@setOnItemSelectedListener true
        }


        swipeLayout.setOnRefreshListener {
            val i = Intent(this@HomeActivity, HomeActivity::class.java)
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
        }
    }

    private fun getUserData() {


        val nBalance: String? = sharedPreferences.getString(BALANCE, "")

        //val balance: Double = if(nBalance != null && nBalance != "0"){
            //nBalance.toDouble()
        //}else{
           // 0.0
        //}

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
            0.0
        )

        UserFactory(user)

    }

    private fun replaceFragment(selected: Fragment){
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fl_layout, selected)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack(null)


        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigationBar.selectedItemId =  R.id.itemHome
    }

}