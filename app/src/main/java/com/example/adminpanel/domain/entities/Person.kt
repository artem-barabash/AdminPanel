package com.example.adminpanel.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.parceler.Parcel


@Parcel
@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NotNull @ColumnInfo(name = "number_user")
    val number: String,
    @NotNull @ColumnInfo(name = "first_name")
    val firstName: String,
    @NotNull @ColumnInfo(name = "last_name")
    val lastName: String,
    @NotNull @ColumnInfo(name = "balance")
    val balance: Double
):Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(number)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeDouble(balance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: android.os.Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}