package com.example.adminpanel.data.presenter_impl

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.adminpanel.data.utilities.VotingFirebaseManager
import com.example.adminpanel.data.utilities.room.AdminDao
import com.example.adminpanel.domain.entities.Answer
import com.example.adminpanel.domain.entities.StatisticsItem
import com.example.adminpanel.domain.entities.Variant
import com.example.adminpanel.domain.presenter.VotingPageFragmentContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.CoroutineContext

class VotingPageFragmentPresenterImpl(
    view: VotingPageFragmentContract.View,
    private val adminDao: AdminDao,
    private val uiContext: CoroutineContext = Dispatchers.Main): VotingPageFragmentContract.Presenter, CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    private val fireBaseManager = VotingFirebaseManager()

    init {

    }

    override fun getAllVariants(numberQuestion: String): Flow<List<Variant>>
    = adminDao.selectAllVariantsOnQuestion(numberQuestion)

    override fun getAllStatisticsOnQuestion(number: String): LiveData<List<StatisticsItem>>
    = adminDao.getStatisticsQuestion(number)

    override fun getVotingQualityOfQuestion(number: String): Flow<Int> =
        adminDao.getGetQualityOfVoting(number)



    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseDate(time: String): String? {
        var time = time
        val pattern = "yyyy-MM-dd HH:mm:ss.SSS"
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
        //выровняли дату, 0 в конце если после точки 2 числа
        time = if (time.substring(time.indexOf('.') + 1).length != 3) time + "0" else time
        val localDateTime: LocalDateTime = LocalDateTime.parse(time, formatter)
        val month = java.lang.String.format(
            "%s%s", localDateTime.month.toString().substring(0, 1),
            localDateTime.month.toString().substring(1, 3).lowercase(Locale.ROOT)
        )
        return "${localDateTime.hour}:${localDateTime.minute}:${localDateTime.second} " +
                "  ${localDateTime.dayOfMonth}.${localDateTime.monthValue}.${localDateTime.year}"
    }

    override fun closeQuestion(number: String) {
        fireBaseManager.closeQuestion(number)
    }

}