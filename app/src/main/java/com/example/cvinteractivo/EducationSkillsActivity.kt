package com.example.cvinteractivo

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cvinteractivo.model.Estudio
import com.example.cvinteractivo.model.Experiencia
import com.example.cvinteractivo.model.CV
import com.google.android.material.button.MaterialButton

/**
 * Activity donde:
 *  - El usuario ingresa UN solo estudio (Institución, Título y Años).
 *  - Pulsando “Continuar”:
 *      1) Se guarda ese estudio (si los campos están completos).
 *      2) Añade un TextView al contenedor para mostrarlo.
 *      3) Si hay al menos un estudio, crea el objeto CV completo (incluye experiencias previas y lista de estudios),
 *         envía ese CV a ViewActivity y navega.
 *  - TODO en un único layout sin usar RecyclerView/Adapter.
 */
class EducationSkillsActivity : AppCompatActivity() {

    // EditText de “Agregar Estudio”
    private lateinit var editTextInstitucion: EditText
    private lateinit var editTextTituloEstudio: EditText
    private lateinit var editTextAniosEstudio: EditText

    // Contenedor dinámico donde añadiremos un TextView por cada Estudio
    private lateinit var linearContainerEstudios: LinearLayout

    // Botón “Continuar”
    private lateinit var buttonContinuar: MaterialButton

    // Lista interna de Estudios (en memoria)
    private val listaEstudios = mutableListOf<Estudio>()

    // Datos que vienen desde ExperienciaActivity
    private var nombreUsuario: String = ""
    private var tituloProfesional: String = ""
    private var fotoUriString: String = ""
    private var listaExperiencias: ArrayList<Experiencia> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_skills)

        // Ajuste edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1) Recuperar datos enviados desde ExperienciaActivity
        recuperarDatosPrevios()

        // 2) Vincular vistas con sus IDs
        vincularVistas()

        // 3) Configurar el botón “Continuar”
        buttonContinuar.setOnClickListener {
            // a) Guardar el estudio que haya en los tres EditText (si corresponde)
            guardarEstudioActual()
            // b) Si hay al menos un estudio, construir CV y navegar
            irAVistaFinal()
        }
    }

    /**
     * Recupera del Intent las claves:
     *  - "nombre"
     *  - "titulo"
     *  - "fotoUri"
     *  - "listaExperiencias"
     */
    private fun recuperarDatosPrevios() {
        nombreUsuario = intent.getStringExtra("nombre") ?: ""
        tituloProfesional = intent.getStringExtra("titulo") ?: ""
        fotoUriString = intent.getStringExtra("fotoUri") ?: ""

        @Suppress("UNCHECKED_CAST")
        listaExperiencias = intent
            .getParcelableArrayListExtra<Experiencia>("listaExperiencias")
            ?: arrayListOf()

        // Si faltan datos previos, mostrar advertencia (opcional cerrar Activity)
        if (nombreUsuario.isEmpty() || tituloProfesional.isEmpty() || fotoUriString.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_datos_previos), Toast.LENGTH_LONG).show()
        }
    }

    /** Encuentra cada vista del XML mediante findViewById. */
    private fun vincularVistas() {
        editTextInstitucion    = findViewById(R.id.editTextInstitucion)
        editTextTituloEstudio  = findViewById(R.id.editTextTituloEstudio)
        editTextAniosEstudio   = findViewById(R.id.editTextAniosEstudio)

        linearContainerEstudios = findViewById(R.id.linearContainerEstudios)
        buttonContinuar         = findViewById(R.id.buttonContinuarEducacion)
    }

    /**
     * 1) Toma los valores de los tres EditText.
     * 2) Si los tres campos están vacíos => retorna sin mostrar error (el usuario ya agregó antes).
     * 3) Si alguno está vacío (pero no todos) => muestra Toast y retorna (no avanza).
     * 4) Si los tres están completos => crea un Estudio, lo guarda en listaEstudios y añade un TextView al contenedor.
     */
    private fun guardarEstudioActual() {
        val institucion = editTextInstitucion.text.toString().trim()
        val titulo      = editTextTituloEstudio.text.toString().trim()
        val anios       = editTextAniosEstudio.text.toString().trim()

        // Caso (1): si los 3 campos vacíos -> no guardamos nada y retornamos
        if (institucion.isEmpty() && titulo.isEmpty() && anios.isEmpty()) {
            return
        }

        // Caso (3): si alguno está vacío -> mostramos Toast y retornamos
        if (institucion.isEmpty() || titulo.isEmpty() || anios.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_campos_vacios), Toast.LENGTH_SHORT).show()
            return
        }

        // Caso (4): todos completos -> creamos el objeto Estudio y lo agregamos a la lista
        val nuevoEstudio = Estudio(institucion, titulo, anios)
        listaEstudios.add(nuevoEstudio)

        // Construimos un nuevo TextView para representarlo en pantalla
        val tv = TextView(this).apply {
            // Por ejemplo: “• Universidad X – Lic. en Y (2019 - 2023)”
            text = "• $institucion – $titulo ($anios)"
            textSize = 16f
            setPadding(8, 8, 8, 8)
        }

        // Añadimos el TextView al contenedor vertical
        linearContainerEstudios.addView(
            tv,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        // Limpiamos los campos para que el usuario pueda agregar otro estudio
        editTextInstitucion.text?.clear()
        editTextTituloEstudio.text?.clear()
        editTextAniosEstudio.text?.clear()
    }

    /**
     * Verifica si la listaEstudios está vacía:
     *  - Si está vacía -> muestra Toast “Debes agregar al menos un estudio” y retorna.
     *  - Si contiene al menos un Estudio -> crea el objeto CV y abre ViewActivity.
     */
    private fun irAVistaFinal() {
        if (listaEstudios.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_sin_estudios), Toast.LENGTH_SHORT).show()
            return
        }

        // Construcción del CV (el modelo CV tiene “titulo” en lugar de “tituloProfesional”)
        val cvCompleto = CV(
            nombre       = nombreUsuario,
            titulo       = tituloProfesional,
            fotoUri      = fotoUriString,
            experiencias = listaExperiencias,
            estudios     = listaEstudios
        )

        // Enviamos con clave "cv_data" (consistente con ViewActivity)
        val intent = Intent(this, ViewActivity::class.java).apply {
            putExtra("cv_data", cvCompleto)
        }
        startActivity(intent)
        finish()
    }
}