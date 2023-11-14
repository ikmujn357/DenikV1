package com.example.denikv1

data class Cesta(
    val id: Int,
    val title: String,
    val description: String,
) {
    // Konstruktor pro vytvoření instance
    constructor(title: String, description: String) : this(0, title, description)
}
