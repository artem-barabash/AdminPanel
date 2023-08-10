package com.example.adminpanel.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.parceler.Parcel

@Parcel
@Entity(tableName = "variants")
data class Variant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NotNull
    @ColumnInfo(name = "number_question")
    val numberQuestion: String,
    @NotNull
    @ColumnInfo(name = "text")
    val text:String
):Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(numberQuestion)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Variant> {
        override fun createFromParcel(parcel: android.os.Parcel): Variant {
            return Variant(parcel)
        }

        override fun newArray(size: Int): Array<Variant?> {
            return arrayOfNulls(size)
        }
    }
}