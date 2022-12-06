package com.example.adminpanel.data.presenter_impl

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminpanel.R
import com.example.adminpanel.domain.presenter.LoginContract
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception


@RunWith(MockitoJUnitRunner::class)
class LoginPresenterImplTest {

    @Mock
    private lateinit var view: LoginContract.View

    @Mock
    private lateinit var presenter: LoginPresenterImpl

    @Before
    fun setUp(){
       presenter = LoginPresenterImpl(view)
    }



   /* @Test
    fun test_exit_to_system_failure(){
        presenter.exitAccount("", "")
        verify(view, times(2)).setTextViewError(R.string.message_empty_fields)
    }*/
}