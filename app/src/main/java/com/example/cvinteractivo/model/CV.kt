package com.example.cvinteractivo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CV(
    var nombre: String = "",
    var titulo: String = "",
    var fotoUri: String = ""
) : Parcelable