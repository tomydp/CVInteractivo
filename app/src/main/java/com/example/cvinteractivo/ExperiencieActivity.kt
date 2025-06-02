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

    private lateinit var editTextEmpresa: EditText
    private lateinit var editTextPuesto: EditText
    private lateinit var editTextAnios:  EditText
    private lateinit var buttonContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiencie)

        editTextEmpresa   = findViewById(R.id.editTextEmpresa)
        editTextPuesto    = findViewById(R.id.editTextPuesto)
        editTextAnios     = findViewById(R.id.editTextAnios)
        buttonContinuar   = findViewById(R.id.buttonContinuar)

        @Suppress("DEPRECATION")
        val cv = intent.getParcelableExtra<CV>("cv_data") ?: CV()

        buttonContinuar.setOnClickListener {
            val empresa = editTextEmpresa.text.toString().trim()
            val puesto  = editTextPuesto.text.toString().trim()
            val anios   = editTextAnios.text.toString().trim()

            if (empresa.isEmpty() || puesto.isEmpty() || anios.isEmpty()) {
                Toast.makeText(this, "Completa los tres campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cv.experiencias.add(Experiencia(empresa, puesto, anios))

            Intent(this, EducationSkillsActivity::class.java).apply {
                putExtra("cv_data", cv)
                startActivity(this)
            }
        }
    }
}
