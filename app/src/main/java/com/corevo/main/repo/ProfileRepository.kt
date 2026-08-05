package com.corevo.main.repo

import com.corevo.main.data.model.*
import com.corevo.main.data.network.ProfileApi

class ProfileRepository(private val profileApi: ProfileApi) {
    suspend fun getMyProfile(): Result<UserDetails> {
        return try {
            val res = profileApi.getMyProfile()
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to fetch profile"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(update: UpdateInformation): Result<UserDetails> {
        return try {
            val res = profileApi.updateProfile(update)
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to update profile"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getActivityHeatmap(): Result<ActivityHeatmap> {
        return try {
            val res = profileApi.getActivityHeatmap()
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to fetch activity heatmap"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
