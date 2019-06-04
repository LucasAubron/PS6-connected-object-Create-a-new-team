package fr.etudes.ps6finalandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import fr.etudes.ps6finalandroid.R;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    Spinner spinner;
    int positionSpinner = 0;

    FileAttente[] listesFileAttente = {new FileAttente("Cinéma", 2), new FileAttente("Stage Airbus",6),
        new FileAttente("Dentiste", 4), new FileAttente("Bibliothèque Universitaire", 10)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String URL = "http://192.168.99.2:3000/api/clients";
        RequestQueue rq= Volley.newRequestQueue(this);
        /*
        JsonArrayRequest arr=new JsonArrayRequest(
                Request.Method.GET,
                null,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for(int i=0;i<response.length();i++){
                                JSONObject student = response.getJSONObject(i);
                                String prenom = student.getString("prenom");
                                String nom = student.getString("nom");
                                String id = student.getString("id");

                                // Display the formatted json data in text view
                                mTextView.append(firstName +" " + lastName +"\nAge : " + age);
                                mTextView.append("\n\n");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }
        );
        */
        /*
        JsonObjectRequest obj=new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
        */
        //rq.add(obj);
        initSpinner();
        rejoindre();
        quitter();
    }

    protected void initSpinner() {
        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner = findViewById(R.id.spinner_listes);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listesFileAttente
        );

        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);
    }

    public void rejoindre(){
        final Button btn_rejoindre = findViewById(R.id.btn_rejoindre);
        final Button btn_quitter = findViewById(R.id.btn_quitter);

        btn_rejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEditText(listesFileAttente[positionSpinner].rejoindreFA());
                btn_rejoindre.setEnabled(false);
                btn_quitter.setEnabled(true);
            }
        });
    }

    public void quitter(){
        final Button btn_quitter = findViewById(R.id.btn_quitter);
        final Button btn_rejoindre = findViewById(R.id.btn_rejoindre);

        btn_quitter.setEnabled(false);
        btn_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEditText(listesFileAttente[positionSpinner].quitterFA());
                btn_quitter.setEnabled(false);
                btn_rejoindre.setEnabled(true);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        positionSpinner = position;
        changeEditText(listesFileAttente[positionSpinner].getNbrAttente());
        /* enable le bouton apres avoir changer de file d'attente */
        Button btn_rejoindre = findViewById(R.id.btn_rejoindre);
        Button btn_quitter = findViewById(R.id.btn_quitter);
        btn_rejoindre.setEnabled(!listesFileAttente[positionSpinner].estDansLaFile());
        btn_quitter.setEnabled(listesFileAttente[positionSpinner].estDansLaFile());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void changeEditText(int nombre){
        EditText editText = findViewById(R.id.txt_name);
        editText.setText(Integer.toString(nombre));
    }
}
