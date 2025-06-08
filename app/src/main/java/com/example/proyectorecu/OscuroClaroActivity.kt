package com.example.proyectorecu

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class OscuroClaroActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null
    private var lightLevel = 1000f
    private var currentLightLevel = 1000f

    override fun onCreate(savedInstanceState: Bundle?) {
        // Cargar tema antes del setContentView
        if (lightLevel < 50) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sensor setup
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor != null) {
            sensorManager!!.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    var isNightMode: Boolean = false

    override fun onSensorChanged(event: SensorEvent) {
        currentLightLevel = event.values[0]

        if (currentLightLevel < 50 && !isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            isNightMode = true
        } else if (currentLightLevel >= 50 && isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            isNightMode = false
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // No necesario en este caso
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager!!.unregisterListener(this)
    }
}