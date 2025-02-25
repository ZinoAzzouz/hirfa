package com.example.hirfa.domain.usecase

import com.example.hirfa.data.model.Craftsman
import com.example.hirfa.data.repository.CraftsmanRepository
import javax.inject.Inject

class AddCraftsmanUseCase @Inject constructor(private val craftsmanRepository: CraftsmanRepository) {

    operator suspend fun invoke(craftsman: Craftsman) = craftsmanRepository.addCraftsman(craftsman)

}