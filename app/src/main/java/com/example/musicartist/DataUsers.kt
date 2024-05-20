package com.example.musicartist

import kotlinx.serialization.Serializable

@Serializable
    data class DataUser(val id:String="",
                        val Email:String="",
                        val Имя:String="")

