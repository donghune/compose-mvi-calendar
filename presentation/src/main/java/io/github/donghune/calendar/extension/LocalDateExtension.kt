package io.github.donghune.calendar.extension

import java.time.LocalDate

val LocalDate.totalMonths: Int
    get() = year * 12 + monthValue

val Int.toLocalDate: LocalDate
    get() {
        var years = this / 12
        var months = this % 12

        // 0개월이면 12개월로 변경하고 연도를 하나 감소
        if (months == 0) {
            months = 12
            years -= 1
        }

        return LocalDate.of(years, months, 1)
    }