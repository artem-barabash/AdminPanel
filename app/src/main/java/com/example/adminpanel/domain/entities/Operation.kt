package com.example.adminpanel.domain.entities

import org.jetbrains.annotations.NotNull
import java.sql.Timestamp

data class Operation(
    val id: Int,
    val send: String,
    val receive: String,
    val time: String,
    val sum: Double
):Comparable<Operation> {
    override fun compareTo(other: Operation): Int {
        val tm1 = Timestamp.valueOf(correctDateAndTime(other.time))
        val tm2 = Timestamp.valueOf(correctDateAndTime(this.time))

        return tm1.compareTo(tm2)
    }

    fun correctDateAndTime(time: String): String? {
        return if (time.substring(time.indexOf('.') + 1).length != 3) time + "0" else time
    }

}