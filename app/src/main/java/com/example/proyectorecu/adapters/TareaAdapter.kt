package com.example.proyectorecu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.R
import com.example.proyectorecu.models.TareaModel

//Adaptador para el RecyclerView que maneja una lista de tareas.
class TareaAdapter(
    var lista: MutableList<TareaModel>,
    private val borrarTarea: (Int)->Unit,
    private val updateTarea: (TareaModel)->Unit
): RecyclerView.Adapter<TareaViewHolder>() {

    //Método que se ejecuta al crear un nuevo ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.tarea_layout, parent, false)
        return TareaViewHolder(v)
    }

    //Devuelve la cantidad de elementos en la lista de tareas.
    override fun getItemCount()=lista.size

    // Método que asocia los datos de una tarea a un ViewHolder.
    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.render(lista[position], borrarTarea, updateTarea)
    }
}