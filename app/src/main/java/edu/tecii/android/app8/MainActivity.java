package edu.tecii.android.app8;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lstVw;
    Button btn1;
    static ArrayList<Contact> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstVw = (ListView)findViewById(R.id.listView);
        btn1 = (Button)findViewById(R.id.button);

        leerLista();
        actualizarLista();

        lstVw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact c = lista.get(i);
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("id", Integer.toString(i));
                intent.putExtra("nombre", c.Nombre);
                intent.putExtra("apellido", c.Apellido);
                intent.putExtra("telefono", c.Telefono);
                intent.putExtra("correo", c.Correo);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        leerLista();
        actualizarLista();
    }

    public void actualizarLista(){
        ArrayList<String> aux = new ArrayList<>();
        for (Contact c : lista){
            aux.add(c.Nombre + " " + c.Apellido + " " + c.Telefono + " " + c.Correo);
        }
        ArrayAdapter adapter =  new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, aux);
        lstVw.setAdapter(adapter);
    }

    public Boolean hasExternalStorage() {
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED)){
            return true;
        } else {
            return false;
        }
    }

    public void leerLista() {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "archivo.txt");
            if (hasExternalStorage() && file.exists()){
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                lista = (ArrayList<Contact>)stream.readObject();
                stream.close();
            }
        } catch (Exception e) {
        }
    }
}
