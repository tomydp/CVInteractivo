package com.example.cvinteractivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cvinteractivo.model.CV
import com.example.cvinteractivo.model.Experiencia

class ExperiencieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiencie)

        val editTextEmpresa = findViewById<EditText>(R.id.editTextEmpresa)
        val editTextPuesto = findViewById<EditText>(R.id.editTextPuesto)
        val editTextAnios = findViewById<EditText>(R.id.editTextAnios)
        val buttonContinuar = findViewById<Button>(R.id.buttonContinuar)

        // Obtener CV desde el intent
        @Suppress("DEPRECATION")
        val cv = intent.getParcelableExtra<CV>("cv_data") ?: CV()

        buttonContinuar.setOnClickListener {
            val empresa = editTextEmpresa.text.toString().trim()
            val puesto = editTextPuesto.text.toString().trim()
            val anios = editTextAnios.text.toString().trim()

            if (empresa.isEmpty() || puesto.isEmpty() || anios.isEmpty()) {
                Toast.makeText(this, "Por favor completá todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Agregar la experiencia al CV
            val experiencia = Experiencia(empresa, puesto, anios)
            cv.experiencias.add(experiencia)

            // Ir a la siguiente pantalla
            val intent = Intent(this, EducationSkillsActivity::class.java)
            intent.putExtra("cv_data", cv)
            startActivity(intent)
        }
    }
}
