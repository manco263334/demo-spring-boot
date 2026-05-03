package com.example.demo.infrastructure.dto

data class CategoryDTO (
    var id: String?,
    var name: String,
    var icon: String?,

    var recipes: List<Map<String, Any?>>? = null
)