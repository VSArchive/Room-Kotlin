package com.vs.roomkotlin.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

internal class RoomRepository(application: Application) {
    private val nameDao: RoomDao?
    val allNotes: LiveData<List<RoomEntity?>?>?
    fun insert(name: RoomEntity?) {
        InsertNoteAsyncTask(nameDao).execute(name)
    }

    fun update(name: RoomEntity?) {
        UpdateNoteAsyncTask(nameDao).execute(name)
    }

    fun delete(name: RoomEntity?) {
        DeleteNoteAsyncTask(nameDao).execute(name)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(nameDao).execute()
    }

    private class InsertNoteAsyncTask(private val nameDao: RoomDao?) :
        AsyncTask<RoomEntity?, Void?, Void?>() {
        override fun doInBackground(vararg params: RoomEntity?): Void? {
            nameDao!!.insert(params[0])
            return null
        }

    }

    private class UpdateNoteAsyncTask(private val nameDao: RoomDao?) :
        AsyncTask<RoomEntity?, Void?, Void?>() {
        override fun doInBackground(vararg params: RoomEntity?): Void? {
            nameDao!!.update(params[0])
            return null
        }

    }

    private class DeleteNoteAsyncTask(private val nameDao: RoomDao?) :
        AsyncTask<RoomEntity?, Void?, Void?>() {
        override fun doInBackground(vararg params: RoomEntity?): Void? {
            nameDao!!.delete(params[0])
            return null
        }

    }

    private class DeleteAllNotesAsyncTask(private val nameDao: RoomDao?) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            nameDao!!.deleteAll()
            return null
        }

    }

    init {
        val database: mRoomDatabase? = mRoomDatabase.getInstance(application)
        nameDao = database?.nameDao()
        allNotes = nameDao?.alphabetizedWords
    }
}