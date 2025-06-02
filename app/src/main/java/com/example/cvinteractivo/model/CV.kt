package com.example.cvinteractivo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CV(
    var nombre: String = "",
    var titulo: String = "",
    var fotoUri: String = "",
    var experiencias: MutableList<Experiencia> = mutableListOf(),
    var estudios: MutableList<Estudio> = mutableListOf()
) : Parcelable