package io.github.donghune.data.mapper

import io.github.donghune.data.entity.DayRecordEntity
import io.github.donghune.domain.entity.DayRecord

fun DayRecord.toEntity() = DayRecordEntity(
    localDate = localDate,
    title = title,
    description = description,
    emotion = DayRecordEntity.Emotion.valueOf(emotion.name)
)

fun DayRecordEntity.toDomain() = DayRecord(
    localDate = localDate,
    title = title,
    description = description,
    emotion = DayRecord.Emotion.valueOf(emotion.name)
)