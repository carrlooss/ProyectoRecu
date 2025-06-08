package com.example.proyectorecu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.Notificacion
import com.example.proyectorecu.databinding.ItemNotificacionBinding

class NotificacionAdapter(private val lista: List<Notificacion>) :
    RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder>() {

    inner class NotificacionViewHolder(val binding: ItemNotificacionBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacionViewHolder {
        val binding = ItemNotificacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificacionViewHolder, position: Int) {
        val item = lista[position]
        holder.binding.titulo.text = item.titulo
        holder.binding.descripcion.text = item.descripcion
        holder.binding.fecha.text = item.fecha
    }

    override fun getItemCount(): Int = lista.size
}