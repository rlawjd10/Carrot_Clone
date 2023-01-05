package com.clone.karrot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.clone.karrot.worker.RegionDatabaseWorker

//database 객체를 반환하거나 삭제할 수 있도록 getInstance()와 destroyInstance()메소드를 생성
@Database(entities = arrayOf(Region::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun regionDao(): RegionDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "carrot_clone")
                .addCallback(object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<RegionDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                }).build()
        }

        fun destroyInstance() {
            instance = null
        }

    }
}