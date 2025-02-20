package io.github.donghune.calendar.presentation.main

import java.time.LocalDate

data class CalendarUiState(
    val currentLocalDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val recordList: List<DayRecordUiState> = emptyList()
)

data class DayRecordUiState(
    val localDate: LocalDate,
    val title: String,
    val description: String,
    val emotion: EmotionType,
    val isVisible: Boolean,
    val isSelected: Boolean,
) {
    companion object {
        fun empty(localDate: LocalDate): DayRecordUiState {
            return DayRecordUiState(
                localDate = localDate,
                title = "",
                description = "",
                emotion = EmotionType.EMPTY,
                isVisible = false,
                isSelected = false
            )
        }
    }
}

enum class EmotionType {
    HAPPY,
    SAD,
    ANGRY,
    EMPTY
}