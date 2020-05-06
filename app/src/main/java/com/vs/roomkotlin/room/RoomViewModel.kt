package com.vs.roomkotlin.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RoomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RoomRepository = RoomRepository(application)
    public val allNotes: LiveData<List<RoomEntity?>?>?
    fun insert(note: RoomEntity?) {
        repository.insert(note)
    }

    fun update(note: RoomEntity?) {
        repository.update(note)
    }

    fun delete(note: RoomEntity?) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    init {
        allNotes = repository.allNotes
    }
}