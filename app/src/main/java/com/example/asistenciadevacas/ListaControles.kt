package com.example.asistenciadevacas

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistenciadevacas.ListaDeVacasAsistidas.Companion.nuevasVacas
import com.example.asistenciadevacas.ListaDeVacasAsistidas.Companion.nuevoVacaAdapter

class ListaControles : AppCompatActivity() {

    companion object {
        var controlAdapter: ControlAdapter? = null
        var controles: MutableList<ControlModel> = mutableListOf()
        var controlesFiltradas: MutableList<ControlModel> = mutableListOf()
        var isLoading = false
        var isLastPage = false
        var currentPage = 0
        const val pageSize = 10 // Número de controles por página
        val REQUEST_CODE_ADD_VACA = 1
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_controles)

        // No rotate pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val listaControles = findViewById<RecyclerView>(R.id.rvListaControles)
        val layoutManager = LinearLayoutManager(this)
        listaControles.layoutManager = layoutManager

        controlAdapter = ControlAdapter(controlesFiltradas)
        listaControles.adapter = controlAdapter

        // Configurar el EditText para la búsqueda
        val etBuscarcontrol = findViewById<EditText>(R.id.etBuscarControl)
        etBuscarcontrol.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val texto = s.toString()
                controlAdapter?.filtrar(texto)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Cargar la primera página de datos
        loadControles()

        // Configurar el scroll listener para paginación
        listaControles.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                loadControles()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        // Configuración del botón para añadir una vaca
        val btnAniadirVaca = findViewById<Button>(R.id.btnAniadirControl)
        btnAniadirVaca.setOnClickListener {
            //aca se debe pasar argumentos de vaca del model vaca.....
            val intent = Intent(this, ControlVaca::class.java)
            //startActivity(intent)
            startActivityForResult(intent, REQUEST_CODE_ADD_VACA)
        }
    }

    private fun loadControles() {
        val conexion = ConexionDB(this)
        val nuevasControles = conexion.getControlesPaginadas(currentPage * pageSize, pageSize)

        // Log para verificar los datos obtenidos
        Log.d("Lista De Controles", "Nuevas Controles: ${nuevasControles.size}")

        if (nuevasControles.isNotEmpty()) {
            controles.addAll(nuevasControles)
            controlesFiltradas.addAll(nuevasControles)
            controlAdapter?.notifyDataSetChanged()
        } else {
            isLastPage = true
        }

        isLoading = false
    }
}