package com.example.proyectorecu

import android.app.Application
import android.content.Context
import com.example.proyectorecu.providers.db.MyDatabase


class Aplicacion: Application() {

    // Companion object permite definir variables estáticas compartidas en toda la aplicación
    companion object{
        // Versión de la base de datos
        const val VERSION=2
        //Nombre de la bd
        const val DB="Base_1"
        //Nombre de la tabla en la base de datos
        const val TABLA="tareas"
        lateinit var appContext: Context
        lateinit var llave: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // Guardamos el contexto global de la aplicación para su uso en otras partes del código
        appContext=applicationContext
        // Inicialización de la base de datos
        llave=MyDatabase()
    }
}