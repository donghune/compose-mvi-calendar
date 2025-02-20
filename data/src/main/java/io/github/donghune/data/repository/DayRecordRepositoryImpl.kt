package io.github.donghune.data.repository

import io.github.donghune.data.datasource.CalendarDataSource
import io.github.donghune.data.mapper.toDomain
import io.github.donghune.data.mapper.toEntity
import io.github.donghune.domain.entity.DayRecord
import io.github.donghune.domain.repository.DayRecordRepository
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class DayRecordRepositoryImpl @Inject constructor(
    private val calendarDataSource: CalendarDataSource
) : DayRecordRepository {

    override suspend fun getDayRecordList(yearMonth: YearMonth): List<DayRecord> {
        return calendarDataSource.getDayRecordListByYearMonth(yearMonth).map { it.toDomain() }
    }

    override suspend fun getDayRecordByLocalDate(localDate: LocalDate): DayRecord? {
        return calendarDataSource.getDayRecordByLocalDate(localDate)?.toDomain()
    }

    override suspend fun saveDayRecord(dayRecord: DayRecord) {
        calendarDataSource.saveDayRecord(dayRecord.toEntity())
    }
}

