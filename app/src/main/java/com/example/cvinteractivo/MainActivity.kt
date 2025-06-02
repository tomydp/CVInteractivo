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

    // Launcher para abrir la galería
    private val seleccionarImagen =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uriFoto = it
                imageViewFoto.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom)
            insets
        }

        editTextNombre  = findViewById(R.id.editTextNombre)
        editTextTitulo  = findViewById(R.id.editTextTitulo)
        imageViewFoto   = findViewById(R.id.imageViewFoto)
        buttonContinuar = findViewById(R.id.buttonContinuar)

        imageViewFoto.setOnClickListener { seleccionarImagen.launch("image/*") }

        buttonContinuar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val titulo = editTextTitulo.text.toString().trim()

            if (nombre.isEmpty() || titulo.isEmpty() || uriFoto == null) {
                Toast.makeText(this, "Completa todos los campos y selecciona foto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Creamos el CV con los datos básicos
            val cv = CV(nombre = nombre, titulo = titulo, fotoUri = uriFoto.toString())

            val intent = Intent(this, ExperiencieActivity::class.java).apply {
                putExtra("cv_data", cv)
            }
            startActivity(intent)
        }
    }
}
