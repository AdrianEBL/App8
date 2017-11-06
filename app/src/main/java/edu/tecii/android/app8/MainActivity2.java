package edu.tecii.android.app8;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class MainActivity2 extends AppCompatActivity {

    EditText edtxt1, edtxt2, edtxt3, edtxt4;
    Button btn2, btn3;
    String indiceLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edtxt1 = (EditText)findViewById(R.id.editText);
        edtxt2 = (EditText)findViewById(R.id.editText2);
        edtxt3 = (EditText)findViewById(R.id.editText3);
        edtxt4 = (EditText)findViewById(R.id.editText4);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);

        try {
            indiceLista = getIntent().getExtras().getString("id");
            edtxt1.setText(getIntent().getExtras().getString("nombre"));
            edtxt2.setText(getIntent().getExtras().getString("apellido"));
            edtxt3.setText(getIntent().getExtras().getString("telefono"));
            edtxt4.setText(getIntent().getExtras().getString("correo"));
        } catch (Exception e) { }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarModificar();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (indiceLista != null || indiceLista != "") {
                        MainActivity.lista.remove(Integer.parseInt(indiceLista));
                        guardarArchivo();
                        Toast.makeText(MainActivity2.this, "Elemento Borrado Exitosamente",
                                Toast.LENGTH_SHORT).show();
                        edtxt1.setText("");
                        edtxt2.setText("");
                        edtxt3.setText("");
                        edtxt4.setText("");
                        indiceLista = "";
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity2.this, "Error! Debes seleccionar un elemento de la lista",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void guardarModificar() {
        Contact contact = new Contact(edtxt1.getText().toString(), edtxt2.getText().toString(),
                edtxt3.getText().toString(), edtxt4.getText().toString());
        if (indiceLista == null || indiceLista == "") {
            MainActivity.lista.add(contact);
            guardarArchivo();
            Toast.makeText(MainActivity2.this, "Elemento Guardado", Toast.LENGTH_SHORT).show();
        } else {
            MainActivity.lista.set(Integer.parseInt(indiceLista), contact);
            guardarArchivo();
            Toast.makeText(MainActivity2.this, "Elemento Modificado Exitosamente",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean hasExternalStorage() {
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED)){
            return true;
        } else {
            return false;
        }
    }

    public void guardarArchivo() {
        try {
            if (hasExternalStorage()){
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "archivo.txt");
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file, false));
                stream.writeObject(MainActivity.lista);
                stream.close();
            }
        } catch (Exception e) {
        }
    }
}
