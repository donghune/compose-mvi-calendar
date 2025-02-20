package io.github.donghune.domain.usecase

import io.github.donghune.domain.entity.DayRecord
import io.github.donghune.domain.repository.DayRecordRepository
import javax.inject.Inject

class SaveDayRecordUseCase @Inject constructor(
    private val repository: DayRecordRepository
) {
    suspend fun invoke(dayRecord: DayRecord) {
        repository.saveDayRecord(dayRecord)
    }
}

