package com.gmail.data.repository.profile_repository

import com.gmai.pavlovsv93.healtysoul.domain.models.profile.Profile
import com.gmai.pavlovsv93.healtysoul.domain.repository.profile.ProfileRepository

class ProfileRepositoryImpl : ProfileRepository {
    override suspend fun getAllNotes(): Profile {
        return Profile(null, "Николай", "8-9111-123-45-67", null)
    }
}