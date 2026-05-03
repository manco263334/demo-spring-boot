package com.example.demo.infrastructure.dto

data class RecipeDTO (
    var id: String?,
    var name: String,
    var persons: Int,
    var ingredients: List<Map<String, String>>,
    var steps: List<String>,
    var totalTimeInMinutes: Int,
    var cookingTimeInMinutes: Int,
    var preparationTimeInMinutes: Int,
    var stars: Int?,
    var icon: String?,

    var creator: Map<String, Any?>? = null,
    var categories: List<Map<String, Any?>>? = null
)