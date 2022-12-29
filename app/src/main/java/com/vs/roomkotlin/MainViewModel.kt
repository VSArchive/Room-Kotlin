package com.vs.roomkotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.vs.roomkotlin.room.MyRoomEntity
import com.vs.roomkotlin.room.MyRoomRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MyRoomRepository = MyRoomRepository(application)
    val allName: LiveData<List<MyRoomEntity>?>? = repository.allNames
    fun insert(note: MyRoomEntity?) {
        repository.insert(note)
    }

    fun update(note: MyRoomEntity?) {
        repository.update(note)
    }

    fun delete(note: MyRoomEntity?) {
        repository.delete(note)
    }

    fun deleteAllNames() {
        repository.deleteAllNotes()
    }

}