package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        // Permite que la UI llegue hasta los bordes de la pantalla (ideal para pantallas completas y temas modernos)

        setContentView(R.layout.activity_main)
        // Inflamos el layout principal que contiene la Toolbar, el título "KAHOOT!" y el contenedor de fragments

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Configura la Toolbar como ActionBar, necesaria para mostrar el menú de opciones

        toolbar.overflowIcon?.setTint(
            resources.getColor(android.R.color.white, theme)
        )
        // Cambia el color del icono de tres puntitos a blanco para que contraste con el fondo morado
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // Infla el menú con las opciones "Jugar" y "Configuración"

        supportActionBar?.title = ""
        // Eliminamos el título por defecto del toolbar para mantener solo el logo o texto principal en la UI

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val titulo = findViewById<TextView>(R.id.tituloKahoot)
        val container = findViewById<View>(R.id.fragmentContainer)

        when (item.itemId) {
            R.id.menu_configuracion -> {
                // Si el usuario selecciona "Configuración":
                titulo.visibility = View.GONE
                // Ocultamos el título principal de la pantalla inicial
                container.visibility = View.VISIBLE
                // Mostramos el contenedor donde se cargará el fragment

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ConfiguracionPreguntas())
                    .commit()
                // Reemplazamos el contenedor por el fragment de configuración de preguntas
                return true
            }

            R.id.menu_jugar -> {
                // Si el usuario selecciona "Jugar":
                titulo.visibility = View.GONE
                container.visibility = View.VISIBLE

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, Juego())
                    .commit()
                // Cargamos el fragment de juego
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
