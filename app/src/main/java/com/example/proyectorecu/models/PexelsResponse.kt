package com.example.proyectorecu.models

data class PexelsResponse(
    val photos: List<Photo>
)

data class Photo(
    val id: Int,
    val photographer: String,
    val src: Src
)

data class Src(
    val medium: String,
    val original: String
)