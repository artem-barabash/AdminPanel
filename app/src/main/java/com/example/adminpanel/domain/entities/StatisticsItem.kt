package com.example.adminpanel.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

data class StatisticsItem(
    @ColumnInfo(name = "text")
    val textAnswer:String,
    @ColumnInfo(name = "quality")
    val quality:Int
) {
}