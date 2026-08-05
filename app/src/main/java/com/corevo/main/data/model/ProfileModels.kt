package com.corevo.main.data.model

data class UserEssentials(
    val username: String,
    val email: String? = null
)

enum class GenderType { MALE, FEMALE, OTHER }
enum class GoalType { WEIGHT_LOSS, MUSCLE_GAIN, MAINTENANCE, GENERAL_FITNESS }
enum class ActivityLevelType { SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTREMELY_ACTIVE }

data class UserDetails(
    val userEssentials: UserEssentials,
    val profilePicture: String? = null,
    val bio: String? = null,
    val motto: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val isOwner: Boolean = false,
    val birthDate: String? = null,
    val heightCm: Int? = null,
    val weightKg: Int? = null,
    val gender: GenderType? = null,
    val primaryGoal: GoalType? = null,
    val activityLevel: ActivityLevelType? = null
)

data class ActivityDay(
    val date: String,
    val count: Int,
    val type: String
)

data class ActivityHeatmap(
    val days: List<ActivityDay> = emptyList(),
    val totalWorkouts: Int = 0,
    val totalPosts: Int = 0,
    val currentStreak: Int = 0
)

data class UpdateInformation(
    val firstName: String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val motto: String? = null,
    val birthDate: String? = null,
    val heightCm: Int? = null,
    val weightKg: Int? = null,
    val gender: GenderType? = null,
    val primaryGoal: GoalType? = null,
    val activityLevel: ActivityLevelType? = null
)
