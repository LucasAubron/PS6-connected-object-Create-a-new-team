package fr.etudes.ps6finalandroid.activities;

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

    public FileAttente(String nom, int nbrAttente){
        this.nom = nom;
        this.nbrAttente = nbrAttente;
        this.place = 0;
    }

    /**
     * Permet de rejoindre la file d'attente
     * @return le nombre de personnes dans la file d'attente
     */
    public int rejoindreFA(){
        place = nbrAttente;
        return ++nbrAttente;
    }

    /**
     * Permet de quitter la file d'attente
     * @return le nombre de personnes dans la file d'attente
     */
    public int quitterFA(){
        place = 0;
        return --nbrAttente;
    }

    public boolean estDansLaFile(){
        return place != 0;
    }
}
