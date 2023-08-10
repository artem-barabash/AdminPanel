package com.example.adminpanel.data.utilities.room

import android.app.Application

class UserDataApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}