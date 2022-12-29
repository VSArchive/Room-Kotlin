package com.vs.roomkotlin.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyRoomDao {
    @Insert
    fun insert(name: MyRoomEntity?)

    @Update
    fun update(name: MyRoomEntity?)

    @Delete
    fun delete(name: MyRoomEntity?)

    @Query("DELETE FROM names_table")
    fun deleteAll()

    @get:Query("SELECT * from names_table ORDER BY id ASC")
    val alphabetizedWords: LiveData<List<MyRoomEntity>?>?
}