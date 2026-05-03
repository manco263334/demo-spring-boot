package com.example.demo.domain.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: String? = null,

    @Column
    var name: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @Column
    var role: String = "USER",

    @Column
    var password: String,

    @Column(nullable = true)
    var phone: String? = null,

    @Column(nullable = true)
    var username: String? = null,

    @Column(nullable = true)
    var icon: String? = null,

    @OneToMany(mappedBy = "creator", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var recipes: MutableList<RecipeEntity> = mutableListOf()
) {
    fun getRecipes(): List<RecipeEntity> = recipes.toList()
}