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
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            insets
        }

        // 1) Recuperar el CV
        @Suppress("DEPRECATION")
        val cv = intent.getParcelableExtra<CV>("cv_data") ?: CV()

        // 2) Referencias a vistas
        val imgFoto   = findViewById<ImageView>(R.id.imgFoto)
        val txtNom    = findViewById<TextView>(R.id.txtNombre)
        val txtTit    = findViewById<TextView>(R.id.txtTitulo)
        val txtExp    = findViewById<TextView>(R.id.txtExperiencias)
        val txtEdu    = findViewById<TextView>(R.id.txtEstudios)
        val btnShare  = findViewById<Button>(R.id.btnCompartir)

        // 3) Cargar datos básicos
        txtNom.text = cv.nombre
        txtTit.text = cv.titulo
        if (cv.fotoUri.isNotEmpty()) {
            imgFoto.setImageURI(Uri.parse(cv.fotoUri))
        } else {
            imgFoto.setImageResource(R.drawable.baseline_person_24)
        }

        // 4) Construir texto de experiencias
        txtExp.text = if (cv.experiencias.isEmpty()) {
            "• Sin datos"
        } else {
            cv.experiencias.joinToString("\n") { exp ->
                "• ${exp.empresa} – ${exp.puesto} (${exp.anios})"
            }
        }

        // 5) Construir texto de estudios
        txtEdu.text = if (cv.estudios.isEmpty()) {
            "• Sin datos"
        } else {
            cv.estudios.joinToString("\n") { est ->
                "• ${est.institucion} – ${est.titulo} (${est.años})"
            }
        }

        // 6) Botón compartir
        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${cv.nombre} – ${cv.titulo}\n\nExperiencia:\n${txtExp.text}\n\nEducación:\n${txtEdu.text}"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir CV"))
        }
    }
}
