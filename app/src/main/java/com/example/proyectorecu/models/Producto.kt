package com.example.proyectorecu.models

//Modelo de datos para representar un producto en la lista de compras.
data class Producto(
    val id: Int,
    val nombre: String,
    var comprado: Boolean = false
)