package com.gmai.pavlovsv93.healtysoul.domain.repository.profile

import com.gmai.pavlovsv93.healtysoul.domain.models.profile.Profile

interface ProfileRepository {
    suspend fun getAllNotes(): Profile
}