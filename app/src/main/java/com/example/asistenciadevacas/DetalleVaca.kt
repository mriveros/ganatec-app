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

class DetalleVaca : AppCompatActivity() {

    companion object{
        var refrescar = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_vaca)
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
    fun mostrarDetalle(){
        val conexion = ConexionDB(this)

        val nombreVaca = findViewById<TextView>(R.id.txtNombreVaca)
        val nacimientoVaca = findViewById<TextView>(R.id.txtFechaNacimiento)
        val caravanaVaca = findViewById<TextView>(R.id.txtNroCaravana)
        val ubicacionVaca = findViewById<TextView>(R.id.SnnUbicacion)
        val colorVaca = findViewById<TextView>(R.id.SnnColor)
        val sexoVaca = findViewById<TextView>(R.id.SnnSexo)
        val pesoVaca = findViewById<TextView>(R.id.TxtPeso)

        val vaca = ListaDeVacas.vacas!![intent.getIntExtra("position", 0)]
        if (vaca != null) {
            nombreVaca.text = "Nombre: " + vaca.nombre_vaca //?.toUpperCase()
            caravanaVaca.text = "Caravana: " + vaca.caravana
            nacimientoVaca.text = "Fecha Nacimiento: " + vaca.fecha_nac
            ubicacionVaca.text = "Ubicación: " + ColoresUbicaciones.ubicaciones[vaca.id_ubicacion!!]
            colorVaca.text = "Raza: " + ColoresUbicaciones.colores[vaca.id_raza_vaca!!]
            sexoVaca.text = "Sexo: " + ColoresUbicaciones.sexos[vaca.id_sexo!!]
            pesoVaca.text = "Peso: " + vaca.peso
        }

        val btnEliminarVaca = findViewById<Button>(R.id.btnEliminarVaca)
        btnEliminarVaca.setOnClickListener {
            if (vaca != null) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Atención")
                builder.setMessage("Seguro desea desactivar a: " + vaca.nombre_vaca)
                builder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción a realizar cuando se presiona el botón "Aceptar"
                    conexion.desactivarVaca(vaca)
                    ListaDeVacas.vacas!!.removeAt(vaca.position)
                    ListaDeVacas.vacaAdapter!!.ordenarPosiciones()
                    ListaDeVacas.vacaAdapter!!.notifyItemRemoved(vaca.position)
                    val mensaje = "Se desactivó a: " + vaca.nombre_vaca?.toUpperCase()
                    val duracion = Toast.LENGTH_SHORT // Duración de 3 segundos
                    val toast = Toast.makeText(this, mensaje, duracion)
                    toast.show()
                    finish()
                }
                builder.setNegativeButton("Cancelar") { dialog, which ->
                    // Acción a realizar cuando se presiona el botón "Cancelar"
                    val mensaje = "No se desactivó a: " + vaca.nombre_vaca?.toUpperCase()
                    val duracion = Toast.LENGTH_SHORT // Duración de 3 segundos
                    val toast = Toast.makeText(this, mensaje, duracion)
                    toast.show()
                }
                val alert = builder.create()
                alert.show()
            }
        }
        //Editar la vaca seleccionada
        val btnEditarVaca = findViewById<Button>(R.id.btnEditarVaca)
        btnEditarVaca.setOnClickListener{
            val intent = Intent(this, AniadirVaca::class.java)
            intent.putExtra("editar", true)
            intent.putExtra("vaca", vaca)
            Log.d("DEBUG", "Peso antes de enviar: ${vaca.peso}")
            startActivity(intent)

        }
        //Agregar control a la vaca seleccionada
        val btnAgregarControl = findViewById<Button>(R.id.btnAgregarControl)
        btnAgregarControl.setOnClickListener{
            val intent = Intent(this, ControlVaca::class.java)
            intent.putExtra("agregar_control", true)
            intent.putExtra("vaca", vaca)
            Log.d("DEBUG", "Peso antes de enviar: ${vaca.peso}")
            startActivity(intent)

        }
        val btnVerControl = findViewById<Button>(R.id.btnVerControl)
        btnVerControl.setOnClickListener{
            val intent = Intent(this, ListaControles::class.java)
            intent.putExtra("ver_control", true)
            intent.putExtra("vaca", vaca)
            startActivity(intent)

        }
    }
}


