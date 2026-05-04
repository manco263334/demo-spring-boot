package com.example.demo.infrastructure.http.controller

import com.example.demo.application.use_cases.user.UpdateUserRequest
import com.example.demo.domain.entities.UserEntity
import com.example.demo.domain.models.UserModel
import com.example.demo.infrastructure.dto.UserDTO
import com.example.demo.infrastructure.http.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.typeOf

@RestController
@RequestMapping("/api/users")
class UserController (
    val service: UserService
) {
    @GetMapping("/", "", "/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun getAll (
        @RequestParam(value = "withRecipes", required = false) withRecipes: Boolean?
    ): List<UserDTO> {
        return service.findAll(withRecipes)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun getById (
        @PathVariable id: String,
        @RequestParam(value = "withRecipes", required = false) withRecipes: Boolean?
    ): UserDTO {
        return service.findById(id, withRecipes) ?: throw NoSuchElementException("User with id '$id' not found")
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun update (
        @PathVariable id: String,
        @RequestBody data: UpdateUserRequest
    ): UserDTO {
        return service.update(id, data)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun delete (
        @PathVariable id: String
    ) {
        return service.delete(id)
    }
}