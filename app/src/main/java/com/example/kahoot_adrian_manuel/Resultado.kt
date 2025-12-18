package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class Resultado(
    private val aciertos: Int,
    private val fallos: Int
) : Fragment(R.layout.resultados) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.totalPreguntas).text = "Total preguntas: 5"

        view.findViewById<TextView>(R.id.aciertos).text = "Aciertos: $aciertos"

        view.findViewById<TextView>(R.id.fallos).text = "Fallos: $fallos"
    }
}
