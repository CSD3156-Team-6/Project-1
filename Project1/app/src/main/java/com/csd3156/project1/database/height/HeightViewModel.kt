package com.csd3156.project1.database.height

import com.csd3156.project1.database.UserPreferencesRepository
import androidx.lifecycle.*
import com.csd3156.project1.database.ScoreRepository
import kotlinx.coroutines.launch


class HeightViewModel(
    private val repository: ScoreRepository,
    private val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    val allHeight: LiveData<List<Height>> = repository.allHeight.asLiveData()

    fun insert(height: Height) = viewModelScope.launch {
        repository.insertHeight(height)
    }

    fun clear() = viewModelScope.launch {
        repository.clearHeight()
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

class HeightViewModelFactory(
    private val repository: ScoreRepository,
    private val userPreferencesRepository: UserPreferencesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeightViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}