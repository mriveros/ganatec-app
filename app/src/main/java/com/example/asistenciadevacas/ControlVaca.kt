package com.example.asistenciadevacas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ControlVaca : AppCompatActivity() {

    companion object{
        var refrescar = 0
    }
    lateinit var arrayControles: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_vaca)
        val vaca = intent.getParcelableExtra<VacaModel>("vaca")
        mostrarDetalle(vaca)
        invalidateOptionsMenu()
    }

    @SuppressLint("SetTextI18n", "WrongViewCast")
    fun mostrarDetalle(vaca: VacaModel?) {


        val nombreVaca = findViewById<TextView>(R.id.txtNombreVaca)
        val caravanaVaca = findViewById<TextView>(R.id.txtNroCaravana)
        val listaControles = ColoresUbicaciones.controles
        val SnnControlesControles = findViewById<Spinner>(R.id.SnnTipoControl)

        arrayControles = findViewById(R.id.SnnTipoControl)
        val adaptadorTiposControlles = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listaControles)
        arrayControles.adapter=adaptadorTiposControlles

        if (vaca != null) {
            nombreVaca.text = "Nombre: " + vaca.nombre_vaca //?.toUpperCase()
            caravanaVaca.text = "Caravana: " + vaca.caravana
        }

        val btnGuardarControl = findViewById<Button>(R.id.btnGuardarControl)
        btnGuardarControl.setOnClickListener{

            Log.d("DEBUG", "Se guarda control")
        }
    }
}


