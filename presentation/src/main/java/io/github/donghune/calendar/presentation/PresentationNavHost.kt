package io.github.donghune.calendar.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.donghune.calendar.presentation.detail.dayRecordDetailScreen
import io.github.donghune.calendar.presentation.main.CALENDAR_ROUTE
import io.github.donghune.calendar.presentation.main.calendarScreen

@Composable
internal fun PresentationNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = CALENDAR_ROUTE,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        calendarScreen(navHostController = navController)
        dayRecordDetailScreen(navHostController = navController)
    }
}