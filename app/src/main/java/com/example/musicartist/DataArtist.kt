package com.example.musicartist

import kotlinx.serialization.Serializable
@Serializable
data class DataArtist(val id: Int? = 0, val Имя: String? = "", val Аватар: String? = "")