package com.example.asistenciadevacas

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistenciadevacas.ControlVacaModel as ControlVacaModel
import java.text.SimpleDateFormat
import java.util.Date
class ControlVaca : AppCompatActivity() {

    companion object{
        var refrescar = 0
    }

    lateinit var arrayControles: Spinner
    var vaca_id: Int = 0
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
        var peso = findViewById<TextView>(R.id.txtPeso)
        var observacion = findViewById<TextView>(R.id.txtObservacion)
        val listaControles = EstructurasColecciones.controles
        val SnnControles = findViewById<Spinner>(R.id.SnnTipoControl)

        arrayControles = findViewById(R.id.SnnTipoControl)
        val adaptadorTiposControlles = ArrayAdapter(this, R.layout.spinner_item,listaControles)
        arrayControles.adapter=adaptadorTiposControlles

        if (vaca != null) {
            nombreVaca.text = "Nombre: " + vaca.nombre_vaca //?.toUpperCase()
            caravanaVaca.text = "Caravana: " + vaca.caravana
            vaca_id = vaca.id_vaca!!
        }

        val btnGuardarControl = findViewById<Button>(R.id.btnGuardarControl)
        btnGuardarControl.setOnClickListener{
            val conexion = ConexionDB(this)
            val sdf = SimpleDateFormat("dd/MM/yyyy") // Formato deseado
            val fechaActual = sdf.format(Date()) // Fecha actual
            //iniciar VacaControlModel para pasar argumentos
            val controlVaca = ControlVacaModel(null,arrayControles.selectedItemPosition,vaca_id,fechaActual,peso.text.toString(),observacion.text.toString())
            var id_control = conexion.guardarControlVaca(controlVaca)
            val control: ControlModel? = conexion.getControl(id_control)
            Toast.makeText(this, "Control ${id_control} registrado", Toast.LENGTH_SHORT).show()
            if (control != null) {
                ListaControles.controles!!.add(control)
            }
            if (control != null) {
                ListaControles.controlesFiltradas!!.add(control)
            }
            ListaControles.controlAdapter!!.notifyItemInserted(ListaControles.controles!!.lastIndex)
            ListaControles.controlAdapter!!.ordenarPosiciones()
            finish()
        }
    }
}


