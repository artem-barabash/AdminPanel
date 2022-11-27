package com.example.adminpanel.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.parceler.Parcel
import java.sql.Timestamp
@Parcel
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
):Comparable<Operation>, Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    ) {
    }

    override fun compareTo(other: Operation): Int {
        val tm1 = Timestamp.valueOf(correctDateAndTime(other.time))
        val tm2 = Timestamp.valueOf(correctDateAndTime(this.time))

        return tm1.compareTo(tm2)
    }

    fun correctDateAndTime(time: String): String? {
        return if (time.substring(time.indexOf('.') + 1).length != 3) time + "0" else time
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(send)
        parcel.writeString(receive)
        parcel.writeString(time)
        parcel.writeDouble(sum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Operation> {
        override fun createFromParcel(parcel: android.os.Parcel): Operation {
            return Operation(parcel)
        }

        override fun newArray(size: Int): Array<Operation?> {
            return arrayOfNulls(size)
        }
    }

}