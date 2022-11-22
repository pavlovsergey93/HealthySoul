package com.gmai.pavlovsv93.healtysoul.domain.profile

import com.gmai.pavlovsv93.healtysoul.domain.models.profile.Profile
import com.gmai.pavlovsv93.healtysoul.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllInfoCase(
    private val profileRepository: ProfileRepository
    ) {
        suspend fun execute(): Flow<Profile> = flow {
            val result = profileRepository.getAllNotes()
            emit(result)
        }
}