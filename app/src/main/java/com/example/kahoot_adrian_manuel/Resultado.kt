package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            aciertos = it.getInt("aciertos")
            fallos = it.getInt("fallos")
        }

        view.findViewById<TextView>(R.id.totalPreguntas).text = "Total preguntas: ${aciertos + fallos}"
        view.findViewById<TextView>(R.id.aciertos).text = "Aciertos: $aciertos"
        view.findViewById<TextView>(R.id.fallos).text = "Fallos: $fallos"
    }
}
