package com.example.proyectorecu

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectorecu.databinding.PreferenciasLayoutBinding

class PreferenciasActivity : AppCompatActivity() {

    private lateinit var binding: PreferenciasLayoutBinding

    private var preferenciasName: String = "MisPreferencias"
    private var idiomaPosition: Int = 0
    private var seekValue: Int = 0

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configura ViewBinding
        binding = PreferenciasLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajusta el padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.preferencias) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Cargar valores guardados en SharedPreferences
        cargarPreferencias()


        // Manejar la selección de opciones en el Spinner
        binding.spinnerOptions.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                idiomaPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        //Configuracion del seekBar
        var seek = binding.seekBar
        seek.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    binding.seekBarValue.text = progress.toString()
                    seekValue = progress
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            }
        )

        // Guarda las preferencias cuando se presiona el botón
        binding.saveButton.setOnClickListener {
            guardarPreferencias()
        }
    }

    // Carga las preferencias guardadas en SharedPreferences
    fun cargarPreferencias(){
        val sharedPreferences = getSharedPreferences(preferenciasName, Context.MODE_PRIVATE)
        idiomaPosition = sharedPreferences.getInt("idioma", 0)
        seekValue = sharedPreferences.getInt("seek", 0)
        ponerValores()
    }

    // Aplica los valores cargados en la interfaz
    fun ponerValores(){
        binding.spinnerOptions.setSelection(idiomaPosition)
        binding.seekBar.progress = seekValue
        binding.seekBarValue.text = seekValue.toString()
    }

    // Guardar las preferencias en SharedPreferences
    fun guardarPreferencias(){
        val sharedPreferences = getSharedPreferences(preferenciasName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("idioma", idiomaPosition)
        editor.putInt("seek", seekValue)
        editor.apply()
        finish()
    }
}