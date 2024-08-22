package com.example.asistenciadevacas
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite(context: Context?, dbH: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, dbH, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        //tabla COLOR
        db?.execSQL("""
            CREATE TABLE raza (
                id_raza_vaca INTEGER PRIMARY KEY AUTOINCREMENT, 
                descripcion VARCHAR(30)
            )
        """)
        //tabla SEXO
        db?.execSQL("""
            CREATE TABLE sexo (
                id_sexo_vaca INTEGER PRIMARY KEY AUTOINCREMENT, 
                descripcion VARCHAR(30)
            )
        """)
        //tabla UBICACIÖN
        db?.execSQL( """
            CREATE TABLE ubicacion (
                id_ubicacion INTEGER PRIMARY KEY AUTOINCREMENT, 
                descripcion_ubicacion VARCHAR(50)
            )
        """)
        //tabla VACA
        db?.execSQL("""
            CREATE TABLE vaca ( 
                id_vaca INTEGER PRIMARY KEY AUTOINCREMENT, 
                id_raza_vaca INTEGER, 
                id_ubicacion INTEGER, 
                nombre_vaca TEXT, 
                fecha_nac TEXT, 
                fecha_preniez TEXT, 
                foto BLOB, 
                caravana VARCHAR(10),
                activo INTEGER,
                sincronizado INTEGER,
                id_sexo INTEGER,
                peso TEXT,
                FOREIGN KEY (id_raza_vaca) REFERENCES raza(id_raza_vaca),
                FOREIGN KEY (id_ubicacion) REFERENCES ubicacion(id_ubicacion),
                FOREIGN KEY (id_sexo) REFERENCES sexo(id_sexo_vaca)          
            )
        """)
        //tabla ASISTENCIA
        db?.execSQL("""
            CREATE TABLE asistencia(
                id_asistencia INTEGER PRIMARY KEY AUTOINCREMENT, 
                id_vaca INTEGER, 
                id_ubicacion INTEGER, 
                fecha_asistencia TEXT,
                FOREIGN KEY (id_vaca) REFERENCES vaca(id_vaca),
                FOREIGN KEY (id_ubicacion) REFERENCES ubicacion(id_ubicacion)
            )
        """)
        //tabla controles
        db?.execSQL("""
            CREATE TABLE tipo_control ( 
                id_tipo_control INTEGER PRIMARY KEY AUTOINCREMENT, 
                descripcion INTEGER
            )
        """)
        //tabla controles
        db?.execSQL("""
            CREATE TABLE controles ( 
                id_control INTEGER PRIMARY KEY AUTOINCREMENT, 
                id_tipo_control INTEGER,  
                id_vaca INTEGER, 
                fecha TEXT, 
                peso TEXT,
                observacion TEXT,
                FOREIGN KEY (id_vaca) REFERENCES vaca(id_vaca),
                FOREIGN KEY (id_tipo_control) REFERENCES control(id_control)  
            )
        """)
        // Inserción de valores por defecto en la tabla tipo_control
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Vacunación')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Control de enfermedades')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Nutrición y suplementación')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Desparasitación interna')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Desparasitación externa')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Manejo reproductivo')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Manejo de la mastitis')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Higiene y limpieza')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Corte de pezuñas')")
        db?.execSQL("INSERT INTO tipo_control (descripcion) VALUES ('Sanitaciones pre y postparto')")


        
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db?.execSQL("ALTER TABLE vaca ADD COLUMN estado INTEGER DEFAULT 0")
        }
    }
}