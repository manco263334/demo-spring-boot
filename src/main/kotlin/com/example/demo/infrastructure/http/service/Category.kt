package com.example.demo.infrastructure.http.service

import com.example.demo.application.use_cases.category.CreateCategory
import com.example.demo.application.use_cases.category.UpdateCategory
import com.example.demo.application.use_cases.category.UpdateCategoryRequest
import com.example.demo.domain.entities.CategoryEntity
import com.example.demo.infrastructure.dto.CategoryDTO
import com.example.demo.infrastructure.http.utils.mapper.isNullOrFalse
import com.example.demo.infrastructure.http.utils.mapper.toDTO
import com.example.demo.infrastructure.repository.CategoryRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CategoryService (
    private val repository: CategoryRepository,
    private val createCategoryUseCase: CreateCategory,
    private val updateCategoryUseCase: UpdateCategory,
) {
    fun save (category: CategoryEntity): CategoryDTO {
        return createCategoryUseCase.execute(category)
    }

    fun findAll (withRecipes: Boolean?): List<CategoryDTO> {
        val categories = repository.findAll().map { it.toDTO() }

        if (withRecipes.isNullOrFalse()) {
            return categories.map { it.recipes = null; it }
        }

        return categories
    }

    fun findById (id: String, withRecipes: Boolean?): CategoryDTO? {
        val category = repository.findById(id).getOrNull()?.toDTO() ?: throw Error("Category with id '$id' not found")

        if (withRecipes.isNullOrFalse()) {
            category.recipes = null
        }

        return category
    }

    fun update (id: String, data: UpdateCategoryRequest): CategoryDTO {
        data.id = id
        return updateCategoryUseCase.execute(data)
    }

    fun delete (id: String) {
        repository.deleteById(id)
    }
}