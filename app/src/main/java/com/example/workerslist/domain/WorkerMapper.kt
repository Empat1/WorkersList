package com.example.workerslist.domain

import androidx.work.Worker
import com.example.workerslist.data.database.WorkerDb
import javax.inject.Inject


class WorkerMapper @Inject constructor(){
    fun mapDtoToModel(dto: WorkerDb) =
        WorkerModel(dto.id, dto.fio, dto.birthday, dto.jobRole)

    fun mapModelToDto(model: WorkerModel) =
        WorkerDb(model.id, model.fio, model.birthday, model.jobRole)
}