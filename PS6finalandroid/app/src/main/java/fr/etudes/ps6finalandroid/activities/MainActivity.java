package fr.etudes.ps6finalandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.concurrent.TimeUnit;

import fr.etudes.ps6finalandroid.R;
import fr.etudes.ps6finalandroid.utils.Parser;
import fr.etudes.ps6finalandroid.models.FileAttente;
import fr.etudes.ps6finalandroid.utils.ServerCallBack;
import fr.etudes.ps6finalandroid.utils.Utils;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int numFA = 0;
    String fileName = "src";

    private FileAttente[] listeFileAttente= new FileAttente[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.get( 1,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray response) {
                listeFileAttente[0] = new FileAttente("Médecin1", response.length());
            }});
        Utils.get( 2,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray response) {
                listeFileAttente[1] = new FileAttente("Médecin2", response.length());
            }});
        Utils.get( 3,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray response) {
                listeFileAttente[2] = new FileAttente("Médecin3", response.length());
            }});
        Utils.get( 4,this, new ServerCallBack(){
            @Override
            public void onSuccess(JSONArray response) {
                listeFileAttente[3] = new FileAttente("Médecin4", response.length());
            }});
        try {
            TimeUnit.MILLISECONDS.sleep(46);
        } catch(Exception e) {
        }
        initSpinner();
        initRejoindre();
        initQuitter();
        initMoinsFA();
        Parser parser = new Parser(fileName);
        byte[] bytes = parser.read(getApplicationContext(), listeFileAttente.length+1);
        setFA((int)bytes[0]);
        for (int i = 1; i != bytes.length; i++){
            if (bytes[i] == (byte)1) {
                changeEditText(listeFileAttente[i-1].rejoindreFA());
                demanderServeurPlace(i - 1);
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Parser parser = new Parser(fileName);
        byte[] bytes = new byte[listeFileAttente.length+1];
        bytes[0] = (byte)numFA;
        for (int i = 1; i != bytes.length; i++){
            bytes[i] = listeFileAttente[i-1].estDansLaFile() ? (byte)1 : (byte)0;
        }
        parser.write(bytes, getApplicationContext());
    }

    protected void initSpinner() {
        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        Spinner spinner = findViewById(R.id.spinner_listes);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<FileAttente> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listeFileAttente
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
                changeEditText(listeFileAttente[numFA].rejoindreFA());
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
                changeEditText(listeFileAttente[numFA].quitterFA());
                btn_quitter.setEnabled(false);
                btn_rejoindre.setEnabled(true);

            }
        });
    }

    public void setFA(int numFA){
        this.numFA = numFA;
        Spinner spinner = findViewById(R.id.spinner_listes);
        spinner.setSelection(numFA);
    }

    /**
     * Pour changer de file d'attente
     * NE change pas l'item dans le spinner ni la variable globalle numFA
     */
    private void changeFA(){
        changeEditText(listeFileAttente[numFA].getNbrAttente());
        /* enable le bouton apres avoir changer de file d'attente */
        Button btn_rejoindre = findViewById(R.id.btn_rejoindre);
        Button btn_quitter = findViewById(R.id.btn_quitter);
        TextView msg_pret = findViewById(R.id.txt_ready);

        btn_rejoindre.setEnabled(!listeFileAttente[numFA].estDansLaFile());
        btn_quitter.setEnabled(listeFileAttente[numFA].estDansLaFile());
        msg_pret.setVisibility(View.INVISIBLE);
    }

    /**
     * Lorsqu'un item de la liste est selectionné
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numFA = position;
        changeFA();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * A appeler lorsque l'utilisateur doit passer
     */
    private void doitPasser(){
        final Button btnRejoindre = findViewById(R.id.btn_rejoindre);
        btnRejoindre.setEnabled(true);
        final Button btnQuitter = findViewById(R.id.btn_quitter);
        btnQuitter.setEnabled(false);
        TextView msg_pret = findViewById(R.id.txt_ready);
        msg_pret.setVisibility(View.VISIBLE);
        changeEditText("A vous !");
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
        if(listeFileAttente[numFA].next()){
            //C'est à l'utilisateur de passer
            doitPasser();
        }
        else
            changeEditText(listeFileAttente[numFA].getPlace());
    }

    /**
     * Pour ajouter une personne à la fin d'une file d'attente
     * @param numFA le numéro de la file d'attente en question
     */
    public void ajouterPersonneFA(int numFA){
        listeFileAttente[numFA].ajouterPersonneFA();
    }

    /**
     * Retire une personne de la file d'attente
     * /*\ A ne pas utiliser pour retirer l'utilisateur
     * @Param numFA le numéro de la file d'attente en question
     * @Param numPersonne le numéro de la personne qui se retire dans la file d'attente
     */
    public void retirerPersonneFA(int numFA, int numPersonne){
        listeFileAttente[numFA].retirerPersonneFA(numPersonne);
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

    public void demanderServeurPlace(int listeNum){

    }
}
