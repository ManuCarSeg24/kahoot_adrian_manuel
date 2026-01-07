package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Resultado : Fragment(R.layout.resultados) {
    private var aciertos: Int = 0
    private var fallos: Int = 0

    companion object {
        // Método estático para crear un fragment con parámetros
        fun newInstance(aciertos: Int, fallos: Int): Resultado {
            val fragment = Resultado()
            val args = Bundle()
            args.putInt("aciertos", aciertos)
            args.putInt("fallos", fallos)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // Indicamos que este fragment no necesita menú (elimina los puntitos)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperamos los datos enviados desde Juego
        arguments?.let {
            aciertos = it.getInt("aciertos")
            fallos = it.getInt("fallos")
        }

        // Mostramos los resultados en la UI
        view.findViewById<TextView>(R.id.totalPreguntas).text = "Total preguntas: ${aciertos + fallos}"
        view.findViewById<TextView>(R.id.aciertos).text = "Aciertos: $aciertos"
        view.findViewById<TextView>(R.id.fallos).text = "Fallos: $fallos"

        // Botón para volver al menú principal
        view.findViewById<Button>(R.id.btnVolverMenu).setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
            requireActivity().findViewById<TextView>(R.id.tituloKahoot).visibility = View.VISIBLE
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear() // Eliminamos el menú superior para que no aparezcan opciones innecesarias
    }
}
