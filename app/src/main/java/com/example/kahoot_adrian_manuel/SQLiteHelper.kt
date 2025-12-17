package com.example.kahoot_adrian_manuel

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, "kahoot.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        val crearPreguntas = """
            CREATE TABLE preguntas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                texto TEXT NOT NULL
            )
        """.trimIndent()

        val crearRespuestas = """
            CREATE TABLE respuestas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_pregunta INTEGER NOT NULL,
                texto TEXT NOT NULL,
                es_correcta INTEGER NOT NULL,
                FOREIGN KEY (id_pregunta) REFERENCES preguntas(id)
            )
        """.trimIndent()

        db.execSQL(crearPreguntas)
        db.execSQL(crearRespuestas)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS respuestas")
        db.execSQL("DROP TABLE IF EXISTS preguntas")
        onCreate(db)
    }

    // INSERTAR PREGUNTA + 4 RESPUESTAS
    fun insertarPreguntaConRespuestas(
        pregunta: String,
        respuestas: List<String>,
        correcta: Int
    ): Boolean {

        val db = writableDatabase
        db.beginTransaction()

        try {
            val stmtPregunta = db.compileStatement(
                "INSERT INTO preguntas (texto) VALUES (?)"
            )
            stmtPregunta.bindString(1, pregunta)
            val idPregunta = stmtPregunta.executeInsert()

            for (i in respuestas.indices) {
                val stmtRespuesta = db.compileStatement(
                    "INSERT INTO respuestas (id_pregunta, texto, es_correcta) VALUES (?, ?, ?)"
                )
                stmtRespuesta.bindLong(1, idPregunta)
                stmtRespuesta.bindString(2, respuestas[i])
                stmtRespuesta.bindLong(3, if (i + 1 == correcta) 1 else 0)
                stmtRespuesta.executeInsert()
            }

            db.setTransactionSuccessful()
            return true

        } catch (e: Exception) {
            return false
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}
