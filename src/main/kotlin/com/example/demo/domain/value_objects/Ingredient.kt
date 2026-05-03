package com.example.demo.domain.value_objects

class Ingredient {
    var quantity: String
    var name: String

    constructor (ingredient: Map<String, Any?>) {
        val name = ingredient["name"] ?: throw Error("Ingredient's name must be specified")
        val quantity = ingredient["quantity"] ?: throw Error("Ingredient's quantity must be specified")

        this.name = name.toString()
        this.quantity = quantity.toString()
    }
}