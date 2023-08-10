package com.example.adminpanel.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.parceler.Parcel

@Parcel
@Entity(tableName = "answers")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NotNull
    @ColumnInfo(name = "user_number")
    var userNumberKey: String,
    @NotNull
    @ColumnInfo(name = "number_question")
    var numberQuestion: String,
    @NotNull
    @ColumnInfo(name = "text_answer")
    var textAnswer: String,
    @NotNull
    @ColumnInfo(name = "date_time")
    var dateTime: String): Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(userNumberKey)
        parcel.writeString(numberQuestion)
        parcel.writeString(textAnswer)
        parcel.writeString(dateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Answer> {
        override fun createFromParcel(parcel: android.os.Parcel): Answer {
            return Answer(parcel)
        }

        override fun newArray(size: Int): Array<Answer?> {
            return arrayOfNulls(size)
        }
    }

}