package com.example.adminpanel.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.sql.Timestamp

@Entity(tableName = "operations")
data class Operation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NotNull @ColumnInfo(name = "send_number")
    val send: String,
    @NotNull @ColumnInfo(name = "receive_number")
    val receive: String,
    @NotNull @ColumnInfo(name = "time_operation")
    val time: String,
    @NotNull @ColumnInfo(name = "sum_operation")
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