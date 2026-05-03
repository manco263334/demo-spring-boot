package com.example.demo.infrastructure.repository

import com.example.demo.domain.entities.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, String> {

}