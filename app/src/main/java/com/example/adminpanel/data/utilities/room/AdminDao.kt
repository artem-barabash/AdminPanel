package com.example.adminpanel.data.utilities.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.adminpanel.domain.entities.Operation
import com.example.adminpanel.domain.entities.Person
import kotlinx.coroutines.flow.Flow


@Dao
interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllPersons(listPerson: List<Person>)

    @Query("DELETE FROM persons")
    fun deleteAllPersons()

    @Query("SELECT * FROM persons")
    fun selectAllPersons(): Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllOperations(listOperation: List<Operation>)

    @Query("SELECT * FROM operations")
    fun selectAllOperations(): Flow<List<Operation>>

    @Query("DELETE FROM operations")
    fun deleteAllOperations()
}