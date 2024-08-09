package com.example.asistenciadevacas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistenciadevacas.ListaDeVacas.Companion.vacas
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var conexion: ConexionDB
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conexion = ConexionDB(this)
        database = FirebaseDatabase.getInstance().reference

        val btnVerListaVacas = findViewById<Button>(R.id.btnVerListaVacas)
        val btnTomarAsistenciaVacas = findViewById<Button>(R.id.btnTomarAsitenciaVacas)
        val btnSincData = findViewById<Button>(R.id.btnSincData)

        btnVerListaVacas.setOnClickListener {
            val intent = Intent(this, ListaDeVacas::class.java)
            startActivity(intent)
        }

        btnTomarAsistenciaVacas.setOnClickListener {
            val intent = Intent(this, ListaDeVacasAsistidas::class.java)
            startActivity(intent)
        }

        btnSincData.setOnClickListener {
            syncData()
        }
    }

    private fun syncData() {
        val vacas = conexion.getAllVacas()
        for (vaca in vacas) {
            // Crear una referencia a la base de datos de Firebase
            val vacaRef = database.child("vacas").child(vaca.id_vaca.toString())

            // Sincronizar los datos
            vacaRef.setValue(vaca)
                .addOnSuccessListener {
                    Toast.makeText(this, "Vaca ${vaca.id_vaca} sincronizada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al sincronizar Vaca ${vaca.id_vaca}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}