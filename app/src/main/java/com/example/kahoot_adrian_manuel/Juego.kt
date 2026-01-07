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

    private var indice = 0
    private var aciertos = 0
    private var fallos = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.layout_juego, container, false)

        db = SQLiteHelper(requireContext())

        // Tener al menos 8 preguntas para jugar
        if (db.contarPreguntas() < 8) {
            Toast.makeText(
                context,
                "Necesitas al menos 8 preguntas para jugar",
                Toast.LENGTH_LONG
            ).show()

            // Volver al menú principal
            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()

            requireActivity().findViewById<TextView>(R.id.tituloKahoot).visibility = View.VISIBLE
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE

            return view
        }

        // Usamos solo 5 preguntas
        preguntas = db.obtener5Preguntas()

        val tvPregunta = view.findViewById<TextView>(R.id.preguntaJuego)
        val radioGroup = view.findViewById<RadioGroup>(R.id.respuestas)

        val rb1 = view.findViewById<RadioButton>(R.id.respuesta1Juego)
        val rb2 = view.findViewById<RadioButton>(R.id.respuesta2Juego)
        val rb3 = view.findViewById<RadioButton>(R.id.respuesta3Juego)
        val rb4 = view.findViewById<RadioButton>(R.id.respuesta4Juego)

        val btnContinuar = view.findViewById<Button>(R.id.continuarJuego)

        // 👉 BOTÓN CERRAR JUEGO (AQUÍ ESTÁ)
        val btnCerrarJuego = view.findViewById<Button>(R.id.btnCerrarJuego)

        fun mostrarPregunta() {
            val p = preguntas[indice]
            tvPregunta.text = p.texto
            radioGroup.clearCheck()

            rb1.text = p.respuestas[0].texto
            rb2.text = p.respuestas[1].texto
            rb3.text = p.respuestas[2].texto
            rb4.text = p.respuestas[3].texto
        }

        mostrarPregunta()

        btnContinuar.setOnClickListener {

            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(context, "Debes seleccionar una respuesta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val seleccion = when (radioGroup.checkedRadioButtonId) {
                R.id.respuesta1Juego -> 0
                R.id.respuesta2Juego -> 1
                R.id.respuesta3Juego -> 2
                else -> 3
            }

            if (preguntas[indice].respuestas[seleccion].esCorrecta) {
                aciertos++
            } else {
                fallos++
            }

            indice++

            if (indice < preguntas.size) {
                mostrarPregunta()
            } else {
                // Pasamos a Resultados
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        Resultado.newInstance(aciertos, fallos)
                    )
                    .commit()
            }
        }

        btnCerrarJuego.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()

            requireActivity().findViewById<TextView>(R.id.tituloKahoot).visibility = View.VISIBLE
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
        }

        return view
    }
}
