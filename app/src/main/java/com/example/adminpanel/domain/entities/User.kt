package com.example.adminpanel.domain.entities

data class User(
    val number: String,
    val password: String,
    val firstName: String,
    val lastName : String,
    val phone : String,
    val email: String,
    val birthday: String,
    val gender: String,
    val homeAddress: String,
    var balance: Double
)