package com.example.pm2e1663035.configuracion;

public class Transacciones {
    public static final String nameDB = "ContactosExamen";

    public static final String tabla = "contacto";

    public static final String id = "id";

    public static final String nombres = "nombres";

    public static final String nacionalidad = "nacionalidad";
    public static final String telefono = "telefono";
    public static final String nota = "nota";

    public static final String imagen = "imagen";


    public static final String CreateTableClient = "CREATE TABLE contacto ("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, nacionalidad TEXT, telefono TEXT, nota Text, imagen TEXT)";

    public static final String DROPTableClient = "DROP TABLE IF EXISTS contacto";

    public static final String selectTableClient = "SELECT * FROM " + tabla;
}
