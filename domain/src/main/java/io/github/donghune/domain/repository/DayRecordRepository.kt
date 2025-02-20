package io.github.donghune.domain.repository

import io.github.donghune.domain.entity.DayRecord
import java.time.LocalDate
import java.time.YearMonth

interface DayRecordRepository {
    suspend fun getDayRecordList(yearMonth: YearMonth): List<DayRecord>

    suspend fun getDayRecordByLocalDate(localDate: LocalDate): DayRecord?

    suspend fun saveDayRecord(dayRecord: DayRecord)
}