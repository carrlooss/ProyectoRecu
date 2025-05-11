package com.example.proyectorecu.models

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.R
import com.example.proyectorecu.adapters.ProductoAdapter
import kotlin.random.Random

//Actividad principal para gestionar la lista de compras.
// Permite visualizar, agregar y marcar productos como comprados.
class CompraActivity : AppCompatActivity() {
    private lateinit var viewModel: ListaCompraViewModel
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_compra)

        viewModel = ViewModelProvider(this).get(ListaCompraViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observa los cambios en la lista de productos y actualiza el RecyclerView
        viewModel.productos.observe(this, { productos ->
            adapter = ProductoAdapter(productos, viewModel)
            recyclerView.adapter = adapter
        })

        // Referencias a los elementos de la UI
        val botonAgregar: Button = findViewById(R.id.btnAgregar)
        val inputNombre: EditText = findViewById(R.id.inputNombre)

        // Acción del botón para agregar un nuevo producto
        botonAgregar.setOnClickListener {
            val nombre = inputNombre.text.toString()
            if (nombre.isNotEmpty()) {
                viewModel.agregarProducto(Producto(Random.nextInt(), nombre))
                inputNombre.text.clear()
            }
        }
    }
}