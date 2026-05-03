package com.example.demo.domain.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "categories")
data class CategoryEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: String? = null,

    @Column
    var name: String,

    @Column(nullable = true)
    var icon: String? = null,

    @ManyToMany(mappedBy = "categories", cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    private var recipes: MutableList<RecipeEntity> = mutableListOf()
) {
    fun getRecipes(): List<RecipeEntity> = recipes.toList()
}