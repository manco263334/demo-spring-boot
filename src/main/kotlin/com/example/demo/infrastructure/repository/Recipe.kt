package com.example.demo.infrastructure.repository

import com.example.demo.domain.entities.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<RecipeEntity, String> {
}