package com.vs.roomkotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class MRoomDatabase : RoomDatabase() {
    abstract fun nameDao(): RoomDao

    companion object {
        private var instance: MRoomDatabase? = null
        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    populateDb(instance)
                }
            }
        }

        @Synchronized
        fun getInstance(context: Context): MRoomDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MRoomDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }
    }
}

private suspend fun populateDb(db: MRoomDatabase?) {
    withContext(Dispatchers.IO) {
        db!!.nameDao().insert(RoomEntity("0", "test"))
    }
}