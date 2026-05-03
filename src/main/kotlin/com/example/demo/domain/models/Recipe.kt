package com.example.demo.domain.models

import com.example.demo.domain.value_objects.Ingredient

data class RecipeModel (
    var id: String?,
    var name: String,
    var persons: Int,
    var ingredients: List<Ingredient>,
    var steps: List<String>,
    var totalTimeInMinutes: Int,
    var cookingTimeInMinutes: Int,
    var preparationTimeInMinutes: Int,
    var stars: Int,
    var icon: String?
)