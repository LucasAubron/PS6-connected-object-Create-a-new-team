package fr.etudes.ps6finalandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    String[] listes_fileAttente = {"Cinéma", "Stage Airbus", "Dentiste"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSpinner();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),listes_fileAttente[position] , Toast.LENGTH_LONG).show();
        EditText editText = findViewById(R.id.txt_name);
        editText.setText(listes_fileAttente[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
