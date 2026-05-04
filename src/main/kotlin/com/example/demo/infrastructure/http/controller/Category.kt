package com.example.demo.infrastructure.http.controller

import com.example.demo.application.use_cases.category.UpdateCategoryRequest
import com.example.demo.domain.entities.CategoryEntity
import com.example.demo.infrastructure.dto.CategoryDTO
import com.example.demo.infrastructure.http.service.CategoryService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryController (
    private val service: CategoryService
) {
    @PostMapping("/", "")
    @PreAuthorize("authentication != null")
    fun create (
        @RequestBody data: CategoryEntity
    ): CategoryDTO {
        return service.save(data)
    }

    @GetMapping("/", "", "/all")
    fun getAll (
        @RequestParam(value = "withRecipes", required = false) withRecipes: Boolean?
    ): List<CategoryDTO> {
        return service.findAll(withRecipes)
    }

    @GetMapping("/{id}")
    fun getById (
        @PathVariable id: String,
        @RequestParam(value = "withRecipes", required = false) withRecipes: Boolean?
    ): CategoryDTO {
        return service.findById(id, withRecipes) ?: throw NoSuchElementException("Category with id '$id' not found")
    }

    @PutMapping("/{id}")
    fun update (
        @PathVariable id: String,
        @RequestBody data: UpdateCategoryRequest
    ): CategoryDTO {
        return service.update(id, data)
    }

    @DeleteMapping("/{id}")
    fun delete (
        @PathVariable id: String
    ) {
        service.delete(id)
    }
}