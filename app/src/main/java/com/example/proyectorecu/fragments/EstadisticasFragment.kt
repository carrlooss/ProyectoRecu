package com.example.proyectorecu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyectorecu.R
import com.example.proyectorecu.providers.db.CrudTareas

// Fragmento que muestra estadísticas sobre las tareas almacenadas en la base de datos.
class EstadisticasFragment : Fragment() {

    //Método llamado cuando el fragmento es creado.
    //Aquí se pueden inicializar variables o recuperar datos antes de que la UI se renderice.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Infla el layout del fragmento cuando se crea la vista.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas, container, false)

    }

    //Método llamado cuando la vista del fragmento ya ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var registros = CrudTareas().registrosTotales()

        val tvRegistros = view.findViewById<TextView>(R.id.tv_registros)
        tvRegistros.text = "La tabla tareas tiene $registros registros"
    }
}