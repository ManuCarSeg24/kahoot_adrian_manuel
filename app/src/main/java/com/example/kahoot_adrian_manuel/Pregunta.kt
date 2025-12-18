package com.example.kahoot_adrian_manuel.model

data class Pregunta(
    val id: Long,
    val texto: String,
    val respuestas: List<Respuesta>
)
