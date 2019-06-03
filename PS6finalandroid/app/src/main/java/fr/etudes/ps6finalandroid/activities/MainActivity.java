package fr.etudes.ps6finalandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.etudes.ps6finalandroid.R;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    Spinner spinner;
    int positionSpinner = 0;
    String[] listes_fileAttente = {"Cinéma", "Stage Airbus", "Dentiste","Bibliothèque Universitaire"};
    //int[] nb_attente = {2, 6, 4 , 10};

    FileAttente[] listesFileAttente = {new FileAttente("Cinéma", 2), new FileAttente("Stage Airbus",6),
        new FileAttente("Dentiste", 4), new FileAttente("Bibliothèque Universitaire", 10)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                listes_fileAttente
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
        //Toast.makeText(getApplicationContext(),listes_fileAttente[position] , Toast.LENGTH_LONG).show();
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
