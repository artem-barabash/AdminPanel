package com.example.adminpanel.data.utilities.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.adminpanel.domain.entities.*
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllQuestions(listQuestion: List<Question>)

    @Query("SELECT * FROM questions")
    fun selectAllQuestions(): Flow<List<Question>>

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllVariants(listQuestion: List<Variant>)

    @Query("SELECT * FROM variants")
    fun selectAllVariants(): Flow<List<Variant>>

    @Query("SELECT * FROM variants where number_question = :question")
    fun selectAllVariantsOnQuestion(question: String): Flow<List<Variant>>

    @Query("DELETE FROM variants")
    fun deleteAllVariants()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllAnswers(listAnswers: List<Answer>)

    @Query("SELECT * FROM answers WHERE number_question =:number")
    fun selectAllAnswers(number: String):Flow<List<Answer>>

    @Query("DELETE FROM answers")
    fun deleteAllAnswers()

    @Query("SELECT text, COUNT(text_answer) AS quality FROM variants v LEFT JOIN answers a ON a.text_answer = text " +
            "WHERE v.number_question =:number GROUP BY v.id")
    fun getStatisticsQuestion(number: String): LiveData<List<StatisticsItem>>

    @Query("SELECT COUNT(*) FROM answers WHERE number_question =:number")
    fun getGetQualityOfVoting(number: String): Flow<Int>


    /*SELECT text, COUNT(text_answer) AS occurrences
    FROM variants WHERE number_question = 'YOUR NUMBER'
    LEFT JOIN answers ON text_answer = text
    group by text_answer*/

/*SELECT text FROM variants where number_question = '5ae306fd-0dde-4d02-98df-401928fbc65f'
*/


}