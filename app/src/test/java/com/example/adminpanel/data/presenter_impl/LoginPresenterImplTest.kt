package com.example.adminpanel.data.presenter_impl


import android.content.Context
import com.example.adminpanel.domain.presenter.LoginContract
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mockStatic
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginPresenterImplTest {
    @JvmField @Rule
    var mockitoRule = MockitoJUnit.rule()


    private lateinit var loginPresenterImpl: LoginPresenterImpl

    @Mock
    private lateinit var view: LoginContract.View

    @Mock
    private lateinit var mockApplicationContext: Context

    private lateinit var successTask: Task<AuthResult>
    private lateinit var failureTask: Task<AuthResult>

    @Mock
    private lateinit var mAuth: FirebaseAuth


    private var mockedDatabaseReference: DatabaseReference? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        loginPresenterImpl = LoginPresenterImpl(view)


        mockedDatabaseReference = Mockito.mock(DatabaseReference::class.java)

        val mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase::class.java)
        `when`(mockedFirebaseDatabase.reference).thenReturn(mockedDatabaseReference)

        mockStatic(FirebaseDatabase::class.java)
        `when`(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase)


    }

    @After
    fun tearDown() {
        //Dispatchers.resetMain()
    }

    @Test
    fun test(){

        //MockitoAnnotations.initMocks(this)
        //Mockito.`when`(loginPresenterImpl.isOnline(mockApplicationContext)).thenReturn(false)


        //Mockito.`when`(loginPresenterImpl.exitAccount("admin@gmail.com", "Artem-1234"))

        `when`(mockedDatabaseReference!!.child(anyString())).thenReturn(mockedDatabaseReference)

        doAnswer { invocation ->
            val valueEventListener = invocation.arguments[0] as ValueEventListener
            val mockedDataSnapshot = Mockito.mock(DataSnapshot::class.java)
            //when(mockedDataSnapshot.getValue(User.class)).thenReturn(testOrMockedUser)
            valueEventListener.onDataChange(mockedDataSnapshot)
            //valueEventListener.onCancelled(...);
            null
        }.`when`(mockedDatabaseReference)?.addListenerForSingleValueEvent(
            any(
                ValueEventListener::class.java
            )
        )

        loginPresenterImpl.exitAccount("admin@gmail.com", "Artem-1234")


    }
}