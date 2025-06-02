package com.example.cvinteractivo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cvinteractivo.model.Experiencia

class ExperienciaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Apunta al XML que acabamos de crear
        setContentView(R.layout.activity_experiencie)

        // Referencias a los TextViews y al botón
        val tvEmpresa  = findViewById<TextView>(R.id.tvEmpresa)
        val tvPuesto   = findViewById<TextView>(R.id.tvPuesto)
        val tvAnios    = findViewById<TextView>(R.id.tvAnios)
        val buttonVolver = findViewById<Button>(R.id.buttonVolver)

        // Intent: intent.getParcelableExtra("experiencia") buscará
        // un objeto Experiencia pasado desde la actividad anterior
        val experiencia: Experiencia? = intent.getParcelableExtra("experiencia")

        if (experiencia != null) {
            // Si llegó un objeto Experiencia, lo mostramos
            tvEmpresa.text = "Empresa: ${experiencia.empresa}"
            tvPuesto.text  = "Puesto: ${experiencia.puesto}"
            tvAnios.text   = "Años: ${experiencia.anios}"
        } else {
            // Si no llegó nada, ponemos valores por defecto
            tvEmpresa.text = "Empresa: N/A"
            tvPuesto.text  = "Puesto: N/A"
            tvAnios.text   = "Años: N/A"
        }

        // Cuando el usuario toque “Volver”, cerramos esta Activity
        buttonVolver.setOnClickListener {
            finish()
        }
    }
}
