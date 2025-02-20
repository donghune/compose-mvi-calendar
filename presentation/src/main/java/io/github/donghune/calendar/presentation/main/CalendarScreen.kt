package io.github.donghune.calendar.presentation.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.donghune.calendar.R
import io.github.donghune.calendar.extension.toLocalDate
import io.github.donghune.calendar.extension.totalMonths
import io.github.donghune.calendar.presentation.detail.DAY_RECORD_DETAIL_ROUTE
import io.github.donghune.calendar.presentation.detail.navigateDayRecordDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
internal fun CalendarRoute(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(navHostController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.route == DAY_RECORD_DETAIL_ROUTE) {
                viewModel.updateCurrentLocalDate(uiState.currentLocalDate)
            }
        }
        navHostController.addOnDestinationChangedListener(listener)

        onDispose {
            navHostController.removeOnDestinationChangedListener(listener)
        }
    }

    CalendarScreen(
        modifier = modifier,
        localDate = uiState.currentLocalDate,
        days = uiState.recordList,
        onPrevClick = { viewModel.updateCurrentLocalDate(uiState.currentLocalDate.minusMonths(1)) },
        onNextClick = { viewModel.updateCurrentLocalDate(uiState.currentLocalDate.plusMonths(1)) },
        onPageChanged = { position ->
            viewModel.updateCurrentLocalDate(position.toLocalDate)
        },
        onDayRecordClick = {
            if (it.isSelected) {
                navHostController.navigateDayRecordDetail(it.localDate)
            } else {
                viewModel.onClickDayRecord(it)
            }
        }
    )
}

@Preview
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    localDate: LocalDate = LocalDate.now(),
    days: List<DayRecordUiState> = listOf(),
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onPageChanged: (Int) -> Unit = {},
    onDayRecordClick: (DayRecordUiState) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = localDate.totalMonths,
        pageCount = { Int.MAX_VALUE },
    )

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Scaffold(
        topBar = {
            CalendarTopBar(onPrevClick, coroutineScope, pagerState, localDate, onNextClick)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            // Weekdays Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) {
                MonthGrid(
                    days = days,
                    onClickDayRecord = onDayRecordClick
                )
            }
        }
    }
}

@Composable
private fun CalendarTopBar(
    onPrevClick: () -> Unit,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    localDate: LocalDate,
    onNextClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onPrevClick()
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                    contentDescription = "Previous"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = YearMonth.from(localDate).toString(),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                onNextClick()
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                    contentDescription = "Next"
                )
            }
        }
    }
}

@Composable
fun MonthGrid(
    days: List<DayRecordUiState>,
    onClickDayRecord: (DayRecordUiState) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7), // 7일씩 한 줄
        modifier = Modifier.fillMaxWidth()
    ) {
        items(days) { day ->
            DayItem(day, onClickDayRecord)
        }
    }
}

@Composable
fun DayItem(
    day: DayRecordUiState,
    onClickDayRecord: (DayRecordUiState) -> Unit
) {
    if (!day.isVisible) {
        return
    }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = 1.dp,
                color = if (day.isSelected) Color.Black else Color.Transparent
            )
            .clickable {
                onClickDayRecord(day)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = getEmotionDrawable(day.emotion)),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(getEmotionColor(day.emotion))
        )

        Text(text = day.localDate.dayOfMonth.toString())
    }
}

// 감정 상태에 따른 아이콘 선택
@DrawableRes
fun getEmotionDrawable(emotion: EmotionType): Int {
    return when (emotion) {
        EmotionType.HAPPY -> R.drawable.ic_smile
        EmotionType.SAD -> R.drawable.ic_sad
        EmotionType.ANGRY -> R.drawable.ic_angry
        EmotionType.EMPTY -> R.drawable.ic_empt_emotion
    }
}

// 감정 상태에 따른 색상 선택
fun getEmotionColor(emotion: EmotionType): Color {
    return when (emotion) {
        EmotionType.HAPPY -> Color.Green
        EmotionType.SAD -> Color.Blue
        EmotionType.ANGRY -> Color.Red
        EmotionType.EMPTY -> Color.Gray
    }
}

