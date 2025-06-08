package com.example.proyectorecu

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlin.math.min


class MostrarLuminosidadActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null

    private var currentLightLevel = 1000f
    private var isNightMode = false

    private var textLux: TextView? = null
    private var progressLux: ProgressBar? = null
    private var iconLight: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        textLux = findViewById(R.id.textLux)
        progressLux = findViewById(R.id.progressLux)
        iconLight = findViewById(R.id.iconLight)

        // Sensor setup
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor != null) {
            sensorManager!!.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        currentLightLevel = event.values[0]

        // Actualiza el texto de lux
        textLux!!.text = String.format("Luminosidad: %.1f lux", currentLightLevel)

        // Actualiza la ProgressBar
        val progressValue = min(currentLightLevel.toDouble(), 1000.0) as Int
        progressLux!!.progress = progressValue

        // Cambia el icono, luna si es oscuro, sol si es claro
        if (currentLightLevel < 50 && !isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            iconLight!!.setImageResource(R.drawable.ic_moon)
            isNightMode = true
        } else if (currentLightLevel >= 50 && isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            iconLight!!.setImageResource(R.drawable.ic_sun)
            isNightMode = false
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // No necesario
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager!!.unregisterListener(this)
    }
}