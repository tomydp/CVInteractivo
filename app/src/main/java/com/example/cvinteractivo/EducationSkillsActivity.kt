package com.example.cvinteractivo

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cvinteractivo.model.CV
import com.example.cvinteractivo.model.Estudio
import com.google.android.material.button.MaterialButton

class EducationSkillsActivity : AppCompatActivity() {

    // Vistas
    private lateinit var editTextInstitucion:   EditText
    private lateinit var editTextTituloEstudio: EditText
    private lateinit var editTextAniosEstudio:  EditText
    private lateinit var linearContainer:       LinearLayout
    private lateinit var buttonContinuar:       MaterialButton

    // Datos
    private lateinit var cv: CV
    private val listaEstudios = mutableListOf<Estudio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_skills)

        // Recuperamos el CV completo
        @Suppress("DEPRECATION")
        cv = intent.getParcelableExtra("cv_data") ?: CV()

        // Vincular vistas
        editTextInstitucion   = findViewById(R.id.editTextInstitucion)
        editTextTituloEstudio = findViewById(R.id.editTextTituloEstudio)
        editTextAniosEstudio  = findViewById(R.id.editTextAniosEstudio)
        linearContainer       = findViewById(R.id.linearContainerEstudios)
        buttonContinuar       = findViewById(R.id.buttonContinuarEducacion)

        buttonContinuar.setOnClickListener {
            guardarEstudioActual()
            if (listaEstudios.isEmpty()) {
                Toast.makeText(this, "Agrega al menos un estudio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cv.estudios = listaEstudios  // añadimos la lista al CV
            Intent(this, ViewActivity::class.java).apply {
                putExtra("cv_data", cv)
                startActivity(this)
            }
        }
    }

    private fun guardarEstudioActual() {
        val inst  = editTextInstitucion.text.toString().trim()
        val tit   = editTextTituloEstudio.text.toString().trim()
        val anios = editTextAniosEstudio.text.toString().trim()

        // si los tres están vacíos, no hacemos nada
        if (inst.isEmpty() && tit.isEmpty() && anios.isEmpty()) return

        if (inst.isEmpty() || tit.isEmpty() || anios.isEmpty()) {
            Toast.makeText(this, "Completa los tres campos del estudio", Toast.LENGTH_SHORT).show()
            return
        }

        val estudio = Estudio(inst, tit, anios)
        listaEstudios.add(estudio)

        // Mostramos un TextView con el estudio recién añadido
        val tv = TextView(this).apply {
            text = "• $inst – $tit ($anios)"
            textSize = 16f
            setPadding(8, 8, 8, 8)
        }
        linearContainer.addView(
            tv,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        )

        // Limpiamos campos
        editTextInstitucion.text?.clear()
        editTextTituloEstudio.text?.clear()
        editTextAniosEstudio.text?.clear()
    }
}
