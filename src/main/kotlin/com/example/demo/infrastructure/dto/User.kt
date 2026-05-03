package com.example.demo.infrastructure.dto

data class UserDTO (
    var id: String?,
    var name: String,
    var email: String,
    var role: String,
    var phone: String?,
    var username: String?,
    var icon: String?,

    var recipes: List<Map<String, Any?>>? = null
)