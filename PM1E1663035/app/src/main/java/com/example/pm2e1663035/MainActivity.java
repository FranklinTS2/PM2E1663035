package com.example.pm2e1663035;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm2e1663035.Models.Contacto;
import com.example.pm2e1663035.configuracion.SQLiteConexion;
import com.example.pm2e1663035.configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    Button btnSalvar, btnMostrar;
    ImageView imagen;

    EditText nombre,telefono,nota;

    Spinner spinner;

    Contacto contacto = new Contacto();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView) findViewById(R.id.IMG);
        imagen.setImageResource(R.drawable.camera);

        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        nota = (EditText) findViewById(R.id.txtNota);
        spinner = (Spinner) findViewById(R.id.cmbPais);

        btnSalvar = (Button) findViewById(R.id.btnSalvarContacto);
        btnMostrar = (Button) findViewById(R.id.btnListado);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddContact();

            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),mostrarContactos.class);
                startActivity(intent);

            }
        });







    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmapImg = (Bitmap) extras.get("data");

            imagen.setImageBitmap(bitmapImg);
            contacto.setImagen(imageUtils.encodeToBase64(bitmapImg));
        }
    }

    private void AddContact(){
        try{
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.nameDB,null,1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            if(contacto.getImagen().isEmpty() || contacto.getImagen() == null){
                Toast.makeText(this,"Imagen no ha sido registrada",Toast.LENGTH_LONG).show();
            }else {
                if(nombre.getText().toString().trim().isEmpty()){
                    Toast.makeText(this,"Debe ingresar un nombre",Toast.LENGTH_SHORT).show();
                }else {
                    if(telefono.getText().toString().trim().isEmpty()){
                        Toast.makeText(this,"Debe ingresar un telefono",Toast.LENGTH_SHORT).show();
                    }else {
                        if(nota.getText().toString().trim().isEmpty()){
                            Toast.makeText(this,"Debe ingresar una nota",Toast.LENGTH_SHORT).show();
                        }else {
                            ContentValues valores = new ContentValues();
                            valores.put(Transacciones.nombres, nombre.getText().toString());
                            valores.put(Transacciones.nacionalidad, spinner.getSelectedItem().toString());
                            valores.put(Transacciones.telefono, telefono.getText().toString());
                            valores.put(Transacciones.nota, nota.getText().toString());
                            valores.put(Transacciones.imagen, contacto.getImagen());
                            Long Result = db.insert(Transacciones.tabla, Transacciones.id, valores);

                            Toast.makeText(this, "Registros ingresados exitosamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }
}