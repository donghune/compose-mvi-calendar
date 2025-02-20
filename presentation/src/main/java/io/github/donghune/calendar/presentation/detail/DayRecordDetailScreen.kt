@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.donghune.calendar.presentation.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.github.donghune.calendar.R
import io.github.donghune.calendar.presentation.main.EmotionType

@Composable
fun DayRecordDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DayRecordDetailViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val uiState by viewModel.uiState.collectAsState()

    DayRecordDetailScreen(
        modifier = modifier,
        uiState = uiState,
        onTitleChange = { viewModel.updateTitle(it) },
        onDescriptionChange = { viewModel.updateDescription(it) },
        onEmotionChange = { viewModel.updateEmotionType(it) },
        onBackClick = {
            viewModel.save()
            navHostController.popBackStack()
        }
    )
}


@Preview
@Composable
fun DayRecordDetailScreen(
    modifier: Modifier = Modifier,
    uiState: DayRecordDetailUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onEmotionChange: (EmotionType) -> Unit,
    onBackClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EmotionSelectDialog(
            onDismiss = { showDialog = false },
            onSelect = {
                onEmotionChange(it)
                showDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            // App Bar
            TopAppBar(
                title = { Text(text = uiState.localDate.toString()) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Title Input
            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text("하루의 요약") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description Input
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = { Text("하루의 설명") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Emotion Title
            Text(
                text = "오늘의 감정은 어떤가요?",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Emotion Image
            Image(
                painter = painterResource(id = getEmotionDrawable(uiState.emotionType)),
                contentDescription = "Emotion Icon",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Start)
                    .clickable(onClick = {
                        showDialog = true
                    })
            )
        }
    }
}

@Composable
fun EmotionSelectDialog(
    onDismiss: () -> Unit,
    onSelect: (EmotionType) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "감정을 선택하세요",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EmotionButton(EmotionType.SAD, onSelect)
                EmotionButton(EmotionType.HAPPY, onSelect)
                EmotionButton(EmotionType.ANGRY, onSelect)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun EmotionButton(
    emotion: EmotionType,
    onSelect: (EmotionType) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onSelect(emotion) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = getEmotionDrawable(emotion)),
            contentDescription = emotion.name,
            modifier = Modifier.size(48.dp)
        )
        Text(text = emotion.name)
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

