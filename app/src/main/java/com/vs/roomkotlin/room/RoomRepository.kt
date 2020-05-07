package com.vs.roomkotlin.room

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RoomRepository(application: Application) {
    private val nameDao: RoomDao?
    val allNotes: LiveData<List<RoomEntity?>?>?
    fun insert(name: RoomEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            insertItem(nameDao, name)
        }
    }

    fun update(name: RoomEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            updateItem(nameDao, name)
        }
    }

    fun delete(name: RoomEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteItem(nameDao, name)
        }
    }

    fun deleteAllNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            deleteAllItems(nameDao)
        }
    }

    private suspend fun insertItem(itemDao: RoomDao?, vararg params: RoomEntity?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.insert(params[0])
        }
        return null
    }

    private suspend fun updateItem(itemDao: RoomDao?, vararg params: RoomEntity?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.update(params[0])
        }
        return null
    }

    private suspend fun deleteItem(itemDao: RoomDao?, vararg params: RoomEntity?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.delete(params[0])
        }
        return null
    }

    private suspend fun deleteAllItems(itemDao: RoomDao?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.deleteAll()
        }
        return null
    }

    init {
        val database: MRoomDatabase? = MRoomDatabase.getInstance(application)
        nameDao = database?.nameDao()
        allNotes = nameDao?.alphabetizedWords
    }
}