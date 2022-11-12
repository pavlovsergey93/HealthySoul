package com.gmai.pavlovsv93.healtysoul.domain.usecases.base

import com.gmai.pavlovsv93.healtysoul.domain.wrapper.CaseResult
import kotlinx.coroutines.flow.Flow

typealias DefaultUseCase<T, E> = () -> Flow<CaseResult<T, E>>
