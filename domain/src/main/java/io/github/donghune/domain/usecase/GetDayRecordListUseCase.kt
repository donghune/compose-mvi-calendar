package io.github.donghune.domain.usecase

import io.github.donghune.domain.entity.DayRecord
import io.github.donghune.domain.repository.DayRecordRepository
import java.time.YearMonth
import javax.inject.Inject

class GetDayRecordListUseCase @Inject constructor(
    private val repository: DayRecordRepository
) {
    suspend fun invoke(yearMonth: YearMonth): List<DayRecord> {
        return repository.getDayRecordList(yearMonth)
    }
}