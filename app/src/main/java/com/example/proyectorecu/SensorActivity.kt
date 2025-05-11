package com.example.proyectorecu

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectorecu.databinding.SensorLuzBinding

class SensorActivity : AppCompatActivity(), SensorEventListener {
    // ViewBinding para acceder a los elementos del layout

    private lateinit var binding: SensorLuzBinding
    // Administrador de sensores
    private lateinit var sensorManager: SensorManager

    // Sensores utilizados
    private var acelerometro: Sensor? = null
    private var giroscopio: Sensor? = null
    private var sensorLuz: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SensorLuzBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste de los márgenes para evitar superposición con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el administrador de sensores
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager

        // Configura los listeners de los botones
        setListeners()

        //Los inicializa
        iniciarSensores()
    }

    //Obtiene las instancias de los sensores disponibles en el dispositivo
    private fun iniciarSensores() {
        acelerometro=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        giroscopio=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorLuz=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }


    //Configura los listeners de los botones de la interfaz de usuario
    private fun setListeners() {
        binding.btnSalir.setOnClickListener {
            finish()
        }
    }


    //Detener la escucha de los botones si fuera necesario
    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        // Reactiva la escucha de los sensores al volver a la actividad
        ponerListenerSensores(acelerometro)
        ponerListenerSensores(giroscopio)
        ponerListenerSensores(sensorLuz)
    }


    //Registra un listener para el sensor si está disponible
    private fun ponerListenerSensores(sensor: Sensor?){
        if (sensor!=null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }


    //Se ejecuta cuando se detecta un cambio en alguno de los sensores registrados
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.values?.isNotEmpty() == true) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {

                    // Muestra los valores del acelerómetro en la interfaz
                    pintarValores(binding.tvResXAce, event.values[0])
                    pintarValores(binding.tvResYAce, event.values[1])
                    pintarValores(binding.tvResZAce, event.values[2])
                }
                Sensor.TYPE_GYROSCOPE -> {

                    // Muestra los valores del giroscopio en la interfaz
                    pintarValores(binding.tvResXGir, event.values[0])
                    pintarValores(binding.tvResYGir, event.values[1])
                    pintarValores(binding.tvResZGir, event.values[2])
                }
                Sensor.TYPE_LIGHT -> {

                    // Muestra la intensidad de luz en la interfaz
                    pintarValores(binding.tvResLuz, event.values[0])
                }
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    //Muestra un valor numérico en un TextView con formato de tres decimales
    private fun pintarValores(tv: TextView, valor: Float){
        val v=String.format("%.3f",valor)
        tv.text=v

    }
}