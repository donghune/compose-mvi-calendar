package io.github.donghune.calendar.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.donghune.calendar.mapper.toUiEntity
import io.github.donghune.domain.usecase.GetDayRecordListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getDayRecordUseCase: GetDayRecordListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState

    fun updateCurrentLocalDate(date: LocalDate) {
        viewModelScope.launch {
            val yearMonth = YearMonth.from(date)
            _uiState.update {
                it.copy(
                    currentLocalDate = date,
                    recordList = generateRecordList(yearMonth)
                )
            }
        }
    }

    fun onClickDayRecord(dayRecordUiState: DayRecordUiState) {
        _uiState.update {
            it.copy(
                recordList = it.recordList.map { record ->
                    record.copy(isSelected = record.localDate == dayRecordUiState.localDate)
                }
            )
        }
    }

    private suspend fun generateRecordList(yearMonth: YearMonth): List<DayRecordUiState> {
        val recordList = mutableMapOf<LocalDate, DayRecordUiState>()

        val firstDay = LocalDate.of(yearMonth.year, yearMonth.month, 1)
        val lastDay = firstDay.plusMonths(1).minusDays(1)

        // 공백 사이즈 계산
        val firstDayOfWeek = firstDay.dayOfWeek.value
        for (i in 0 until firstDayOfWeek) {
            val localDate = firstDay.minusDays((firstDayOfWeek - i).toLong())
            recordList[localDate] = DayRecordUiState.empty(localDate)
        }

        // 날짜 데이터 생성
        (firstDay.dayOfMonth..lastDay.dayOfMonth)
            .map { firstDay.withDayOfMonth(it) }
            .associateWith { DayRecordUiState.empty(it).copy(isVisible = true) }
            .forEach { (localDate, uiState) -> recordList[localDate] = uiState }

        // 기록된 날짜 데이터 반영
        getDayRecordUseCase.invoke(YearMonth.of(yearMonth.year, yearMonth.month))
            .forEach { recordList[it.localDate] = it.toUiEntity() }

        return recordList.values.toList().also { Log.d(TAG, "generateRecordList: $it") }
    }

    companion object {
        private const val TAG = "CalendarViewModel"
    }
}

