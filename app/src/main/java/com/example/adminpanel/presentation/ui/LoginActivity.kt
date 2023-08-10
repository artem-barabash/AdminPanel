package com.example.adminpanel.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.LoginPresenterImpl
import com.example.adminpanel.domain.entities.User
import com.example.adminpanel.domain.presenter.LoginContract
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity(), LoginContract.View {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var textError:TextView

    lateinit var presenterImpl:LoginPresenterImpl

    lateinit var sharedPreferencesLogin: SharedPreferences

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenterImpl = LoginPresenterImpl(this)

        databaseReference = FirebaseDatabase.getInstance().reference

        sharedPreferencesLogin = this.getSharedPreferences(TEMP_USER_DATA, MODE_PRIVATE)

        editEmail = findViewById(R.id.editTextEmail)
        editPassword = findViewById(R.id.editTextPassword)

        buttonLogin = findViewById(R.id.buttonLogin)

        textError = findViewById(R.id.textViewOnError)

        buttonLogin.setOnClickListener{
            if(presenterImpl.isOnline(this)){
                presenterImpl.exitAccount(editEmail.text.toString(), editPassword.text.toString())
            }else{
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun enterToHomeActivity() {
       //lifecycleScope.coroutineContext.let {
            //retrieveUserDataFromFireBase(editEmail.text.toString(), editPassword.text.toString())

            //Thread.sleep(2000)

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        //}

    }

    override fun retrieveUserDataFromFireBase(email: String, password: String) {
        val dataReferenceUser: DatabaseReference = databaseReference.child("User")

        val query: Query = dataReferenceUser.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("WrongConstant", "ApplySharedPref")
            override  fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {

                    val nBalance: Any? = it.child("balance").value

                    val balance: Double = if((nBalance != null) && (nBalance.toString() != "0")){
                        if(nBalance is Long){
                            nBalance.toDouble()
                        }
                        else {
                            nBalance as Double}
                    }else 0.0


                    val key = it.key.toString()
                    val user = User(
                        key,
                        password,
                        it.child("firstName").value.toString(),
                        it.child("lastName").value.toString(),
                        it.child("phone").value.toString(),
                        email,
                        it.child("birthday").value.toString(),
                        it.child("gender").value.toString(),
                        it.child("homeAddress").value.toString(),
                        0.0
                    )

                    println("user= $user")


                    lifecycleScope.coroutineContext.let {
                        sharedPreferencesLogin.edit().clear().apply()

                        saveDataSharedPreferences(user)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })

    }



    fun saveDataSharedPreferences(user: User){
        val editor = sharedPreferencesLogin.edit()

        editor.putString(FIRST_NAME, user.firstName)
        editor.putString(LAST_NAME, user.lastName)
        editor.putString(PHONE, user.phone)
        editor.putString(EMAIL, user.email)
        editor.putString(BIRTHDAY, user.birthday)
        editor.putString(GENDER, user.gender)
        editor.putString(HOME_ADDRESS, user.homeAddress)

        editor.putString(BALANCE, user.balance.toString())

        editor.putString(PASSWORD, user.password)
        editor.putString(NUMBER_KEY, user.number)

        editor.apply()
    }

    override fun setTextViewError(str: Int) {
        textError.setText(str)
    }

    override fun clearFields() {
        editEmail.text.clear()
        editPassword.text.clear()
    }


    companion object{
        const val TEMP_USER_DATA : String = "MySharedPref"

        const val FIRST_NAME: String = "firstNameU"
        const val LAST_NAME: String = "lastNameU"
        const val PHONE: String = "phoneU"
        const val EMAIL: String = "emailU"
        const val BIRTHDAY: String = "birthdayU"
        const val GENDER: String = "genderU"
        const val HOME_ADDRESS: String = "homeAddressU"
        const val BALANCE: String = "balanceU"
        const val PASSWORD: String = "passwordU"
        const val NUMBER_KEY: String = "numberKeyU"
    }

}