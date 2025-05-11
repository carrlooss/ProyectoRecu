package com.example.proyectorecu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectorecu.databinding.DrawerLayoutBinding
import com.example.proyectorecu.models.CompraActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DrawerActivity : AppCompatActivity() {

    // View Binding para acceder a los elementos del layout sin usar findViewById
    private lateinit var binding: DrawerLayoutBinding

    // Instancia de FirebaseAuth para gestionar la autenticación de usuarios
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa View Binding
        binding =DrawerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de padding para ajustes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el Firebase Auth
        auth = Firebase.auth

        // Configuración de los botones de la interfaz para navegar a otras actividades
        binding.botonPerfil.setOnClickListener {
            irActivityPerfil()
        }
        binding.botonMapa.setOnClickListener {
            irActivityMapa()
        }
        binding.buttonApi.setOnClickListener {
            irActivityApi()
        }
        binding.buttonPreferencias.setOnClickListener {
            irActivityPreferencias()
        }

        binding.botonFragment.setOnClickListener {
            irActivityFragment()
        }

        binding.btnLogout.setOnClickListener{
            logout()
        }

        binding.buttonLista.setOnClickListener{
            irActivityLista()
        }

        binding.buttonSensor.setOnClickListener{
            irActivitySensor()
        }

        binding.buttonVideo.setOnClickListener{
            irActivityAudio()
        }

        binding.botonGestionTareas.setOnClickListener{
            irActivityTarea()
        }
    }

    // Métodos para cambiar de actividad según el botón presionado
    private fun irActivityMapa() {
        startActivity(Intent(this, MapaActivity::class.java))
    }

    private fun irActivityPerfil() {
        val i=Intent(this, PerfilActivity::class.java).apply {}
        startActivity(i)
    }

    private fun irActivityApi() {
        val i=Intent(this, ApiActivity::class.java).apply {}
        startActivity(i)
    }
    private fun irActivityPreferencias(){
        val i=Intent(this, PreferenciasActivity::class.java).apply {}
        startActivity(i)
    }

    private fun irActivityFragment(){
        val i=Intent(this, FragmentActiity::class.java).apply {}
        startActivity(i)
    }

    private fun irActivityLista(){
        val i=Intent(this, CompraActivity::class.java).apply {}
        startActivity(i)
    }

    private fun irActivitySensor(){
        val i=Intent(this, SensorActivity::class.java).apply { }
        startActivity(i)
    }

    private fun irActivityAudio(){
        val i=Intent(this, VideoActivity::class.java).apply {}
        startActivity(i)
    }

    private fun irActivityTarea(){
        val i=Intent(this, MainActivity::class.java).apply {}
        startActivity(i)
    }


    // Método para cerrar sesión en Firebase y volver a la pantalla de login
    fun logout() {
        // Cerrar sesión
        auth.signOut()
        val i=Intent(this, LoginActivity::class.java).apply {}
        startActivity(i)
    }
}