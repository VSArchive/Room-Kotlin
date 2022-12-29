package com.vs.roomkotlin.room

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class MyRoomRepository(application: Application) {
    private val nameDao: MyRoomDao?
    val allNames: LiveData<List<MyRoomEntity>?>?
    fun insert(name: MyRoomEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            insertItem(nameDao, name)
        }
    }

    fun update(name: MyRoomEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            updateItem(nameDao, name)
        }
    }

    fun delete(name: MyRoomEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteItem(nameDao, name)
        }
    }

    fun deleteAllNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            deleteAllItems(nameDao)
        }
    }

    private suspend fun insertItem(itemDao: MyRoomDao?, vararg params: MyRoomEntity?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.insert(params[0])
        }
        return null
    }

    private suspend fun updateItem(itemDao: MyRoomDao?, vararg params: MyRoomEntity?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.update(params[0])
        }
        return null
    }

    private suspend fun deleteItem(itemDao: MyRoomDao?, vararg params: MyRoomEntity?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.delete(params[0])
        }
        return null
    }

    private suspend fun deleteAllItems(itemDao: MyRoomDao?): Void? {
        withContext(Dispatchers.IO) {
            itemDao!!.deleteAll()
        }
        return null
    }

    init {
        val database: MRoomDatabase? = MRoomDatabase.getInstance(application)
        nameDao = database?.nameDao()
        allNames = nameDao?.alphabetizedWords
    }
}