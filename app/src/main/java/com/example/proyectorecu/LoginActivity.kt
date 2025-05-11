package com.example.proyectorecu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.example.proyectorecu.databinding.LoginLayoutBinding
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    // ViewBinding para acceder a los elementos del layout sin usar findViewById
    private lateinit var binding: LoginLayoutBinding

    // Instancia de Firebase Authentication
    private lateinit var auth: FirebaseAuth

    // Variables para almacenar email y contraseña ingresados por el usuario
    private var email = ""
    private var pass = ""

    // ActivityResultLauncher para manejar la autenticación con Google
    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                // Obtiene los datos de la cuenta de Google
                val datos = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val cuenta = datos.getResult(ApiException::class.java)
                    if (cuenta != null) {
                        // Obtiene las credenciales de autenticación de Google
                        val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credenciales)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this, DrawerActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                    }
                } catch (e: ApiException) {
                    Log.d("ERROR de API:>>>>", e.message.toString())
                }
            }
            if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "El usuario canceló", Toast.LENGTH_SHORT).show()
            }
        }

    // Método para iniciar sesión con Google
    private fun loginGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        googleClient.signOut() //Fundamental para que no haga login automatico si he cerrado session

        responseLauncher.launch(googleClient.signInIntent)
    }

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configura el ViewBinding
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajusta el padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.login) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa Firebase Auth
        auth = Firebase.auth

        // Configura el botón Limpiar
        binding.limpiar.setOnClickListener {
            limpiarCampos()
        }

        // Configura el botón Iniciar Sesión
        binding.iniciarsesion.setOnClickListener {
            login()
        }

        binding.registrar.setOnClickListener {
            registrar()
        }

        binding.btnGoogleSignIn.setOnClickListener {
            loginGoogle()
        }
    }

    //----------------------------------------------------------------------------------------------

    // Método para registrar un nuevo usuario en Firebase Authentication
    private fun registrar() {
        if (!datosCorrectos()) return
        //datos correctos, procedemos a registar al usuario
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //si el usuario se ha creado vamos a iniciar sesion con el
                    login()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    //----------------------------------------------------------------------------------------------

    // Método para iniciar sesión con correo y contraseña en Firebase Authentication
    private fun login() {
        if (!datosCorrectos()) return
        //LOs datos ya estan validados
        //vamos a logear al usuario
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //todo ha ido bien
                    irActivityDrawer()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
    //----------------------------------------------------------------------------------------------
    // Método para validar que el email y la contraseña sean correctos
    private fun datosCorrectos(): Boolean {
        email = binding.usuario.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usuario.error = "Se esperaba una direccion de email correcta."
            return false
        }
        pass = binding.password.text.toString().trim()
        if (pass.length < 6) {
            binding.password.error = "Error, la contraseña debe tener al menos 6 caracteres"
            return false
        }
        return true
    }
    //----------------------------------------------------------------------------------------------

    // Método para limpiar los campos
    private fun limpiarCampos() {
        binding.usuario.setText("")
        binding.password.setText("")
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show()
    }

    // Método para validar los campos
    private fun validarCampos() {
        val usuario = binding.usuario.text.toString().trim()
        val contrasena = binding.password.text.toString().trim()

        var isValid = true

        if (usuario.isEmpty()) {
            binding.usuario.error = "El campo Usuario es obligatorio"
            isValid = false
        } else {
            binding.usuario.error = null // Limpiar el error
        }

        if (contrasena.isEmpty()) {
            binding.password.error = "El campo Contraseña es obligatorio"
            isValid = false
        } else {
            binding.password.error = null // Limpiar el error
        }

        if (isValid) {
            // Si la validación es exitosa, navegar al layout del drawer
            irActivityDrawer()
        }
    }

    private fun irActivityDrawer() {
        startActivity(Intent(this, DrawerActivity::class.java))
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            irActivityDrawer()
        }
    }
}
