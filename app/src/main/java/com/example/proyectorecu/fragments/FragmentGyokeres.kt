package com.example.fragments141124.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectorecu.R

//Fragmento que representa la pantalla de Gyökeres.
class FragmentGyokeres : Fragment() {


    //Método llamado cuando el fragmento se crea por primera vez.
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
        return inflater.inflate(R.layout.fragment_gyokeres, container, false)
    }


}