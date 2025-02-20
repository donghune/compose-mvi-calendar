package io.github.donghune.calendar.presentation.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import java.time.LocalDate

const val DAY_RECORD_DETAIL_ROUTE = "dayRecordDetailRoute"

fun NavController.navigateDayRecordDetail(localDate: LocalDate) {
    navigate("${DAY_RECORD_DETAIL_ROUTE}/${localDate}") {
        popUpTo(DAY_RECORD_DETAIL_ROUTE)
    }
}

fun NavGraphBuilder.dayRecordDetailScreen(
    navHostController: NavHostController
) {
    composable(
        route = "${DAY_RECORD_DETAIL_ROUTE}/{localDate}",
    ) {
        DayRecordDetailRoute(
            navHostController = navHostController
        )
    }
}