package com.vs.roomkotlin.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "names_table")
class MyRoomEntity(
    val firstName: String,
    val lastName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}