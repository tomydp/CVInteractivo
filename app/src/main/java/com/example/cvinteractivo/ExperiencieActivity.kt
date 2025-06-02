package com.example.cvinteractivo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.cvinteractivo.model.CV
import com.example.cvinteractivo.model.Experiencia

class ExperiencieActivity : AppCompatActivity() {

    // Suponiendo que en tu XML tienes:
    //   - Un TextView con id @+id/tvEmpresa
    //   - Un TextView con id @+id/tvPuesto
    //   - Un TextView con id @+id/tvAnios
    //   - Un Button con id @+id/buttonVolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Asegúrate de que el layout se llame EXACTAMENTE así en tu carpeta res/layout
        setContentView(R.layout.activity_experiencie)

        // 1) Recupero el objeto CV
        @Suppress("DEPRECATION")
        val cv: CV = intent.getParcelableExtra<CV>("cv_data") ?: run {
            Toast.makeText(this, "No hay datos de CV", Toast.LENGTH_SHORT).show()
            finish()
            CV()
        }

        // 2) Referencias a widgets
        val tvEmpresa   = findViewById<TextView>(R.id.tvEmpresa)
        val tvPuesto    = findViewById<TextView>(R.id.tvPuesto)
        val tvAnios     = findViewById<TextView>(R.id.tvAnios)
        val buttonVolver = findViewById<Button>(R.id.buttonVolver)

        // 3) Mostrar la PRIMERA experiencia (o “sin datos” si la lista está vacía)
        if (cv.experiencias.isEmpty()) {
            tvEmpresa.text = "Empresa: N/A"
            tvPuesto.text  = "Puesto: N/A"
            tvAnios.text   = "Años: N/A"
        } else {
            val primeraExp = cv.experiencias[0]
            tvEmpresa.text = "Empresa: ${primeraExp.empresa}"
            tvPuesto.text  = "Puesto: ${primeraExp.puesto}"
            tvAnios.text   = "Años: ${primeraExp.anios}"
        }

        // 4) El botón “Volver” cierra la Activity
        buttonVolver.setOnClickListener {
            finish()
        }
    }
}
