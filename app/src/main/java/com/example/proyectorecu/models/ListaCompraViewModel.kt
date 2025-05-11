package com.example.proyectorecu.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectorecu.providers.db.ListaCompraRepository

//ViewModel para gestionar la lógica de la lista de compras.
class ListaCompraViewModel : ViewModel() {
    private val repository = ListaCompraRepository()
    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> get() = _productos

    //Carga los productos desde el repositorio y actualiza el LiveData.
    fun cargarProductos() {
        _productos.value = repository.getProductos()
    }

    //Agrega un producto a la lista de compras y recarga los datos.
    fun agregarProducto(producto: Producto) {
        repository.agregarProducto(producto)
        cargarProductos()
    }

    //Elimina un producto de la lista de compras y recarga los datos.
    fun eliminarProducto(producto: Producto) {
        repository.eliminarProducto(producto)
        cargarProductos()
    }

    //Cambia el estado de compra de un producto y recarga los datos.
    fun marcarComprado(producto: Producto) {
        repository.marcarComprado(producto)
        cargarProductos()
    }
}
