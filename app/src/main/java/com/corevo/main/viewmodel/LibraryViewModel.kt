package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corevo.main.data.model.Exercise
import com.corevo.main.repo.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    private val _filteredExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val filteredExercises: StateFlow<List<Exercise>> = _filteredExercises

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val searchQuery = MutableStateFlow("")

    init {
        loadExercises()
    }

    fun loadExercises() {
        viewModelScope.launch {
            _isLoading.value = true
            val res = workoutRepository.getExercises()
            if (res.isSuccess) {
                val list = res.getOrNull()?.content ?: emptyList()
                _exercises.value = list
                _filteredExercises.value = list
            }
            _isLoading.value = false
        }
    }

    fun filter(query: String) {
        searchQuery.value = query
        if (query.isBlank()) {
            _filteredExercises.value = _exercises.value
        } else {
            _filteredExercises.value = _exercises.value.filter {
                it.name.contains(query, ignoreCase = true) ||
                (it.targetMuscles?.any { muscle -> muscle.contains(query, ignoreCase = true) } == true)
            }
        }
    }
}
