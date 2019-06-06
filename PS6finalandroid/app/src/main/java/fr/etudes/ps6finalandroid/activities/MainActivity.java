package fr.etudes.ps6finalandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.etudes.ps6finalandroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bouton = findViewById(R.id.bouton);
        bouton.setVisibility(View.INVISIBLE);
        /*
        Utils.get( 1,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[0] = new FileAttente("Médecin1", responseArray.length(), 1);
            }});
        Utils.get( 2,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[1] = new FileAttente("Médecin2", responseArray.length(), 2);
            }});
        Utils.get( 3,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[2] = new FileAttente("Médecin3", responseArray.length(), 3);
            }});
        Utils.get( 4,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray responseArray, JSONObject response) {
                listeFileAttente[3] = new FileAttente("Médecin4", responseArray.length(), 4);
            }});
        try {
            TimeUnit.MILLISECONDS.sleep(46);
        } catch(Exception e) {
        }
        */
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void activerBouton(String texte){
        Button bouton = findViewById(R.id.bouton);
        bouton.setText(texte);
        bouton.setVisibility(View.VISIBLE);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back end
            }
        });
    }

    private void changerTexte(String s){
        TextView texte = findViewById(R.id.texte);
        texte.setText(s);
    }

    private void changerNombre(int n){
        TextView nombre = findViewById(R.id.nombre);
        nombre.setText(String.valueOf(n));
    }

}
