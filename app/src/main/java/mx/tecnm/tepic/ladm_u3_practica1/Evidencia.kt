package mx.tecnm.tepic.ladm_u3_practica1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.util.*


class Evidencia(i:String, f:ByteArray?) {
    var id = 0
    var id_act = i
    var foto = f

    val nombre = "Tareas"
    var puntero : Context?= null

    fun asignarPuntero(p:Context) {
        puntero = p
    }

    fun insertar():Boolean {
        try {
            var base = BaseDatos(puntero!!, nombre, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()

            datos.put("ID_ACTIVIDAD", id_act.toInt())
            datos.put("FOTO", foto)

            var res = insertar.insert("EVIDENCIAS", "ID_EVIDENCIA",datos)

            if (res.toInt() == -1) {
                return false
            }
        }catch (e: SQLiteException) {
            return false
        }
        return true
    }

    fun recuperarDatos(id:String):ArrayList<Evidencia>{
        var data = ArrayList<Evidencia>()
        try{
            var bd = BaseDatos(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("ID_EVIDENCIA,ID_ACTIVIDAD")
            var actividad = arrayOf(id)

            var cursor  = select.query("EVIDENCIAS", columnas, "ID_ACTIVIDAD = ?", actividad, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Evidencia(cursor.getString(1), ByteArray(0))
                    temp.id = cursor.getInt(0)
                    data.add(temp)
                }while (cursor.moveToNext())
            }else{
            }
        }catch (e:SQLiteException){
        }
        return data
    }

    fun buscaFoto(id:String): ByteArray?{
        var registro = Evidencia("",ByteArray(0))

        try {
            var bd = BaseDatos(puntero!!, nombre, null, 1)
            var select = bd.readableDatabase
            var busca = arrayOf("FOTO")
            var buscaID = arrayOf(id)

            var res = select.query("EVIDENCIAS", busca, "ID_EVIDENCIA = ?",buscaID, null, null, null)
            if(res.moveToFirst()){
                registro.foto = res.getBlob(0)
            }
        }catch (e:SQLiteException){
            e.message.toString()
        }
        return registro.foto
    }

    fun eliminar(id:String):Boolean{
        try{
            var base = BaseDatos(puntero!!, nombre,null,1)
            var eliminar = base.writableDatabase
            var eliminarID = arrayOf(id)

            var res = eliminar.delete("EVIDENCIAS","ID_EVIDENCIA = ?",eliminarID)
            if(res == 0){
                return false
            }
        }catch (e:SQLiteException){
            return false
        }
        return true
    }

    fun eliminarPorAct(id:String):Boolean{
        try{
            var base = BaseDatos(puntero!!, nombre,null,1)
            var eliminar = base.writableDatabase
            var eliminarID = arrayOf(id)

            var res = eliminar.delete("EVIDENCIAS","ID_ACTIVIDAD = ?",eliminarID)
            if(res == 0){
                return false
            }
        }catch (e:SQLiteException){
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

            datos.put("ID_ACTIVIDAD", id_act)
            datos.put("FOTO", foto)

            var res = actualizar.update("EVIDENCIAS",datos,"ID_EVIDENCIA = ?", actualizarID)
            if(res == 0){
                return false
            }
        }catch (e:SQLiteException){
            return false
        }
        return true
    }
}