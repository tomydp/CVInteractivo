package com.example.cvinteractivo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvinteractivo.model.CV

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Recibe el objeto CV (gracias a @Parcelize)
        @Suppress("DEPRECATION")
        val cv: CV = intent.getParcelableExtra<CV>("cv_data") ?: run {
            // Si no viene cv_data en el Intent, cerramos la Activity
            finish()
            CV()
        }

        val imgFoto       = findViewById<ImageView>(R.id.imgFoto)
        val txtNombre     = findViewById<TextView>(R.id.txtNombre)
        val txtTitulo     = findViewById<TextView>(R.id.txtTitulo)
        val btnCompartir  = findViewById<Button>(R.id.btnCompartir)

        // Mostrar nombre y título
        txtNombre.text = cv.nombre
        txtTitulo.text = cv.titulo

        // Cargar la foto en el ImageView
        if (cv.fotoUri.isNotEmpty()) {
            try {
                val uri = Uri.parse(cv.fotoUri)
                imgFoto.setImageURI(uri)
            } catch (e: Exception) {
                imgFoto.setImageResource(R.drawable.baseline_person_24)
            }
        } else {
            imgFoto.setImageResource(R.drawable.baseline_person_24)
        }

        // Acción del botón “Compartir CV”
        btnCompartir.setOnClickListener {
            val contenido = "${cv.nombre} – ${cv.titulo}"
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, contenido)
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir CV"))
        }
    }
}