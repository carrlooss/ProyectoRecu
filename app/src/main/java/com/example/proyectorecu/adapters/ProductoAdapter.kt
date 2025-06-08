package com.example.proyectorecu.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.R
import com.example.proyectorecu.models.ListaCompraViewModel
import com.example.proyectorecu.models.Producto

class ProductoAdapter(private val productos: List<Producto>, private val viewModel: ListaCompraViewModel) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombreProducto)
        val btnComprar: Button = itemView.findViewById(R.id.btnComprar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombre

        // Configurar botón Comprar
        holder.btnComprar.text = if (producto.comprado) "Comprado" else "Comprar"
        holder.btnComprar.setBackgroundColor(
            if (producto.comprado) Color.RED else Color.BLUE
        )

        holder.btnComprar.setOnClickListener {
            viewModel.marcarComprado(producto)
        }

        // Configurar botón Eliminar
        holder.btnEliminar.text = "🗑"
        holder.btnEliminar.setBackgroundColor(Color.GRAY)
        holder.btnEliminar.setOnClickListener {
            viewModel.eliminarProducto(producto)
        }
    }

    override fun getItemCount() = productos.size
}