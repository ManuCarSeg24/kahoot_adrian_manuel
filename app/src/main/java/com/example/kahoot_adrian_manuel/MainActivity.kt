package com.example.kahoot_adrian_manuel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var layoutConfigurarPreguntas: LinearLayout
    private lateinit var layoutJuego: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.overflowIcon?.setTint(
            resources.getColor(android.R.color.white, theme)
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Cargamos el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayShowTitleEnabled(false)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_configuracion -> {
                layoutConfigurarPreguntas.visibility = View.VISIBLE
                layoutJuego.visibility = View.GONE
            }
            R.id.menu_jugar -> {
                layoutConfigurarPreguntas.visibility = View.GONE
                layoutJuego.visibility = View.VISIBLE
            }
        }
        return super.onOptionsItemSelected(item)
    }
}