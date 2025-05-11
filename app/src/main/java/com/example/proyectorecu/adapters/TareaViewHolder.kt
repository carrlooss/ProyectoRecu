package com.example.proyectorecu.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.databinding.TareaLayoutBinding
import com.example.proyectorecu.models.TareaModel

//ViewHolder para representar una tarea dentro del RecyclerView.
class TareaViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val tipos = listOf("Normal", "Importante")

    val binding  = TareaLayoutBinding.bind(v)

    //Método para asignar los datos de una tarea a los elementos de la vista.
    fun render(
        t: TareaModel,
        borrarTarea: (Int) -> Unit,
        updateTarea: (TareaModel) -> Unit
    ){
        binding.tvTipo.text = tipos[t.tipo]
        binding.tvNombre.text=t.nombre
        binding.tvDescripcion.text=t.descripcion
        binding.cbRealizado.isChecked = t.realizado

        binding.btnBorrar.setOnClickListener {
            borrarTarea(adapterPosition)
        }
        binding.btnUpdate.setOnClickListener {
            updateTarea(t)
        }
    }

}
