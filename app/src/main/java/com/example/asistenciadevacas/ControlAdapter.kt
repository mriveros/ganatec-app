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
        val nombre_control: TextView = view.findViewById(R.id.tipo_control)
        fun bind(control: ControlModel) {
            nombre_control.text = control.id_tipo_control.toString()
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
        control.position = position

        val btnDetalle = holder.itemView.findViewById<Button>(R.id.btnVerDetalleControl)
        btnDetalle.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleVaca::class.java)
            intent.putExtra("position", position)
            startActivity(holder.itemView.context, intent, null)
        }
        holder.bind(control)
    }

    fun filtrar(texto: String) {
        val textoLower = texto.lowercase()

        controlesFiltrados = if (textoLower.isEmpty()) {
            controles
        } else {
            // Primero buscamos por nombre
            val resultadosPorNombre = controles.filter {
                val nombre = it.observacion?.lowercase() ?: ""
                nombre.contains(textoLower)
            }

            // Unimos ambos resultados y eliminamos duplicados
            (resultadosPorNombre).distinct()
        }

        notifyDataSetChanged()
    }

    fun ordenarPosiciones() {
        for (i in controles.indices) {
            controles[i].position = i
        }
    }
}