package com.example.asistenciadevacas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class VacaAdapter(private var vacas: List<VacaModel>) : RecyclerView.Adapter<VacaAdapter.ViewHolder>() {

    private var vacasFiltradas: List<VacaModel> = vacas

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreVaca: TextView = view.findViewById(R.id.vaca_nombre)
        fun bind(vaca: VacaModel) {
            nombreVaca.text = vaca.nombre_vaca
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_lista_vaca, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = vacasFiltradas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vaca = vacasFiltradas[position]
        vaca.position = position

        val btnDetalle = holder.itemView.findViewById<Button>(R.id.btnVerDetalle)
        btnDetalle.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleVaca::class.java)
            intent.putExtra("position", position)
            startActivity(holder.itemView.context, intent, null)
        }
        holder.bind(vaca)
    }

    fun filtrar(texto: String) {
        val textoLower = texto.lowercase()

        vacasFiltradas = if (textoLower.isEmpty()) {
            vacas
        } else {
            // Primero buscamos por nombre
            val resultadosPorNombre = vacas.filter {
                val nombre = it.nombre_vaca?.lowercase() ?: ""
                nombre.contains(textoLower)
            }

            // Luego buscamos por caravana
            val resultadosPorCaravana = vacas.filter {
                val caravana = it.caravana?.lowercase() ?: ""
                caravana.contains(textoLower)
            }

            // Unimos ambos resultados y eliminamos duplicados
            (resultadosPorNombre + resultadosPorCaravana).distinct()
        }

        notifyDataSetChanged()
    }

    fun ordenarPosiciones() {
        for (i in vacas.indices) {
            vacas[i].position = i
        }
    }
}