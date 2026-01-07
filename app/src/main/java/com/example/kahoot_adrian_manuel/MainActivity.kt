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
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.overflowIcon?.setTint(
            resources.getColor(android.R.color.white, theme)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        supportActionBar?.title = ""
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val titulo = findViewById<TextView>(R.id.tituloKahoot)
        val container = findViewById<View>(R.id.fragmentContainer)

        when (item.itemId) {

            R.id.menu_configuracion -> {
                //Ocultamos KAHOOT y mostramos fragment
                titulo.visibility = View.GONE
                container.visibility = View.VISIBLE

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ConfiguracionPreguntas())
                    .commit()
                return true
            }

            R.id.menu_jugar -> {
                // Ocultamos KAHOOT y mostramos fragment
                titulo.visibility = View.GONE
                container.visibility = View.VISIBLE

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, Juego())
                    .commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
