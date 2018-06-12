package com.example.probandomysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    TextView textIP, textPuerto, textContrasena, textUsuario, textBD;
    private Button buttonProbarConexion;
    static Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Asignamos a cada objeto visual creado a su
        //respectivo elemento de activity_main.xml
        textIP = findViewById(R.id.txtIP);
        textPuerto = findViewById(R.id.txtPuerto);
        textContrasena = findViewById(R.id.txtContrasena);
        textUsuario = findViewById(R.id.txtUsuario);
        textBD = findViewById(R.id.txtBD);
        buttonProbarConexion = findViewById(R.id.btProbarConexion);

        //Botón para probar la conexión a una base de datos en el servidor MySQL
        buttonProbarConexion.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Conectamos con el servidor de MySQL directamente
                try
                {
                    Tarea a = new Tarea();
                    a.execute();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class Tarea extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {
            conectarBd();
            return true;
        }

        public void conectarBd() {

            //Connection conn = null;
            String url = "jdbc:mysql://"+textIP.getText().toString()+":"+textPuerto.getText().toString()+"/"+textBD.getText().toString();
            String driver = "com.mysql.jdbc.Driver";
            String userName = textUsuario.getText().toString();
            String password = textContrasena.getText().toString();
            String resultado;
            Log.i("iniciando", "iniciando");
            try {

                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url , userName,
                        password);
                Log.i("JOSE----Conectado", "Conectadito");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from usuario");
                Log.i("JOSE----Consultando", "Realizando consulta");

                while (rs.next()) {

                    resultado = rs.getString("usu_nombre");
                    Log.i("JOSE----usuario", resultado);

                }

                conn.close();
            } catch (Exception e) {
                Log.i("JOSE----error", "error "+url+ " - "+conn);
                Log.i("JOSE----olakease", e + "");
            }

        }
    }
}
