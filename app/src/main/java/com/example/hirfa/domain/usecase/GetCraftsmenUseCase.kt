package com.example.hirfa.domain.usecase

import com.example.hirfa.data.repository.CraftsmanRepository
import javax.inject.Inject

class GetCraftsmenUseCase @Inject constructor(private val craftsmanRepository: CraftsmanRepository){

    operator fun invoke() = craftsmanRepository.getCraftsman()

}