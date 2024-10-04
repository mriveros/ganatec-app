package com.example.asistenciadevacas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ControlAdapter(private var controles: List<ControlModel>) : RecyclerView.Adapter<ControlAdapter.ViewHolder>() {

    private var controlesFiltrados: List<ControlModel> = controles

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fecha_control: TextView = view.findViewById(R.id.fecha_control)
        val nombre_control: TextView = view.findViewById(R.id.tipo_control)
        fun bind(control: ControlModel) {
            fecha_control.text = control.fecha
            nombre_control.text = ColoresUbicaciones.controles[control.id_tipo_control!!]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_lista_control, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = controlesFiltrados.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val control = controlesFiltrados[position]

        val btnDetalle = holder.itemView.findViewById<Button>(R.id.btnVerDetalleControl)
        btnDetalle.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleControl::class.java)
            // Pasa el objeto ControlModel como un extra
            intent.putExtra("control", control)
            startActivity(holder.itemView.context, intent, null)
        }
        holder.bind(control)
    }

    fun filtrar(texto: String) {
        val textoLower = texto.lowercase()
        controlesFiltrados = if (textoLower.isEmpty()) {
            controles
        } else {
            // Primero buscamos por fecha
            val resultadosPorFecha = controles.filter {
                //tipo control del objeto controles
                val fecha = it.fecha.toString()
                //obtener todos los controles que contengan la fecha
                fecha.contains(textoLower)
            }

            //Buscamos por nombre de control
            val resultadosPorControl = controles.filter {
                //tipo control del objeto controles
                val control = it.id_tipo_control.toString()
                //obtener todos los controles que contengan el valor de textoLower
                var indexEncontrado: Int? = null
                // Iterar sobre la colección 'controles' de la clase ColoresUbicaciones
                ColoresUbicaciones.controles.forEachIndexed { index, control ->
                    if (control.lowercase().contains(textoLower)) {
                        indexEncontrado = index
                        return@forEachIndexed  // Salir del loop si se encuentra coincidencia
                    }
                }
                if (indexEncontrado != null) {
                    control.contains(indexEncontrado.toString())
                } else {
                    false
                }


            }
            // Unimos ambos resultados y eliminamos duplicados
            (resultadosPorFecha+resultadosPorControl).distinct()
        }
        notifyDataSetChanged()
    }

    fun ordenarPosiciones() {
        for (i in controles.indices) {
            controles[i].position = i
        }
    }
}