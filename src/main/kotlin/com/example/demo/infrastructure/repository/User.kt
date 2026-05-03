package com.example.demo.infrastructure.repository

import com.example.demo.domain.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
}