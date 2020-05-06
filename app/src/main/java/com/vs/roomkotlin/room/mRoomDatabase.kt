package com.vs.roomkotlin.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class mRoomDatabase : RoomDatabase() {
    abstract fun nameDao(): RoomDao
    private class PopulateDbAsyncTask(db: mRoomDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val noteDao: RoomDao = db!!.nameDao()
        protected override fun doInBackground(vararg params: Void?): Void? {
            noteDao.insert(RoomEntity("1", "1"))
            noteDao.insert(RoomEntity("2", "2"))
            return null
        }

    }

    companion object {
        private var instance: mRoomDatabase? = null
        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }

        @Synchronized
        fun getInstance(context: Context): mRoomDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    mRoomDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }
    }
}