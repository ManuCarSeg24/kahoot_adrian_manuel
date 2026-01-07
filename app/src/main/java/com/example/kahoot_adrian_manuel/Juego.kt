package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.kahoot_adrian_manuel.model.Pregunta

class Juego : Fragment() {

    private lateinit var db: SQLiteHelper
    private lateinit var preguntas: List<Pregunta>
    private var indice = 0 // índice de la pregunta actual
    private var aciertos = 0 // contador de respuestas correctas
    private var fallos = 0   // contador de respuestas incorrectas

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.layout_juego, container, false)
        db = SQLiteHelper(requireContext())
        // Inicializamos la base de datos para obtener preguntas

        // Comprobamos que haya al menos 8 preguntas en la base de datos para jugar
        if (db.contarPreguntas() < 8) {
            Toast.makeText(context, "Necesitas al menos 8 preguntas para jugar", Toast.LENGTH_LONG).show()
            // Si no hay suficientes preguntas, se cierra el fragment y se vuelve al menú principal
            parentFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().findViewById<TextView>(R.id.tituloKahoot).visibility = View.VISIBLE
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
            return view
        }

        preguntas = db.obtener5Preguntas()
        // Seleccionamos aleatoriamente 5 preguntas de la base de datos para la partida actual

        // Referencias a los elementos del layout
        val tvPregunta = view.findViewById<TextView>(R.id.preguntaJuego)
        val radioGroup = view.findViewById<RadioGroup>(R.id.respuestas)
        val rb1 = view.findViewById<RadioButton>(R.id.respuesta1Juego)
        val rb2 = view.findViewById<RadioButton>(R.id.respuesta2Juego)
        val rb3 = view.findViewById<RadioButton>(R.id.respuesta3Juego)
        val rb4 = view.findViewById<RadioButton>(R.id.respuesta4Juego)
        val btnContinuar = view.findViewById<Button>(R.id.continuarJuego)
        val btnCerrarJuego = view.findViewById<Button>(R.id.btnCerrarJuego)

        // Función para mostrar la pregunta actual
        fun mostrarPregunta() {
            val p = preguntas[indice]
            tvPregunta.text = p.texto
            radioGroup.clearCheck()
            // Limpiamos la selección anterior de RadioButtons

            rb1.text = p.respuestas[0].texto
            rb2.text = p.respuestas[1].texto
            rb3.text = p.respuestas[2].texto
            rb4.text = p.respuestas[3].texto
            // Actualizamos los textos de las respuestas según la pregunta
        }

        mostrarPregunta() // Mostramos la primera pregunta al cargar el fragment

        // Maneja la selección de respuesta y pasar a la siguiente pregunta
        btnContinuar.setOnClickListener {
            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(context, "Debes seleccionar una respuesta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertimos la respuesta seleccionada en índice
            val seleccion = when (radioGroup.checkedRadioButtonId) {
                R.id.respuesta1Juego -> 0
                R.id.respuesta2Juego -> 1
                R.id.respuesta3Juego -> 2
                else -> 3
            }

            // Comprobamos si la respuesta es correcta y actualizamos contadores
            if (preguntas[indice].respuestas[seleccion].esCorrecta) aciertos++ else fallos++

            indice++ // pasamos a la siguiente pregunta

            if (indice < preguntas.size) mostrarPregunta()
            else parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Resultado.newInstance(aciertos, fallos))
                .commit()
            // Si no hay más preguntas, mostramos el fragment de resultados
        }

        // Botón para cerrar el juego y volver al menú principal
        btnCerrarJuego.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().findViewById<TextView>(R.id.tituloKahoot).visibility = View.VISIBLE
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
        }

        return view
    }
}
