package io.github.donghune.data.datasource

import io.github.donghune.data.entity.DayRecordEntity
import java.time.LocalDate
import java.time.YearMonth

interface CalendarDataSource {
    fun getDayRecordListByYearMonth(yearMonth: YearMonth): List<DayRecordEntity>
    fun getDayRecordByLocalDate(localDate: LocalDate): DayRecordEntity?
    fun saveDayRecord(dayRecord: DayRecordEntity)
}