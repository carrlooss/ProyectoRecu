package com.example.proyectorecu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectorecu.databinding.PerfilLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: PerfilLayoutBinding

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val usuarioActual = auth.currentUser

    var email: String = ""
    var nombre: String = ""
    var direccion: String = ""
    var telefono: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el View Binding
        binding = PerfilLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de padding para ajustes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(binding.perfil) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recogerPerfil()
        setListeners()
    }

    //Recupera el perfil del usuario actual desde Firebase Authentication y Firestore
    private fun recogerPerfil() {
        val userId = usuarioActual?.uid

        email = usuarioActual?.email.toString()
        nombre = usuarioActual?.displayName.toString()

        // Si el usuario tiene un ID válido, obtiene información adicional desde Firestore
        if (userId != null) {
            db.collection("usuarios").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Accede a los campos del documento
                        direccion = document.getString("direccion").toString()
                        telefono = document.getString("telefono").toString()
                        pintarDatos()
                    }
                }
                .addOnFailureListener { exception ->

                }
        }
        pintarDatos()
    }

    //Muestra los datos obtenidos en los campos de la interfaz
    private fun pintarDatos() {
        binding.etEmail.setText(email)
        binding.etNombre.setText(nombre)
        binding.etDireccion.setText(direccion)
        binding.etTelefono.setText(telefono)
    }

    //Configura los listeners de los botones
    private fun setListeners() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btn2Guardar.setOnClickListener {
            guardarRegistro()
        }
    }

    //Valida los datos ingresados por el usuario
    private fun datosCorrectos(): Boolean {
        nombre=binding.etNombre.text.toString().trim()
        email=binding.etEmail.text.toString().trim()
        direccion=binding.etDireccion.text.toString().trim()
        telefono=binding.etTelefono.text.toString().trim()

        if(nombre.length<3){
            binding.etNombre.error="El campo nombre debe tener al menos 3 caracteres"
            return false;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Debes introducir un email válido"
            return false;
        }

        return true
    }

    // Guarda los cambios realizados en el perfil del usuario
    private fun guardarRegistro()
    {
        if(datosCorrectos()) {
            // Actualiza el Email
            usuarioActual?.updateEmail(email)
                ?.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        binding.etEmail.error = "Error actualizando el correo"
                    }
                }

            // Actualiza el Nombre
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(nombre)
                .build()

            usuarioActual?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //finish()
                    } else {
                        binding.etEmail.error = "Error actualizando el perfil"
                    }
                }

            // Actualiza los datos adicionales en Firestore
            val userId = usuarioActual?.uid
            val userData: Map<String, String> = mapOf(
                "direccion" to direccion,
                "telefono" to telefono
            )

            if (userId != null) {
                val docRef = db.collection("usuarios").document(userId)
                docRef
                    .update(userData)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener { e ->
                        docRef.set(userData)
                        finish()
                    }
            }
        }
    }
}