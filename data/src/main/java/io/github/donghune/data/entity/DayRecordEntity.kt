package io.github.donghune.data.entity

import io.github.donghune.data.serializer.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class DayRecordEntity(
    @Serializable(with = LocalDateSerializer::class)
    val localDate: LocalDate,
    val title: String,
    val description: String,
    val emotion: Emotion
) {
    @Serializable
    enum class Emotion {
        HAPPY, SAD, ANGRY, EMPTY
    }
}