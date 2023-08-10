package com.example.adminpanel.domain.entities

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.parceler.Parcel

@Parcel
@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NotNull
    @ColumnInfo(name = "number")
    val number: String,
    @NotNull
    @ColumnInfo(name = "text")
    val textQuestion: String,
    @ColumnInfo(name = "isOpen")
    val isOpen:Boolean,
    @NotNull
    @ColumnInfo(name = "time")
    val time: String): Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: android.os.Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeString(number)
        parcel.writeString(textQuestion)
        parcel.writeBoolean(isOpen)
        parcel.writeString(time)
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: android.os.Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}