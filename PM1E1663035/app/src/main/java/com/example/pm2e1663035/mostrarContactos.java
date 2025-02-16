package com.example.pm2e1663035;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm2e1663035.Models.Contacto;
import com.example.pm2e1663035.configuracion.SQLiteConexion;
import com.example.pm2e1663035.configuracion.Transacciones;

import java.util.ArrayList;

public class mostrarContactos extends AppCompatActivity {

    ArrayList<Contacto> Lista;
    SQLiteConexion conexion;
    ListView listView;

    Boolean confirmar = false;

    Button eliminar, modificar, compartir;

    Contacto selectedContacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_contactos);
        try {
            conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
            eliminar = (Button) findViewById(R.id.btnEliminar);

            obtenerInfo();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedContacto = (Contacto) (listView.getItemAtPosition(i));
                    AlertDialog dialog = createDialog("¿Desea llamar?",selectedContacto.getTelefono());
                    dialog.show();


                }
            });


            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteContact();
                }
            });


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void obtenerInfo() {

        SQLiteDatabase db = conexion.getReadableDatabase();

        Contacto contact = null;

        Cursor cursor = db.rawQuery(Transacciones.selectTableClient, null);

        Lista = new ArrayList<Contacto>();
        while (cursor.moveToNext()) {
            contact = new Contacto();
            contact.setId(cursor.getInt(0));
            contact.setNombre(cursor.getString(1));
            contact.setPais(cursor.getString(2));
            contact.setTelefono(cursor.getString(3));
            contact.setNota(cursor.getString(4));
            contact.setImagen(cursor.getString(5));

            Lista.add(contact);
        }


        listView = (ListView) findViewById(R.id.contactsListView);

        ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(),0,Lista);
        listView.setAdapter(adapter);
    }



    AlertDialog createDialog(String mensaje,String tel){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+tel));
                startActivity(callIntent);

            }


        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmar = false;
            }


        });
        return builder.create();
    }

    private void deleteContact(){
        try{
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.nameDB,null,1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            int Result = db.delete(Transacciones.tabla,"where id="+selectedContacto.getId(),null);

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }
}