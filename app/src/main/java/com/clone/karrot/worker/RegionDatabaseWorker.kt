package com.clone.karrot.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.clone.karrot.data.AppDatabase
import com.clone.karrot.data.Region
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope

class RegionDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope{
        try {
            applicationContext.assets.open("regions.json").use { inputStream ->
                com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                    val regionType = object: TypeToken<List<Region>>(){}.type
                    val regionList: List<Region> = Gson().fromJson(jsonReader, regionType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.regionDao().insertRegions(regionList)

                    Result.success()
                }
            }
        } catch (e: Exception) {
            Result.failure()
        }

    }
}