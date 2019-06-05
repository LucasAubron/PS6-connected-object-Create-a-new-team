package fr.etudes.ps6finalandroid.models;

import android.content.Context;

import org.json.JSONArray;

import fr.etudes.ps6finalandroid.utils.ServerCallBack;
import fr.etudes.ps6finalandroid.utils.Utils;

public class FileAttente {
    public String getNom() {
        return nom;
    }

    public int getNbrAttente() {
        return nbrAttente;
    }

    public int getPlace() {
        return place;
    }

    private String nom;
    private int nbrAttente;
    private int place;
    private int id;

    public FileAttente(String nom, int nbrAttente, int id){
        this.nom = nom;
        this.nbrAttente = nbrAttente;
        this.place = -1;
        this.id=id;
    }

    /**
     * Permet de rejoindre la file d'attente
     * @return le nombre de personnes dans la file d'attente
     */
    public int rejoindreFA(Context context){
        String req = "";
        Utils.post(this.id, context, req);
        return nbrAttente;
    }

    /**
     * Permet de quitter la file d'attente
     * @return le nombre de personnes dans la file d'attente
     */
    public int quitterFA(){
        place = -1;
        return decrementerNbrAttente();
    }

    public boolean estDansLaFile(){
        return place > 0;
    }

    /**
     * Méthode à utiliser lorsque le premier de la file passe
     * @return true si l'utilisateur est celui qui passe
     */
    public boolean next(){
        decrementerNbrAttente();
        place--;
        return !estDansLaFile();
    }

    /**
     * Ajoute une personne à la fin de la file d'attente
     */
    public void ajouterPersonneFA(){
        nbrAttente++;
    }

    /**
     * Retire une personne de la file d'attente
     * /*\ A ne pas utiliser pour retirer l'utilisateur
     * @param placePersonne la place de la personne
     */
    public void retirerPersonneFA(int placePersonne){
        if (placePersonne < place)
            place--;
        decrementerNbrAttente();
    }

    private int decrementerNbrAttente(){
        if (nbrAttente > 0) nbrAttente--;
        return nbrAttente;
    }


    @Override
    public String toString(){
        return nom;
    }
}
