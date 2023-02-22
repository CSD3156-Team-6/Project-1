package com.csd3156.project1.database.time

import com.csd3156.project1.database.UserPreferencesRepository
import androidx.lifecycle.*
import com.csd3156.project1.database.ScoreRepository
import kotlinx.coroutines.launch


class TimerViewModel(
    private val repository: ScoreRepository,
    private val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    val allTime: LiveData<List<Timer>> = repository.allTime.asLiveData()

    fun insert(time: Timer) = viewModelScope.launch {
        repository.insertTime(time)
    }

    fun clear() = viewModelScope.launch {
        repository.clearTime()
    }

    fun savePreference(preference: Boolean) = viewModelScope.launch {
        userPreferencesRepository.save(preference)
    }

    fun getPreference(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userPreferencesRepository.read()
            if (result != null)
                callback(result)
        }
    }
}

class TimerViewModelFactory(
    private val repository: ScoreRepository,
    private val userPreferencesRepository: UserPreferencesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}