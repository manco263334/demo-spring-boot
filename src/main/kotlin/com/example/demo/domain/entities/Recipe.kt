package com.example.demo.domain.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "recipes")
data class RecipeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: String? = null,

    @Column
    var name: String,

    @Column
    var persons: Int,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column
    var ingredients: List<Map<String, String>>,

    @Column
    var steps: List<String>,

    @Column(name = "total_time_in_minutes")
    var totalTimeInMinutes: Int,

    @Column(name = "cooking_time_in_minutes")
    var cookingTimeInMinutes: Int,

    @Column(name = "preparation_time_in_minutes")
    var preparationTimeInMinutes: Int,

    @Column
    var stars: Int = 0,

    @Column(nullable = true)
    var icon: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private var creator: UserEntity,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinTable (
        name = "categories_recipes",
        inverseJoinColumns = [JoinColumn(name = "category_id", referencedColumnName = "id")],
        joinColumns = [JoinColumn(name = "recipe_id", referencedColumnName = "id")]
    )
    private var categories: MutableList<CategoryEntity> = mutableListOf()
) {
    fun updateCategories(categories: List<CategoryEntity>) {
        this.categories = categories.toMutableList()
    }

    fun getCategories() = categories.toList()

    fun getCreator() = creator
}