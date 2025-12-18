package com.example.kahoot_adrian_manuel

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kahoot_adrian_manuel.model.Pregunta
import com.example.kahoot_adrian_manuel.model.Respuesta

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, "kahoot.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE preguntas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                texto TEXT NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE respuestas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_pregunta INTEGER,
                texto TEXT,
                es_correcta INTEGER
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS respuestas")
        db.execSQL("DROP TABLE IF EXISTS preguntas")
        onCreate(db)
    }

    fun insertarPreguntaConRespuestas(
        pregunta: String,
        respuestas: List<String>,
        correcta: Int
    ): Boolean {
        val db = writableDatabase
        db.beginTransaction()
        return try {
            val stmt = db.compileStatement(
                "INSERT INTO preguntas (texto) VALUES (?)"
            )
            stmt.bindString(1, pregunta)
            val idPregunta = stmt.executeInsert()

            respuestas.forEachIndexed { i, texto ->
                val st = db.compileStatement(
                    "INSERT INTO respuestas (id_pregunta, texto, es_correcta) VALUES (?, ?, ?)"
                )
                st.bindLong(1, idPregunta)
                st.bindString(2, texto)
                st.bindLong(3, if (i + 1 == correcta) 1 else 0)
                st.executeInsert()
            }

            db.setTransactionSuccessful()
            true
        } catch (e: Exception) {
            false
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun contarPreguntas(): Int {
        val db = readableDatabase
        val c = db.rawQuery("SELECT COUNT(*) FROM preguntas", null)
        c.moveToFirst()
        val total = c.getInt(0)
        c.close()
        db.close()
        return total
    }

    fun obtener5Preguntas(): List<Pregunta> {
        val lista = mutableListOf<Pregunta>()
        val db = readableDatabase

        val c = db.rawQuery(
            "SELECT id, texto FROM preguntas ORDER BY RANDOM() LIMIT 5",
            null
        )

        while (c.moveToNext()) {
            val id = c.getLong(0)
            val texto = c.getString(1)

            val respuestas = mutableListOf<Respuesta>()
            val cr = db.rawQuery(
                "SELECT texto, es_correcta FROM respuestas WHERE id_pregunta = ?",
                arrayOf(id.toString())
            )

            while (cr.moveToNext()) {
                respuestas.add(
                    Respuesta(
                        cr.getString(0),
                        cr.getInt(1) == 1
                    )
                )
            }
            cr.close()

            lista.add(Pregunta(id, texto, respuestas))
        }

        c.close()
        db.close()
        return lista
    }
}
