package io.github.donghune.domain.entity

import java.time.LocalDate

data class DayRecord(
    val localDate: LocalDate,
    val title: String,
    val description: String,
    val emotion: Emotion,
) {
    enum class Emotion {
        HAPPY,
        SAD,
        ANGRY,
        EMPTY
    }
}