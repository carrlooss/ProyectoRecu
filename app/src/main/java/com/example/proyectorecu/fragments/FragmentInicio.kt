package com.example.fragments141124.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectorecu.R

//Fragmento que representa la pantalla de inicio de la aplicación.
class FragmentInicio : Fragment() {


    //Se ejecuta cuando el fragmento es creado.
    //Aquí se pueden inicializar variables o recuperar datos antes de que la UI se renderice.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //Infla el layout del fragmento cuando se crea la vista.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del fragmento desde XML
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }


}