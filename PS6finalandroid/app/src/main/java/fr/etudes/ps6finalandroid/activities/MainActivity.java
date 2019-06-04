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
import android.widget.TextView;

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
    int numFA = 0;

    FileAttente[] listesFileAttente = {new FileAttente("Cinéma", 2), new FileAttente("Stage Airbus",6),
        new FileAttente("Dentiste", 4), new FileAttente("Bibliothèque Universitaire", 10)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String URL = "http://192.168.99.2:3000/api/clients";
        RequestQueue rq= Volley.newRequestQueue(this);
        JsonArrayRequest arr=new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject client = response.getJSONObject(i);
                                String prenom = client.getString("prenom");
                                String nom = client.getString("nom");
                                String id = client.getString("id");
                                System.out.println(nom+" "+prenom+"\n");
                            }
                        } catch (JSONException e){
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
        rq.add(arr);
        initSpinner();
        initRejoindre();
        initQuitter();
        initMoinsFA();
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

    public void initRejoindre(){
        final Button btn_rejoindre = findViewById(R.id.btn_rejoindre);
        final Button btn_quitter = findViewById(R.id.btn_quitter);

        btn_rejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEditText(listesFileAttente[numFA].rejoindreFA());
                btn_rejoindre.setEnabled(false);
                btn_quitter.setEnabled(true);
            }
        });
    }

    public void initQuitter(){
        final Button btn_quitter = findViewById(R.id.btn_quitter);
        final Button btn_rejoindre = findViewById(R.id.btn_rejoindre);

        btn_quitter.setEnabled(false);
        btn_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEditText(listesFileAttente[numFA].quitterFA());
                btn_quitter.setEnabled(false);
                btn_rejoindre.setEnabled(true);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numFA = position;
        changeEditText(listesFileAttente[numFA].getNbrAttente());
        /* enable le bouton apres avoir changer de file d'attente */
        Button btn_rejoindre = findViewById(R.id.btn_rejoindre);
        Button btn_quitter = findViewById(R.id.btn_quitter);
        TextView msg_pret = findViewById(R.id.txt_ready);
        btn_rejoindre.setEnabled(!listesFileAttente[numFA].estDansLaFile());
        btn_quitter.setEnabled(listesFileAttente[numFA].estDansLaFile());
        msg_pret.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void changeEditText(int nombre){
        TextView textView = findViewById(R.id.txt_place);
        textView.setText(Integer.toString(nombre));
    }

    protected void changeEditText(String s){
        TextView textView = findViewById(R.id.txt_place);
        textView.setText(s);
    }

    /**
     * Pour faire avancer d'une place une file d'attente
     * @param numFA le numéro de la file d'attente en question
     */
    public void next(int numFA){
        if(listesFileAttente[numFA].next()){
            //C'est à l'utilisateur de passer
            final Button btnRejoindre = findViewById(R.id.btn_rejoindre);
            btnRejoindre.setEnabled(true);
            final Button btnQuitter = findViewById(R.id.btn_quitter);
            btnQuitter.setEnabled(false);
            TextView msg_pret = findViewById(R.id.txt_ready);
            msg_pret.setVisibility(View.VISIBLE);
            changeEditText("A vous !");
        }
        else
            changeEditText(listesFileAttente[numFA].getPlace());
    }

    /**
     * Pour ajouter une personne à la fin d'une file d'attente
     * @param numFA le numéro de la file d'attente en question
     */
    public void ajouterPersonneFA(int numFA){
        listesFileAttente[numFA].ajouterPersonneFA();
    }

    /**
     * Retire une personne de la file d'attente
     * /*\ A ne pas utiliser pour retirer l'utilisateur
     * @Param numFA le numéro de la file d'attente en question
     * @Param numPersonne le numéro de la personne qui se retire dans la file d'attente
     */
    public void retirerPersonneFA(int numFA, int numPersonne){
        listesFileAttente[numFA].retirerPersonneFA(numPersonne);
    }

    /**
     * Non final
     * permet au docteur de faire passer le patient suivant
     */
    public void initMoinsFA(){
        final Button btn_moins = findViewById(R.id.btn_moins);

        btn_moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(numFA);

            }
        });
    }
}
