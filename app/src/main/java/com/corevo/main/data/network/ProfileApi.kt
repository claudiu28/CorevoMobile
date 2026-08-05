package com.corevo.main.data.network

import com.corevo.main.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {
    @GET("profile/me")
    suspend fun getMyProfile(): Response<UserDetails>

    @PUT("profile/me")
    suspend fun updateProfile(@Body update: UpdateInformation): Response<UserDetails>

    @GET("profile/activity")
    suspend fun getActivityHeatmap(): Response<ActivityHeatmap>
}
