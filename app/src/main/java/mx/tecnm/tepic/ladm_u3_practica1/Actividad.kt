package mx.tecnm.tepic.ladm_u3_practica1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.util.ArrayList

class Actividad(d:String, fc:String, fe: String) {
    var id = 0
    var desc = d
    var fcap = fc
    var fent = fe

    val nombre = "Tareas"
    var puntero : Context?= null

    fun asignarPuntero(p: Context) {
        puntero = p
    }

    fun insertar():Boolean {
        try {
            var base = BaseDatos(puntero!!, nombre, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()

            datos.put("DESCRIPCION", desc)
            datos.put("FECHACAPTURA", fcap)
            datos.put("FECHAENTREGA", fent)

            var res = insertar.insert("ACTIVIDADES", "ID_ACTIVIDAD", datos)

            if (res.toInt() == -1) {
                return false
            }
        }catch (e: SQLiteException) {
            return false
        }
        return true
    }

    fun recuperarDatos(): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        try{
            var bd = BaseDatos(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("*")

            var cursor  = select.query("ACTIVIDADES", columnas, null, null, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Actividad(cursor.getString(1),cursor.getString(2),cursor.getString(3))

                    temp.id = cursor.getInt(0)
                    data.add(temp)
                }while (cursor.moveToNext())
            }else{
            }
        }catch (e: SQLiteException){
        }
        return data
    }

    fun recuperaPorFecha(f:String): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        try{
            var bd = BaseDatos(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("*")
            var fecha = arrayOf(f)

            var cursor  = select.query("ACTIVIDADES", columnas, "FECHACAPTURA = ?", fecha, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Actividad(cursor.getString(1),cursor.getString(2),cursor.getString(3))

                    temp.id = cursor.getInt(0)
                    data.add(temp)
                }while (cursor.moveToNext())
            }else{
            }
        }catch (e: SQLiteException){
        }
        return data
    }

    fun consultaID(id:String): Actividad{
        var registro = Actividad("","","")

        try {
            var bd = BaseDatos(puntero!!, nombre, null, 1)
            var select = bd.readableDatabase
            var busca = arrayOf("*")
            var buscaID = arrayOf(id)

            var res = select.query("ACTIVIDADES", busca, "ID_ACTIVIDAD = ?",buscaID, null, null, null)
            if(res.moveToFirst()){
                registro.id = id.toInt()
                registro.desc = res.getString(1)
                registro.fcap = res.getString(2)
                registro.fent = res.getString(3)
            }
        }catch (e: SQLiteException){
            e.message.toString()
        }
        return registro
    }

    fun eliminar(id:String):Boolean{
        try{
            var base = BaseDatos(puntero!!, nombre,null,1)
            var eliminar = base.writableDatabase
            var eliminarID = arrayOf(id)

            var res = eliminar.delete("ACTIVIDADES","ID_ACTIVIDAD = ?",eliminarID)
            if(res.toInt() == 0){
                return false
            }
        }catch (e: SQLiteException){
            return false
        }
        return true
    }

    fun actualizar():Boolean{
        try{
            var base = BaseDatos(puntero!!, nombre,null,1)
            var actualizar = base.writableDatabase
            var datos = ContentValues()
            var actualizarID = arrayOf(id.toString())

            datos.put("DESCRIPCION", desc)
            datos.put("FECHACAPTURA", fcap)
            datos.put("FECHAENTREGA", fent)

            var res = actualizar.update("ACTIVIDADES",datos,"ID_ACTIVIDAD = ?", actualizarID)
            if(res == 0){
                return false
            }
        }catch (e: SQLiteException){
            return false
        }
        return true
    }
}