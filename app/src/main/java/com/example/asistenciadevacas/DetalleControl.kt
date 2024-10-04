package com.example.asistenciadevacas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DetalleControl : AppCompatActivity() {

    companion object{
        var refrescar = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_control)
        mostrarDetalle()
        invalidateOptionsMenu()
    }

    override fun onResume(){
        super.onResume()

        if(refrescar == 1){
            this.mostrarDetalle()
            refrescar = 0
        }
    }
    @SuppressLint("SetTextI18n", "WrongViewCast")
    fun mostrarDetalle() {
        val tipo_control = findViewById<TextView>(R.id.SnnTipoControl)
        val vaca_id = findViewById<TextView>(R.id.txtNombreVaca)
        val control_id = findViewById<TextView>(R.id.SnnTipoControl)
        val fecha_control = findViewById<TextView>(R.id.txtFechaControl)
        val peso = findViewById<TextView>(R.id.Peso)
        val observacion = findViewById<TextView>(R.id.TxtObservacion)

        // Obtén el objeto ControlModel directamente del intent
        val control = intent.getParcelableExtra<ControlModel>("control")

        if (control != null) {
            vaca_id.text = "Vaca: " + control.id_vaca
            fecha_control.text = "Fecha: " + control.fecha
            control_id.text = "Control: " + ColoresUbicaciones.controles[control.id_tipo_control!!]
            observacion.text = "Observacion: " + control.observacion
            peso.text = "Peso: " + control.peso
        }
    }
}


