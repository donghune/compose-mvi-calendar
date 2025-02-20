package io.github.donghune.calendar.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val CALENDAR_ROUTE = "calendarRoute"

fun NavGraphBuilder.calendarScreen(
    navHostController: NavHostController
) {
    composable(
        route = CALENDAR_ROUTE,
    ) {
        CalendarRoute(
            navHostController = navHostController
        )
    }
}