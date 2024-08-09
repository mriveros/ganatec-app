package com.example.asistenciadevacas
import android.content.ContentValues
import android.content.Context

class ConexionDB {
    var conexion: SQLite
    constructor(context: Context){
        conexion = SQLite(context, "dbH", null, 1)
    }

    //guardar vaca
    fun guardarVaca(vaca:VacaModel): Long? {
        var db = conexion.writableDatabase

        val values = ContentValues().apply {
            put("id_color_vaca", vaca.id_color_vaca)
            put("id_ubicacion", vaca.id_ubicacion)
            put("nombre_vaca", vaca.nombre_vaca)
            put("fecha_nac", vaca.fecha_nac)
            put("fecha_preniez", vaca.fecha_preniez)
            put("caravana", vaca.caravana)
            put("activo", vaca.activo)
            put("sincronizado", vaca.sincronizado)
            put("id_sexo", vaca.id_sexo)
        }
        return db?.insert("vaca", "foto", values)

    }

    fun editarVaca(vaca:VacaModel){
        var db = conexion.writableDatabase
        val selection = "id_vaca = " + vaca.id_vaca.toString()
        val values = ContentValues().apply {
            put("id_color_vaca", vaca.id_color_vaca)
            put("id_ubicacion", vaca.id_ubicacion)
            put("nombre_vaca", vaca.nombre_vaca)
            put("fecha_nac", vaca.fecha_nac)
            put("fecha_preniez", vaca.fecha_preniez)
            put("caravana", vaca.caravana)
            put("caravana", vaca.caravana)
            put("activo", vaca.activo)
            put("sincronizado", vaca.sincronizado)
            put("id_sexo", vaca.id_sexo)
        }
        println(db.update("vaca", values, selection, null))
    }

    fun getAllVacas(): MutableList<VacaModel> {
        val db = conexion.readableDatabase

        val cursor = db.query(
            "vaca",
            null,
            null,
            null,
            null,
            null,
            null
        )

        val vacas = mutableListOf<VacaModel>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_vaca"))
            val id_color = cursor.getInt(cursor.getColumnIndexOrThrow("id_color_vaca"))
            val id_ubicacion = cursor.getInt(cursor.getColumnIndexOrThrow("id_ubicacion"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_vaca"))
            val nacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nac"))
            val caravana = cursor.getString(cursor.getColumnIndexOrThrow("caravana"))
            val activo = cursor.getInt(cursor.getColumnIndexOrThrow("activo"))
            val sincronizado = cursor.getInt(cursor.getColumnIndexOrThrow("sincronizado"))
            val id_sexo = cursor.getInt(cursor.getColumnIndexOrThrow("id_sexo"))

            vacas.add(VacaModel(id,id_color, id_ubicacion, nombre, nacimiento, caravana, activo,sincronizado,id_sexo))
        }
        cursor.close()
        return vacas
    }

    fun eliminarVaca(idVaca: Int?){
        val db = conexion.writableDatabase
        val selection = "id_vaca = " + idVaca.toString()
        //val selectionArgs = arrayOf(idVaca.toString())
        val deletedRows = db.delete("vaca", selection, null)
    }

    fun desactivarVaca(vaca:VacaModel) {
        var db = conexion.writableDatabase
        val selection = "id_vaca = " + vaca.id_vaca.toString()
        val values = ContentValues().apply {

            put("activo", 0)

        }
        println(db.update("vaca", values, selection, null))

    }

    fun getAllNuevasVacas(): MutableList<NuevoModelVaca> {
        val db = conexion.readableDatabase

        val cursor = db.query(
            "vaca",
            null,
            null,
            null,
            null,
            null,
            null
        )

        val nuevasVacas = mutableListOf<NuevoModelVaca>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_vaca"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_vaca"))
            //val estado = cursor.getInt(cursor.getColumnIndexOrThrow("estado"))

            // Crear un NuevoModelVaca utilizando el constructor sin argumentos

            val nuevoModel = NuevoModelVaca()
            nuevoModel.id_vaca = id
            nuevoModel.nombre_vaca = nombre

            nuevasVacas.add(nuevoModel)
        }
        cursor.close()
        return nuevasVacas
    }

    fun getVacasPaginadas(offset: Int, limit: Int): MutableList<VacaModel> {
        val vacas = mutableListOf<VacaModel>()
        // Consulta para obtener `limit` vacas a partir de `offset`
        // Ejemplo:
        val query = "SELECT * FROM vaca LIMIT $limit OFFSET $offset"
        val db = conexion.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                // Obtener datos de cursor y añadir a la lista de vacas
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_vaca"))
                val id_color = cursor.getInt(cursor.getColumnIndexOrThrow("id_color_vaca"))
                val id_ubicacion = cursor.getInt(cursor.getColumnIndexOrThrow("id_ubicacion"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_vaca"))
                val nacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nac"))
                val caravana = cursor.getString(cursor.getColumnIndexOrThrow("caravana"))
                val activo = cursor.getInt(cursor.getColumnIndexOrThrow("activo"))
                val sincronizado = cursor.getInt(cursor.getColumnIndexOrThrow("sincronizado"))
                val id_sexo = cursor.getInt(cursor.getColumnIndexOrThrow("id_sexo"))

                vacas.add(VacaModel(id,id_color, id_ubicacion, nombre, nacimiento, caravana, activo,sincronizado,id_sexo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return vacas
    }

}