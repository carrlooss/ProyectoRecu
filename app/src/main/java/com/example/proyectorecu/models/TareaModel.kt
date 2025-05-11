package com.example.proyectorecu.models

import java.io.Serializable

//Modelo de datos para representar una tarea.
//Implementa Serializable para permitir la transferencia de objetos entre componentes.
data class TareaModel(
    val id: Int,
    val tipo: Int,
    val nombre: String,
    val descripcion: String,
    val realizado: Boolean,
): Serializable