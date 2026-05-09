package com.example.demo.infrastructure.http.controller

import com.example.demo.application.use_cases.category.UpdateCategoryRequest
import com.example.demo.domain.entities.CategoryEntity
import com.example.demo.infrastructure.dto.CategoryDTO
import com.example.demo.infrastructure.http.service.CategoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<CategoryDTO> {
        val response = service.save(data)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/", "", "/all")
    fun getAll (
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "withRecipes", required = false) withRecipes: Boolean?
    ): ResponseEntity<Page<CategoryDTO>> {
        val pageable = PageRequest.of(page, size)
        val response = service.findAll(pageable, withRecipes)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getById (
        @PathVariable id: String,
        @RequestParam(value = "withRecipes", required = false) withRecipes: Boolean?
    ): ResponseEntity<CategoryDTO> {
        val response = service.findById(id, withRecipes) ?: throw NoSuchElementException("Category with id '$id' not found")

        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun update (
        @PathVariable id: String,
        @RequestBody data: UpdateCategoryRequest
    ): ResponseEntity<CategoryDTO> {
        val response = service.update(id, data)

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun delete (
        @PathVariable id: String
    ): ResponseEntity<Unit> {
        val response = service.delete(id)

        return ResponseEntity.ok(response)
    }
}