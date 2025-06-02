package com.example.cvinteractivo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Representa una experiencia laboral simple
 */
@Parcelize
data class Experiencia(
    val empresa: String,
    val puesto: String,
    val anios: String
) : Parcelable
