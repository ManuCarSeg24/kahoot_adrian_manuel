package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ConfiguracionPreguntas : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.layout_configurar_preguntas, container, false)
        // Inflamos el layout de configuración de preguntas, que contiene EditText y RadioButtons

        // Captura de todos los EditText para pregunta y respuestas
        val etPregunta = view.findViewById<EditText>(R.id.preguntaConfiguracion)
        val etR1 = view.findViewById<EditText>(R.id.respuesta1)
        val etR2 = view.findViewById<EditText>(R.id.respuesta2)
        val etR3 = view.findViewById<EditText>(R.id.respuesta3)
        val etR4 = view.findViewById<EditText>(R.id.respuesta4)

        // Captura de los RadioButtons para marcar la respuesta correcta
        val rb1 = view.findViewById<RadioButton>(R.id.correcta1)
        val rb2 = view.findViewById<RadioButton>(R.id.correcta2)
        val rb3 = view.findViewById<RadioButton>(R.id.correcta3)
        val rb4 = view.findViewById<RadioButton>(R.id.correcta4)

        val btnGuardar = view.findViewById<Button>(R.id.guardarPregunta)
        val btnVolverMenu = view.findViewById<Button>(R.id.btnVolverMenuConfig)

        val db = SQLiteHelper(requireContext())
        // Instancia de la base de datos para guardar preguntas y respuestas

        // Solo una respuesta correcta puede estar marcada a la vez
        val radios = listOf(rb1, rb2, rb3, rb4)
        radios.forEach { rb ->
            rb.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    radios.filter { it != rb }.forEach { it.isChecked = false }
                }
            }
        }

        // Botón para guardar la pregunta
        btnGuardar.setOnClickListener {
            // Validación: todos los campos deben estar completos
            if (etPregunta.text.isBlank() || etR1.text.isBlank() || etR2.text.isBlank() || etR3.text.isBlank() || etR4.text.isBlank()) {
                Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Determinar cuál respuesta es correcta según el RadioButton seleccionado
            val correcta = when {
                rb1.isChecked -> 1
                rb2.isChecked -> 2
                rb3.isChecked -> 3
                rb4.isChecked -> 4
                else -> 0
            }

            if (correcta == 0) {
                Toast.makeText(context, "Selecciona la respuesta correcta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardamos la pregunta y las respuestas en la base de datos
            val guardado = db.insertarPreguntaConRespuestas(
                etPregunta.text.toString(),
                listOf(etR1.text.toString(), etR2.text.toString(), etR3.text.toString(), etR4.text.toString()),
                correcta
            )

            if (guardado) {
                Toast.makeText(context, "Pregunta guardada", Toast.LENGTH_SHORT).show()
                // Limpiamos el formulario después de guardar
                etPregunta.text.clear()
                etR1.text.clear()
                etR2.text.clear()
                etR3.text.clear()
                etR4.text.clear()
                radios.forEach { it.isChecked = false }
            }
        }

        // Botón para volver al menú principal
        btnVolverMenu.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
            // Restauramos la visibilidad de la pantalla principal
            requireActivity().findViewById<TextView>(R.id.tituloKahoot).visibility = View.VISIBLE
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
        }

        return view
    }
}
