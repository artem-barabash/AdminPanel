package com.example.adminpanel.data.utilities.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adminpanel.domain.entities.*

@Database(
    entities = [Operation::class, Person::class, Question::class, Variant::class, Answer::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun adminDao(): AdminDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .createFromAsset("database/admin_data.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}