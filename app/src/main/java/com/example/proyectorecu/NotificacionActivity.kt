package com.example.proyectorecu

data class Notificacion(val titulo: String, val descripcion: String, val fecha: String)

// Ejemplo de datos
val notificaciones = listOf(
    Notificacion("Pedido enviado", "Tu pedido #123 ya está en camino", "Hoy"),
    Notificacion("Nuevo reto disponible", "¡Explora la selva encantada!", "Ayer"),
    Notificacion("Actualización", "Se ha mejorado la velocidad de carga", "31/05")
)