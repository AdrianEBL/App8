package edu.tecii.android.app8;

import java.io.Serializable;

/**
 * Created by Adrian on 16/10/2017.
 */

public class Contact implements Serializable {

    String Nombre, Apellido, Telefono, Correo;

    public Contact(String Nombre, String ...Datos){
        this.Nombre = Nombre;
        Apellido = Datos[0];
        Telefono = Datos[1];
        Correo = Datos[2];
    }
}
