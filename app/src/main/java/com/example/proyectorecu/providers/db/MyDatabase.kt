package com.example.proyectorecu.providers.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectorecu.Aplicacion

//Clase encargada de gestionar la base de datos SQLite de la aplicación.
//Hereda de `SQLiteOpenHelper` para facilitar la creación y actualización de la BD.
class MyDatabase(): SQLiteOpenHelper(Aplicacion.appContext, Aplicacion.DB, null, Aplicacion.VERSION) {
    // Query SQL para la creación de la tabla de tareas
    private val q="create table ${Aplicacion.TABLA}(" +
            "id integer primary key autoincrement," +
            "tipo integer not null," +
            "nombre text not null," +
            "descripcion text not null," +
            "realizado boolean not null);"


    //Método que se ejecuta cuando la base de datos se crea por primera vez.
    //Se encarga de ejecutar la query para crear la tabla.
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(q)
    }

    //Método que se ejecuta cuando la versión de la base de datos cambia.
    //Aquí se maneja la lógica de actualización de la estructura de la BD.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(newVersion>oldVersion){
            val borrarTabla="drop table ${Aplicacion.TABLA};"
            db?.execSQL(borrarTabla)
            onCreate(db)
        }
    }
}