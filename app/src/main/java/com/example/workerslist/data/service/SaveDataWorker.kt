package com.example.workerslist.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.workerslist.R
import com.example.workerslist.data.FileDataManager
import com.example.workerslist.data.JsonDataManager
import com.example.workerslist.data.database.WorkersDao
import com.example.workerslist.domain.Workers
import com.example.workerslist.presentation.notification.NotificationBuilder
import javax.inject.Inject

class SaveDataWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val fileDataManager: FileDataManager,
    private val jsonDataManager: JsonDataManager,
    private val notificationBuilder: NotificationBuilder,
    private val workersDao: WorkersDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo(context.getString(R.string.wait_save_file)))
        val workers =
            workersDao.getAllWorker().filter { it.fio.length > 5 && it.jobRole == "Администратор" }

        val workersJson = jsonDataManager.exportToJSON(Workers(workers))

        fileDataManager.saveFile(workersJson)

        notificationBuilder.sendMessage(notificationBuilder.standardNotification())
        return Result.success()
    }

    class Factory @Inject constructor(
        private val fileDataManager: FileDataManager,
        private val jsonDataManager: JsonDataManager,
        private val notificationBuilder: NotificationBuilder,
        private val workersDao: WorkersDao
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return SaveDataWorker(
                context,
                workerParameters,
                fileDataManager,
                jsonDataManager,
                notificationBuilder,
                workersDao
            )
        }
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val notification = notificationBuilder.foregroundInfoNotification(progress)
            .build()

        return ForegroundInfo(2, notification)
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<SaveDataWorker>().build()
        }
    }
}