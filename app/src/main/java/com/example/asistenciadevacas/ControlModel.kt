package com.example.asistenciadevacas

import android.os.Parcel
import android.os.Parcelable
import java.sql.Blob

class ControlModel() : Parcelable {
    var id_control: Int? = null
    var id_tipo_control: Int? = null
    var id_vaca: Int? = null
    var fecha: String? = null
    var peso: String? = null
    var observacion: String? = null
    var position: Int = -1

    constructor(parcel: Parcel) : this() {
        id_control = parcel.readValue(Int::class.java.classLoader) as? Int
        id_tipo_control = parcel.readInt()
        id_vaca = parcel.readInt()
        fecha = parcel.readString()
        peso = parcel.readString()
        observacion = parcel.readString()
        position = parcel.readInt()
    }

    constructor(id_control: Int?,id_tipo_control: Int, id_vaca: Int, fecha: String, peso: String, observacion: String ) : this() {
        this.id_control = id_control
        this.id_tipo_control=id_tipo_control
        this.id_vaca=id_vaca
        this.fecha=fecha
        this.peso=peso
        this.observacion=observacion
    }

    constructor( id_control: Int, id_tipo_control: Int, id_vaca: Int, fecha: String, peso: String,  observacion: String) : this() {
        this.id_control=id_control
        this.id_tipo_control=id_tipo_control
        this.id_vaca=id_vaca
        this.fecha=fecha
        this.peso=peso
        this.observacion= observacion
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_control)
        id_tipo_control?.let { parcel.writeInt(it) }
        id_vaca?.let { parcel.writeInt(it) }
        parcel.writeString(fecha)
        parcel.writeString(peso)
        parcel.writeString(observacion)
        parcel.writeInt(position)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ControlModel> {
        override fun createFromParcel(parcel: Parcel): ControlModel {
            return ControlModel(parcel)
        }

        override fun newArray(size: Int): Array<ControlModel?> {
            return arrayOfNulls(size)
        }
    }
}