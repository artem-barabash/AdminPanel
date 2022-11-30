package com.example.adminpanel

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminpanel.presentation.ui.LoginActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.endsWith
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Before
    fun setup(){
        launchActivity<LoginActivity>()
    }


    @Test
    fun entered_in_account_empty_fields(){
        onView(withId(R.id.buttonLogin)).perform(click())

        onView(withText("Fields can not be empty!")).check(matches(isDisplayed()))
    }

    @Test
    fun entered_in_account_no_correctly(){
        onView(withId(R.id.editTextEmail)).perform(replaceText("admin@gmail.com"))

        onView(withId(R.id.editTextPassword)).perform(replaceText("1234"))

        onView(withId(R.id.buttonLogin)).perform(click())

        //Thread.sleep(500)

        onView(withId(R.id.textViewOnError))
            .check(matches(withText(CoreMatchers.containsString("Login or password is not correctly!"))))
    }
}