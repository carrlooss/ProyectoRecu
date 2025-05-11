package com.example.fragments141124.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.proyectorecu.OnFragmentActionListener
import com.example.proyectorecu.R


class FragmentMenu : Fragment() {
    // Interfaz para manejar eventos de clic en las imágenes
    private  var listener: OnFragmentActionListener? = null
    // Lista de IDs de los ImageView en el layout
    private val listaImagenesView= arrayOf(R.id.iv_gavi, R.id.iv_garnacho, R.id.iv_gyokeres)
    // Lista de imágenes iluminadas correspondientes a cada botón
    private val imagenesBotonesIluminados= arrayOf(
        R.drawable.gavi,
        R.drawable.garnacho,
        R.drawable.gyokeres)
    // Variable que almacena el botón que debe mostrarse iluminado, con valor por defecto 3000 (no válido)
    var botonIluminado=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño del fragmento en la vista
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imageView: ImageView
        if(arguments!=null){
            botonIluminado=requireArguments().getInt("BOTONPULSADO")
        }

        // Iteramos sobre la lista de ImageView para configurar cada uno
        for(i in listaImagenesView.indices){
            imageView=view.findViewById(listaImagenesView[i])
            if(botonIluminado==i){
                imageView.setImageResource(imagenesBotonesIluminados[i])
            }
            //ponemos el listener a todos y cada uno de los images views
            imageView.setOnClickListener {
                listener?.onClickImagenMenu(i)
            }

        }
    }

    override fun onAttach(context: Context) {
        //se llama cuando cargamos el fragment en un activity
        super.onAttach(context)
        if(context is OnFragmentActionListener) listener=context
    }

    override fun onDetach() {
        super.onDetach()
        listener=null
    }


}