package com.vs.roomkotlin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class RoomEntity(
    @field:ColumnInfo(name = "first_name") val firstName: String, @field:ColumnInfo(
        name = "last_name"
    ) val lastName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}