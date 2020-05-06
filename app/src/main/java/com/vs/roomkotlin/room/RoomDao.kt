package com.vs.roomkotlin.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(name: RoomEntity?)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(name: RoomEntity?)

    @Delete
    fun delete(name: RoomEntity?)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @get:Query("SELECT * from word_table ORDER BY first_name ASC")
    val alphabetizedWords: LiveData<List<RoomEntity?>?>?
}