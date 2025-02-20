package io.github.donghune.domain.usecase

import io.github.donghune.domain.entity.DayRecord
import io.github.donghune.domain.repository.DayRecordRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDayRecordUseCase @Inject constructor(
    private val repository: DayRecordRepository
) {
    suspend fun invoke(localDate: LocalDate): DayRecord? {
        return repository.getDayRecordByLocalDate(localDate)
    }
}