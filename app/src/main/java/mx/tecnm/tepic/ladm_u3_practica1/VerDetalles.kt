package mx.tecnm.tepic.ladm_u3_practica1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.textView1
import kotlinx.android.synthetic.main.activity_ver_detalles.*
import java.lang.Exception

class VerDetalles : AppCompatActivity() {
    var listaID = ArrayList<String>()
    var id = ""
    var foto:ByteArray? = ByteArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_detalles)

        var extras = intent.extras
        id = extras!!.getString("id").toString()

        cargaInformacion()

        volverDet.setOnClickListener {
            finish()
        }
    }

    private fun cargaInformacion(){
        try {
            var c = Actividad("","","")
            c.asignarPuntero(this)
            var datos = c.consultaID(id)

            textView1.setText(datos.desc)
            textView2.setText(datos.fcap)
            textView3.setText(datos.fent)

            var e = Evidencia("",ByteArray(0))
            e.asignarPuntero(this)
            var data = e.recuperarDatos(id)

            var tamaño = data.size-1
            var v = Array<String>(data.size,{""})

            listaID = ArrayList<String>()
            (0..tamaño).forEach {
                var evidencia = data[it]
                var item = "ID_EVIDENCIA: "+evidencia.id.toString()+"\n"+"ID_ACTIVIDAD: "+evidencia.id_act
                v[it] = item
                listaID.add(evidencia.id.toString())
            }

            listaFotos.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,v)

            listaFotos.setOnItemClickListener { parent, view, position, id ->
                var img = Evidencia("",ByteArray(0))
                img.asignarPuntero(this)
                foto = img.buscaFoto(listaID.get(position))

                fotoEvidencia.setImageBitmap(convierteFoto(foto))
            }

        }catch (e: Exception){
            mensaje(e.message.toString())
        }
    }

    private fun convierteFoto(imagen: ByteArray?): Bitmap? {
        return BitmapFactory.decodeByteArray(imagen, 0, imagen!!.size)
    }

    private fun mensaje(s: String) {
        AlertDialog.Builder(this).setTitle("ATENCION").setMessage(s)
            .setPositiveButton("OK"){
                    d,i-> d.dismiss()
            }
            .show()
    }
}