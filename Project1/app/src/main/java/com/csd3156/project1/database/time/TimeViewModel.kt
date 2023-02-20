package com.csd3156.project1.database.time

import com.csd3156.project1.database.UserPreferencesRepository
import androidx.lifecycle.*
import kotlinx.coroutines.launch


class TimeViewModel(
    private val repository: TimeRepository,
    private val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    val allTime: LiveData<List<Time>> = repository.allTime.asLiveData()

    fun insert(time: Time) = viewModelScope.launch {
        repository.insert(time)
    }

    fun clear() = viewModelScope.launch {
        repository.clear()
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

class FourDigitViewModelFactory(
    private val repository: TimeRepository,
    private val userPreferencesRepository: UserPreferencesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimeViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}