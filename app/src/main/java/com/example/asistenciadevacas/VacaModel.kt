package com.example.asistenciadevacas

import android.os.Parcel
import android.os.Parcelable
import java.sql.Blob

class VacaModel() : Parcelable {
    var id_vaca: Int? = null
    var id_raza_vaca: Int? = null
    var id_ubicacion: Int? = null
    var nombre_vaca: String? = null
    var fecha_nac: String? = null
    var fecha_preniez: String? = null
    var foto: Blob? = null
    var caravana: String? = null
    var position: Int = -1
    var activo: Int? = null
    var sincronizado: Int? = null
    var id_sexo: Int? = null
    var peso: String? = null

    constructor(parcel: Parcel) : this() {
        id_vaca = parcel.readValue(Int::class.java.classLoader) as? Int
        id_raza_vaca = parcel.readInt()
        id_ubicacion = parcel.readInt()
        nombre_vaca = parcel.readString()
        fecha_nac = parcel.readString()
        caravana = parcel.readString()
        position = parcel.readInt()
        id_sexo = parcel.readInt()
        peso = parcel.readString()
        peso = parcel.readString()
    }

    constructor(id_vaca: Int?,id_raza_vaca: Int, id_ubicacion: Int, nombre_vaca: String, fecha_nac: String, caravana: String, activo: Int, sincronizado: Int, id_sexo: Int, peso: String ) : this() {
        this.id_vaca = id_vaca
        this.id_raza_vaca=id_raza_vaca
        this.id_ubicacion=id_ubicacion
        this.nombre_vaca=nombre_vaca
        this.fecha_nac=fecha_nac
        this.caravana=caravana
        this.activo= activo
        this.sincronizado= sincronizado
        this.id_sexo= id_sexo
        this.peso= peso

    }

    constructor( id_raza_vaca: Int, id_ubicacion: Int, nombre_vaca: String, fecha_nac: String, caravana: String,  activo: Int, sincronizado: Int, id_sexo: Int, peso: String) : this() {
        this.id_raza_vaca=id_raza_vaca
        this.id_ubicacion=id_ubicacion
        this.nombre_vaca=nombre_vaca
        this.fecha_nac=fecha_nac
        this.caravana=caravana
        this.activo= activo
        this.sincronizado= sincronizado
        this.id_sexo= id_sexo
        this.peso = peso

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_vaca)
        id_raza_vaca?.let { parcel.writeInt(it) }
        id_ubicacion?.let { parcel.writeInt(it) }
        parcel.writeString(nombre_vaca)
        parcel.writeString(fecha_nac)
        parcel.writeString(caravana)
        parcel.writeInt(position)
        id_sexo?.let { parcel.writeInt(it) }
        parcel.writeString(peso)
        parcel.writeString(peso)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VacaModel> {
        override fun createFromParcel(parcel: Parcel): VacaModel {
            return VacaModel(parcel)
        }

        override fun newArray(size: Int): Array<VacaModel?> {
            return arrayOfNulls(size)
        }
    }
}