package com.example.demo.infrastructure.http.service

import com.example.demo.application.use_cases.user.UpdateUser
import com.example.demo.application.use_cases.user.UpdateUserRequest
import com.example.demo.domain.entities.UserEntity
import com.example.demo.infrastructure.dto.UserDTO
import com.example.demo.infrastructure.http.utils.mapper.isNullOrFalse
import com.example.demo.infrastructure.http.utils.mapper.toDTO
import com.example.demo.infrastructure.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService (
    private val repository: UserRepository,
    private val userUpdateUseCase: UpdateUser
) {
    fun findById(id: String, withRecipes: Boolean?): UserDTO? {
        val user = repository.findById(id).getOrNull()?.toDTO()

        if (withRecipes.isNullOrFalse() && user != null) {
            user.recipes = null
        }

        return user
    }

    fun findAll(withRecipes: Boolean?): List<UserDTO> {
        val users = repository.findAll().map { it.toDTO() }

        if (withRecipes.isNullOrFalse()) {
            return users.map { it.recipes = null; it }
        }

        return users
    }

    fun update(id: String, data: UpdateUserRequest): UserDTO {
        data.id = id
        return userUpdateUseCase.execute(data)
    }

    fun delete(id: String) {
        repository.deleteById(id)
    }
}