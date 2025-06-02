package com.example.cvinteractivo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Representa un registro de estudios/simple (institución, título y años)
 */
@Parcelize
data class Estudio(
    val institucion: String,
    val titulo: String,
    val años: String
) : Parcelable
