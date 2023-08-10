package com.example.adminpanel.domain.use_cases

import com.example.adminpanel.domain.entities.User

class UserFactory(private val user: User) {
    init {
        ACCOUNT = user
    }

    companion object{
        lateinit var ACCOUNT:User
    }
}