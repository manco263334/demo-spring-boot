package com.example.demo.infrastructure.http.service

import com.example.demo.application.use_cases.user.CreateUser
import com.example.demo.domain.models.UserModel
import com.example.demo.infrastructure.http.controller.AuthResponse
import com.example.demo.infrastructure.http.utils.mapper.toEntity
import com.example.demo.infrastructure.http.utils.mapper.toModel
import com.example.demo.infrastructure.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.Error

data class LoginRequest (
    val email: String,
    val password: String
)

@Service
class AuthService (
    private val repository: UserRepository,
    private val jwt: JWTService,
    private val userCreateUseCase: CreateUser,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {
    fun register (data: UserModel): AuthResponse {
        val user = repository.findByEmail(data.email.toString())

        if (user != null) {
            throw Error("User already exists")
        }

        data.password = passwordEncoder.encode(data.password)!!

        val transaction = userCreateUseCase.execute(data.toEntity())

        return AuthResponse(jwt.getToken(mapOf("id" to transaction.id!!), transaction.toModel(data.password)))
    }

    fun login (data: LoginRequest): AuthResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(data.email, data.password))
        val user = repository.findByEmail(data.email)?.toModel() ?: throw NoSuchElementException("User with email '${data.email}' not found")
        val token: String = jwt.getToken(mapOf("id" to user.id!!), user)
        return AuthResponse(token)
    }

    fun me() {

    }
}