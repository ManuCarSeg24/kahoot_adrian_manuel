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
        fun newInstance(aciertos: Int, fallos: Int): Resultado {
            val fragment = Resultado()
            val args = Bundle()
            args.putInt("aciertos", aciertos)
            args.putInt("fallos", fallos)
            fragment.arguments = args
            return fragment
        }
    }

    // 👇 AQUÍ VA (NO dentro de onViewCreated)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recoger datos
        arguments?.let {
            aciertos = it.getInt("aciertos")
            fallos = it.getInt("fallos")
        }

        // Mostrar resultados
        view.findViewById<TextView>(R.id.totalPreguntas).text =
            "Total preguntas: ${aciertos + fallos}"
        view.findViewById<TextView>(R.id.aciertos).text =
            "Aciertos: $aciertos"
        view.findViewById<TextView>(R.id.fallos).text =
            "Fallos: $fallos"

        // BOTÓN VOLVER AL MENÚ
        view.findViewById<Button>(R.id.btnVolverMenu).setOnClickListener {

            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()

            requireActivity()
                .findViewById<TextView>(R.id.tituloKahoot)
                .visibility = View.VISIBLE

            requireActivity()
                .findViewById<View>(R.id.fragmentContainer)
                .visibility = View.GONE
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()   // elimina los puntitos
    }
}
