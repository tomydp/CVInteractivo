package com.example.cvinteractivo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvinteractivo.model.CV

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextTitulo: EditText
    private lateinit var imageViewFoto: ImageView
    private lateinit var buttonContinuar: Button
    private var uriFoto: Uri? = null

    // Launcher para abrir la galería y seleccionar imagen
    private val seleccionarImagen =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                uriFoto = uri
                imageViewFoto.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge si lo usas (opcional)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajuste de insets (opcional si usas Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1) Referencias a las vistas
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextTitulo = findViewById(R.id.editTextTitulo)
        imageViewFoto   = findViewById(R.id.imageViewFoto)
        buttonContinuar = findViewById(R.id.buttonContinuar)

        // 2) Al tocar la ImageView, abrimos la galería
        imageViewFoto.setOnClickListener {
            // Filtramos únicamente imágenes ("image/*")
            seleccionarImagen.launch("image/*")
        }

        // 3) Al tocar “Continuar”, validamos datos y enviamos CV a ViewActivity
        buttonContinuar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val titulo = editTextTitulo.text.toString().trim()

            // Validamos que el usuario haya completado todo
            if (nombre.isEmpty() || titulo.isEmpty() || uriFoto == null) {
                Toast.makeText(
                    this,
                    "Por favor completa todos los campos y selecciona una foto.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Construimos el objeto CV (solo nombre, título y fotoUri)
            val cv = CV(
                nombre = nombre,
                titulo = titulo,
                fotoUri = uriFoto.toString()
            )

            // Enviamos el CV parcelable a ViewActivity
            val intent = Intent(this, ViewActivity::class.java).apply {
                putExtra("cv_data", cv)
            }
            startActivity(intent)
        }
    }
}