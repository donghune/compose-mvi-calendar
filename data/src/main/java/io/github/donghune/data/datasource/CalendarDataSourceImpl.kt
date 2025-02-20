package io.github.donghune.data.datasource

import android.content.SharedPreferences
import io.github.donghune.data.entity.DayRecordEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CalendarDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : CalendarDataSource {

    override fun getDayRecordListByYearMonth(yearMonth: YearMonth): List<DayRecordEntity> {
        try {
            val listStr = sharedPreferences.getString(
                yearMonth.toString(), null
            ) ?: return emptyList()
            return Json.decodeFromString<List<DayRecordEntity>>(listStr)
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override fun getDayRecordByLocalDate(localDate: LocalDate): DayRecordEntity? {
        try {
            val yearMonth = YearMonth.from(localDate)
            val listStr = sharedPreferences.getString(
                yearMonth.toString(), null
            ) ?: return null
            val list = Json.decodeFromString<List<DayRecordEntity>>(listStr)
            return list.find { it.localDate == localDate }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun saveDayRecord(dayRecord: DayRecordEntity) {
        try {
            val yearMonth = YearMonth.from(dayRecord.localDate)
            val listStr = sharedPreferences.getString(
                yearMonth.toString(), null
            ) ?: Json.encodeToString(emptyList<DayRecordEntity>())
            val list = Json.decodeFromString<List<DayRecordEntity>>(listStr).toMutableList()
            list.add(dayRecord)
            sharedPreferences.edit().putString(yearMonth.toString(), Json.encodeToString(list))
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

