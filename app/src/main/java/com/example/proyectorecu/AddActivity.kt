package com.example.proyectorecu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectorecu.databinding.ActivityAddBinding
import com.example.proyectorecu.models.TareaModel
import com.example.proyectorecu.providers.db.CrudTareas

class AddActivity : AppCompatActivity() {
    // Lista de tipos de tarea
    val tipos = listOf("Normal", "Importante")

    private lateinit var binding: ActivityAddBinding
    private var realizado : Boolean = false
    private var tipo: Int = 0
    private var nombre : String = ""
    private var descripcion = ""
    private var id=-1
    private var isUpdate=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Configura el viewBinding
        binding=ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Ajusta el padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Configura las opciones del Spinner (tipo de tarea)
        setOpcionesTipo()
        // Verifica si hay una tarea existente para editar
        recogerTarea()
        // Configura los listeners de los botones
        setListeners()
        if(isUpdate){
            binding.etTitle2.text="Editar Tarea"
            binding.btn2Enviar.text="EDITAR"
        }
    }

    //Configura el Spinner con las opciones "Normal" e "Importante"
    private fun setOpcionesTipo(){
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipoTarea.adapter = adapter
    }

    //Recoge los datos de una tarea si se pasa a través del Intent (para edición)

    private fun recogerTarea() {
        val datos=intent.extras
        if(datos!=null){
            val t = datos.getSerializable("TAREA") as TareaModel
            isUpdate=true
            realizado = t.realizado
            nombre = t.nombre
            descripcion = t.descripcion
            id = t.id

            pintarDatos()
        }
    }

    //Muestra los datos de la tarea en la interfaz
    private fun pintarDatos() {
        binding.cbRealizado.isChecked = realizado
        binding.spTipoTarea.setSelection(tipo)
        binding.etNombre.setText(nombre)
        binding.etDescripcion.setText(descripcion)
    }


    //Configura los eventos de los botones
    private fun setListeners() {
        binding.btnCancelar.setOnClickListener{
            finish()
        }
        binding.btn2Reset.setOnClickListener {
            limpiar()
        }
        binding.btn2Enviar.setOnClickListener {
            guardarRegistro()
        }
    }

    //Guarda la tarea en la base de datos (crea una nueva o edita una existente)
    private fun guardarRegistro() {
        if(datosCorrectos()){
            val t=TareaModel(id, tipo, nombre, descripcion, realizado)
            if(!isUpdate) {

                // Si es una nueva tarea, la inserta en la base de datos
                if (CrudTareas().create(t) != -1L) {
                    Toast.makeText(
                        this,
                        "Se ha añadido un registro a las tareas",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }else{

                // Si es una edición, actualiza la tarea existente
                if(CrudTareas().update(t)){
                    Toast.makeText(this, "Tarea Editada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    //Verifica que los datos sean correctos antes de guardar
    private fun datosCorrectos(): Boolean {
        tipo = binding.spTipoTarea.selectedItemPosition
        nombre=binding.etNombre.text.toString().trim()
        descripcion=binding.etDescripcion.text.toString().trim()
        realizado=binding.cbRealizado.isChecked

        // Validación: La descripción debe tener al menos 3 caracteres
        if(descripcion.length<3){
            binding.etDescripcion.error="El campo nombre debe tener al menos 3 caracteres"
            return false;
        }
        return true
    }


    //Limpia los campos del formulario
    private fun limpiar(){
        binding.spTipoTarea.setSelection(0)
        binding.etNombre.setText("")
        binding.etDescripcion.setText("")
        binding.cbRealizado.isChecked = false
    }
}