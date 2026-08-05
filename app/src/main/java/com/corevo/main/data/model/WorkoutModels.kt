package com.corevo.main.data.model

enum class DifficultyLevel { BEGINNER, INTERMEDIATE, ADVANCED, EXPERT }
enum class ValidationStatus { DRAFT, PENDING, APPROVED, REJECTED }

data class ValidatorUser(
    val username: String,
    val profilePicture: String? = null,
    val roleName: String? = null
)

data class Exercise(
    val id: Long,
    val name: String,
    val gifUrl: String? = null,
    val targetMuscles: List<String>? = null,
    val bodyParts: List<String>? = null,
    val equipments: List<String>? = null,
    val secondaryMuscles: List<String>? = null,
    val instructions: List<String>? = null,
    val createdAt: String? = null
)

data class WorkoutPlan(
    val id: Long,
    val title: String,
    val description: String,
    val difficulty: DifficultyLevel,
    val durationWeeks: Int,
    val creatorUsername: String,
    val creatorProfilePicture: String? = null,
    val validationStatus: ValidationStatus,
    val validatorUsername: String? = null,
    val isCompleted: Boolean = false,
    val validationFeedback: String? = null,
    val exercises: List<Exercise> = emptyList(),
    val createdAt: String? = null
)

data class CreateWorkoutPlanRequest(
    val title: String,
    val description: String,
    val difficulty: DifficultyLevel,
    val durationWeeks: Int,
    val exerciseIds: List<Long>
)

data class SendForValidationRequest(
    val validatorUsername: String
)

data class ValidateWorkoutPlanRequest(
    val approved: Boolean,
    val feedback: String? = null
)
