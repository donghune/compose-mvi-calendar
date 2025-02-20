package io.github.donghune.calendar.mapper

import io.github.donghune.calendar.presentation.main.DayRecordUiState
import io.github.donghune.calendar.presentation.main.EmotionType
import io.github.donghune.domain.entity.DayRecord

fun DayRecord.toUiEntity(): DayRecordUiState {
    return DayRecordUiState(
        localDate = localDate,
        title = title,
        description = description,
        emotion = when (emotion) {
            DayRecord.Emotion.HAPPY -> EmotionType.HAPPY
            DayRecord.Emotion.SAD -> EmotionType.SAD
            DayRecord.Emotion.ANGRY -> EmotionType.ANGRY
            DayRecord.Emotion.EMPTY -> EmotionType.EMPTY
        },
        isVisible = true,
        isSelected = false
    )
}