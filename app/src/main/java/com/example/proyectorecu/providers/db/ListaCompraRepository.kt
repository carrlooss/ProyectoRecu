package com.example.proyectorecu.providers.db

import com.example.proyectorecu.models.Producto

//Repositorio para gestionar la lista de compra
//Almacena una lista mutable de productos y proporciona métodos para gestionarlos
class ListaCompraRepository {
    // Lista mutable que almacena los productos
    private val productos = mutableListOf<Producto>()

    // Obtiene la lista de productos almacenados.
    fun getProductos(): List<Producto> = productos
    //Agrega un producto a la lista de compra
    fun agregarProducto(producto: Producto) { productos.add(producto) }
    //Eliminar un produto de la lista
    fun eliminarProducto(producto: Producto) { productos.remove(producto) }
    //Marcar un producto si est comprado o no
    fun marcarComprado(producto: Producto) {
        val index = productos.indexOfFirst { it.id == producto.id }
        if (index != -1) productos[index].comprado = !productos[index].comprado
    }
}