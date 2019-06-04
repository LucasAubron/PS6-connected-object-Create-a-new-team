package fr.etudes.ps6finalandroid.models;

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
        this.place = -1;
    }

    /**
     * Permet de rejoindre la file d'attente
     * @return le nombre de personnes dans la file d'attente
     */
    public int rejoindreFA(){
        place = ++nbrAttente;
        return nbrAttente;
    }

    /**
     * Permet de quitter la file d'attente
     * @return le nombre de personnes dans la file d'attente
     */
    public int quitterFA(){
        place = -1;
        return --nbrAttente;
    }

    public boolean estDansLaFile(){
        return place > 0;
    }

    /**
     * Méthode à utiliser lorsque le premier de la file passe
     * @return true si l'utilisateur est celui qui passe
     */
    public boolean next(){
        nbrAttente--;
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
            nbrAttente--;
    }


    @Override
    public String toString(){
        return nom;
    }
}