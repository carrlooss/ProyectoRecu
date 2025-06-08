package com.example.proyectorecu.providers.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.proyectorecu.Aplicacion
import com.example.proyectorecu.models.TareaModel

//Clase que gestiona las operaciones CRUD de la tabla de tareas en la base de datos SQLite.
class CrudTareas {

    //Inserta una nueva tarea en la base de datos.
    fun create(t: TareaModel): Long{
        val con=Aplicacion.llave.writableDatabase //abrimos la bbdd en modo escritura
        return try{
            con.insertWithOnConflict(
                Aplicacion.TABLA,
                null,
                t.toContentValues(),
                SQLiteDatabase.CONFLICT_IGNORE
            )
        }catch(ex: Exception){
            ex.printStackTrace()
            -1L
        }finally {
            con.close()
        }
    }

    //Obtiene todas las tareas almacenadas en la base de datos.
    fun read(): MutableList<TareaModel>{
        val lista = mutableListOf<TareaModel>()
        val version = Aplicacion.VERSION
        val con =Aplicacion.llave.readableDatabase
        try{
            val cursor=con.query(
                Aplicacion.TABLA,
                arrayOf("id", "tipo", "nombre", "descripcion", "realizado"),
                null,
                null,
                null,
                null,
                null
            )
            while(cursor.moveToNext()){
                val tarea=TareaModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) == 1
                )
                lista.add(tarea)
            }
        }catch(ex: Exception){
            ex.printStackTrace()
        }finally {
            con.close()
        }
        return lista
    }

    //Busca tareas cuyo nombre coincida con el texto proporcionado.
    public fun searchItems(search: String): MutableList<TareaModel>{
        val lista = mutableListOf<TareaModel>()
        val con =Aplicacion.llave.readableDatabase
        try{
            val cursor=con.query(
                Aplicacion.TABLA,
                arrayOf("id", "tipo", "nombre", "descripcion", "realizado"),
                "nombre LIKE ?",
                arrayOf("%$search%"),
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                while(cursor.moveToNext()){
                    val tarea=TareaModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1
                    )
                    lista.add(tarea)
                }
            }
        }catch(ex: Exception){
            ex.printStackTrace()
        }finally {
            con.close()
        }
        return lista
    }

    //Obtiene el número total de registros en la tabla de tareas.
    public fun registrosTotales(): Int{
        var registros: Int = 0
        val con =Aplicacion.llave.readableDatabase
        try{
            val cursor=con.rawQuery(
                "SELECT COUNT(*) AS registros FROM " + Aplicacion.TABLA,
                null
            )

            if (cursor.moveToFirst()) {
                registros = cursor.getInt(0)
            }
        }catch(ex: Exception){
            ex.printStackTrace()
        }finally {
            con.close()
        }
        return registros
    }

    //Elimina una tarea de la base de datos según su ID.
    public fun borrar(id: Int): Boolean{
        val con=Aplicacion.llave.writableDatabase
        val tareaBorrado=con.delete(Aplicacion.TABLA, "id=?", arrayOf(id.toString()))
        con.close()
        return tareaBorrado>0
    }

    //Actualiza los datos de una tarea existente en la base de datos.
    public fun update(t: TareaModel): Boolean{
        val con = Aplicacion.llave.writableDatabase
        val values=t.toContentValues()
        var filasAfectadas=0
        filasAfectadas=con.update(Aplicacion.TABLA, values, "id=?", arrayOf(t.id.toString()))
        con.close()
        return  filasAfectadas>0
    }

    //Elimina todas las tareas almacenadas en la base de datos.
    public fun borrarTodo(){
        val con=Aplicacion.llave.writableDatabase
        con.execSQL("delete from ${Aplicacion.TABLA}")
        con.close()
    }

    //Convierte un objeto TareaModel en un objeto ContentValues para operaciones en SQLite.
    private fun TareaModel.toContentValues(): ContentValues{
        return  ContentValues().apply {
            put("tipo", tipo)
            put("nombre", nombre)
            put("descripcion", descripcion)
            put("realizado", realizado)
        }
    }
}