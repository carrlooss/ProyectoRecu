package com.example.proyectorecu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.adapters.NotificacionAdapter
import com.example.proyectorecu.databinding.AppBinding
import com.example.proyectorecu.models.CompraActivity
import com.example.proyectorecu.models.PexelsResponse
import com.example.proyectorecu.providers.db.PhotoAdapter
import com.example.proyectorecu.providers.db.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrawerActivity : AppCompatActivity() {

    // View Binding para acceder a los elementos del layout sin usar findViewById
    private lateinit var binding: AppBinding

    // Instancia de FirebaseAuth para gestionar la autenticación de usuarios
    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa View Binding
        binding = AppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de padding para ajustes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(binding.appNew) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.popularView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.api.getCuratedPhotos().enqueue(object : Callback<PexelsResponse> {
            override fun onResponse(call: Call<PexelsResponse>, response: Response<PexelsResponse>) {
                if (response.isSuccessful) {
                    val photos = response.body()?.photos ?: emptyList()
                    recyclerView.adapter = PhotoAdapter(photos)
                } else {
                    Log.e("API", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PexelsResponse>, t: Throwable) {
                Log.e("API", "Failure: ${t.message}")
            }
        })

        val editTextSearch = findViewById<EditText>(R.id.editTextTextSearch)
        val textViewContent = findViewById<TextView>(R.id.textViewPanel)

        val originalText = textViewContent.text.toString()

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                val spannableString = SpannableString(originalText)

                if (searchText.isNotEmpty()) {
                    var index = originalText.indexOf(searchText, 0, true)
                    while (index >= 0) {
                        spannableString.setSpan(
                            BackgroundColorSpan(Color.YELLOW),
                            index,
                            index + searchText.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        index = originalText.indexOf(searchText, index + searchText.length, true)
                    }
                }

                textViewContent.text = spannableString
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        // Inicializa el Firebase Auth
        auth = Firebase.auth


        // Configuración de los botones de la interfaz para navegar a otras actividades

        binding.mapa.setOnClickListener {
            irActivityMapa()
        }
        binding.popularView.setOnClickListener {
            irActivityApi()
        }

        binding.buttonJuego.setOnClickListener {
            irActivityFragment()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.boton_1 -> {
                    startActivity(Intent(this, VideoActivity::class.java))
                    true
                }
                R.id.boton_2 -> {
                    startActivity(Intent(this, PreferenciasActivity::class.java))
                    true
                }
                R.id.boton_3 -> {
                    startActivity(Intent(this, VideoActivity::class.java))
                    true
                }
                R.id.boton_cuenta -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }

        binding.buttonPanel.setOnClickListener{
            irActivityLista()
        }


        binding.sensores.setOnClickListener{
            irActivitySensor()
        }

        binding.ideas.setOnClickListener{
            irActivityTarea()
        }


        binding.notificacion.setOnClickListener{
            logout()
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
    private fun irActivityNotificacion() {
        val i=Intent(this, NotificacionAdapter::class.java).apply {}
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