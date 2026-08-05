package com.corevo.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corevo.main.data.model.*
import com.corevo.main.repo.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutsViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {
    private val _plans = MutableStateFlow<List<WorkoutPlan>>(emptyList())
    val plans: StateFlow<List<WorkoutPlan>> = _plans

    private val _coaches = MutableStateFlow<List<ValidatorUser>>(emptyList())
    val coaches: StateFlow<List<ValidatorUser>> = _coaches

    private val _availableExercises = MutableStateFlow<List<Exercise>>(emptyList())
    val availableExercises: StateFlow<List<Exercise>> = _availableExercises

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val title = MutableStateFlow("")
    val description = MutableStateFlow("")
    val weeks = MutableStateFlow(4)
    val selectedDifficulty = MutableStateFlow(DifficultyLevel.INTERMEDIATE)
    val selectedExerciseIds = MutableStateFlow<List<Long>>(emptyList())

    init {
        loadMyPlans()
        loadCoachesAndExercises()
    }

    fun loadMyPlans() {
        viewModelScope.launch {
            _isLoading.value = true
            val res = workoutRepository.getMyPlans()
            if (res.isSuccess) _plans.value = res.getOrNull() ?: emptyList()
            _isLoading.value = false
        }
    }

    private fun loadCoachesAndExercises() {
        viewModelScope.launch {
            val exRes = workoutRepository.getExercises(0)
            if (exRes.isSuccess) _availableExercises.value = exRes.getOrNull()?.content ?: emptyList()

            val coachesRes = workoutRepository.getValidators()
            if (coachesRes.isSuccess) _coaches.value = coachesRes.getOrNull() ?: emptyList()
        }
    }

    fun toggleExerciseSelection(id: Long) {
        val list = selectedExerciseIds.value.toMutableList()
        if (list.contains(id)) list.remove(id) else list.add(id)
        selectedExerciseIds.value = list
    }

    fun createPlan(onSuccess: () -> Unit) {
        if (title.value.isBlank() || selectedExerciseIds.value.isEmpty()) return
        viewModelScope.launch {
            _isLoading.value = true
            val res = workoutRepository.createPlan(
                title = title.value,
                desc = description.value,
                diff = selectedDifficulty.value,
                weeks = weeks.value,
                exIds = selectedExerciseIds.value
            )
            if (res.isSuccess) {
                title.value = ""
                description.value = ""
                selectedExerciseIds.value = emptyList()
                loadMyPlans()
                onSuccess()
            }
            _isLoading.value = false
        }
    }

    fun sendForReview(planId: Long, coachUsername: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val res = workoutRepository.sendForValidation(planId, coachUsername)
            if (res.isSuccess) loadMyPlans()
            _isLoading.value = false
        }
    }
}
