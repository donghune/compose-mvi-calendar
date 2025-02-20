package io.github.donghune.calendar.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.donghune.calendar.presentation.main.EmotionType
import io.github.donghune.domain.entity.DayRecord
import io.github.donghune.domain.usecase.GetDayRecordUseCase
import io.github.donghune.domain.usecase.SaveDayRecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class DayRecordDetailUiState(
    val localDate: LocalDate = LocalDate.now(),
    val title: String = "",
    val description: String = "",
    val emotionType: EmotionType = EmotionType.HAPPY,
    val isSaved: Boolean = false,
)

@HiltViewModel
class DayRecordDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDayRecordUseCase: GetDayRecordUseCase,
    private val saveDayRecordUseCase: SaveDayRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DayRecordDetailUiState())
    val uiState: StateFlow<DayRecordDetailUiState> = _uiState

    init {
        LocalDate.parse(savedStateHandle.get<String>("localDate"))
            .also { extraLocalDate -> _uiState.update { it.copy(localDate = extraLocalDate) } }

        viewModelScope.launch {
            getDayRecordUseCase.invoke(uiState.value.localDate)?.let { dayRecord ->
                _uiState.update {
                    it.copy(
                        title = dayRecord.title,
                        description = dayRecord.description,
                        emotionType = when (dayRecord.emotion) {
                            DayRecord.Emotion.HAPPY -> EmotionType.HAPPY
                            DayRecord.Emotion.SAD -> EmotionType.SAD
                            DayRecord.Emotion.ANGRY -> EmotionType.ANGRY
                            DayRecord.Emotion.EMPTY -> EmotionType.EMPTY
                        }
                    )
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateEmotionType(emotionType: EmotionType) {
        _uiState.update { it.copy(emotionType = emotionType) }
    }

    fun save() {
        viewModelScope.launch {
            saveDayRecordUseCase.invoke(
                DayRecord(
                    localDate = uiState.value.localDate,
                    title = uiState.value.title,
                    description = uiState.value.description,
                    emotion = when (uiState.value.emotionType) {
                        EmotionType.HAPPY -> DayRecord.Emotion.HAPPY
                        EmotionType.SAD -> DayRecord.Emotion.SAD
                        EmotionType.ANGRY -> DayRecord.Emotion.ANGRY
                        EmotionType.EMPTY -> DayRecord.Emotion.EMPTY
                    }
                )
            )

            _uiState.update { it.copy(isSaved = true) }
        }
    }

}