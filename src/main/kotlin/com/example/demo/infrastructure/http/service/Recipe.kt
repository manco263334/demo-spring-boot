package com.example.demo.infrastructure.http.service

import com.example.demo.application.use_cases.recipe.CreateRecipe
import com.example.demo.application.use_cases.recipe.CreateRecipeRequest
import com.example.demo.application.use_cases.recipe.UpdateRecipe
import com.example.demo.application.use_cases.recipe.UpdateRecipeRequest
import com.example.demo.domain.entities.UserEntity
import com.example.demo.infrastructure.dto.RecipeDTO
import com.example.demo.infrastructure.http.utils.mapper.isNullOrFalse
import com.example.demo.infrastructure.http.utils.mapper.toDTO
import com.example.demo.infrastructure.repository.RecipeRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RecipeService (
    private val repository: RecipeRepository,
    private val createRecipeUseCase: CreateRecipe,
    private val updateRecipeUseCase: UpdateRecipe
) {
    fun save (
        data: CreateRecipeRequest,
        user: UserEntity
    ): RecipeDTO {
        return createRecipeUseCase.execute(data, user)
    }

    fun findAll (
        withCategories: Boolean?,
        withCreator: Boolean?
    ): List<RecipeDTO> {
        var recipes = repository.findAll().map { it.toDTO() }

        if (withCategories.isNullOrFalse()) {
            recipes = recipes.map { it.categories = null; it }
        }

        if (withCreator.isNullOrFalse()) {
            recipes = recipes.map { it.creator = null; it }
        }

        return recipes
    }

    fun findById (
        id: String,
        withCategories: Boolean?,
        withCreator: Boolean?
    ): RecipeDTO? {
        val recipe = repository.findById(id).getOrNull()?.toDTO()

        if (withCategories.isNullOrFalse() && recipe != null) {
            recipe.categories = null
        }

        if (withCreator.isNullOrFalse() && recipe != null) {
            recipe.creator = null
        }

        return recipe
    }

    fun update (
        id: String,
        data: UpdateRecipeRequest
    ): RecipeDTO {
        data.id = id
        return updateRecipeUseCase.execute(data)
    }

    fun delete (
        id: String
    ) {
        return repository.deleteById(id)
    }
}