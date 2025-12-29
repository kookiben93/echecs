import java.util.Scanner;

public class pieces {
    static boolean pep=false;
    static int ligneAvant;
    static int colonneAvant;

    public static void pion(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        Scanner sc = new Scanner(System.in);
        int pion = plateau[ligne][colonne];
        int avancer = 1;
        int reponse;
        boolean priseEnPassant = methodes.pepPossible(plateau, ligne, colonne, pion, pep, ligneAvant, colonneAvant);

        if (mode == 1) {

            // On définit les règles selon la couleur du pion
            int sens;           // -1 pour monter, 1 pour descendre
            int ligneDepart;    // La ligne où il peut avancer de 2
            int ligneFin;       // La ligne où il a une promotion
            int NvLigne = ligne;
            int NvColonne = colonne;
            int direction = 0;

            if (pion == 12) { // Cas du pion bleu
                sens = -1;
                ligneDepart = 6;
                ligneFin = 0;
            } else {         // Cas du pion jaune
                sens = 1;
                ligneDepart = 1;
                ligneFin = 7;
            }

            // On regarde ce qu'il y a autour
            boolean diagoDroite = methodes.caseValide(ligne + sens, colonne + 1) && !methodes.memeCouleurEtVide(plateau, ligne + sens, colonne + 1, pion);
            boolean diagoGauche = methodes.caseValide(ligne + sens, colonne - 1) && !methodes.memeCouleurEtVide(plateau, ligne + sens, colonne - 1, pion);
            boolean peutAvancerUn = plateau[ligne + sens][colonne] == 0;
            boolean peutAvancerDeux = (ligne == ligneDepart) && plateau[ligne + (2 * sens)][colonne] == 0;

            // Demander au joueur ce qu'il veut faire
            int choix = methodes.affichageChoixPion(diagoDroite, diagoGauche, peutAvancerUn, joueur);

            // On vide la case de départ
            plateau[ligne][colonne] = 0;

            if (priseEnPassant) {
                System.out.print("Voulez-vous faire une prise en passant ? (1 pour oui) ");
                reponse = sc.nextInt();

                if (reponse == 1) {
                    methodes.PriseEnPassant(plateau, ligne, colonne, pion);
                }
                pep = false;
            } else {
                pep = false;
                if ((diagoGauche && !diagoDroite && !peutAvancerUn) || choix == 1) { // Mouvement Diagonale Gauche
                    NvLigne = ligne + sens;
                    NvColonne = colonne - 1;
                    direction = 5;
                } else if ((!diagoGauche && diagoDroite && !peutAvancerUn) || choix == 2) { // Mouvement Diagonale Droite
                    NvLigne = ligne + sens;
                    NvColonne = colonne + 1;
                    direction = 6;
                } else if ((!diagoGauche && !diagoDroite && peutAvancerUn) || choix == 3) { // Avancer tout droit
                    if (peutAvancerDeux) {
                        do {
                            System.out.print("Avancer de 1 ou 2 cases : ");
                            avancer = Integer.parseInt(sc.nextLine());
                        } while (avancer != 1 && avancer != 2);
                        NvLigne = ligne + (avancer * sens);
                        if (avancer == 2) {
                            pep = true;
                            ligneAvant = ligne;
                            colonneAvant = colonne;
                        }
                    } else {
                        NvLigne = ligne + sens;
                    }
                    direction = 1;
                }

                // On place le pion ou la promotion
                if (ligne == ligneFin) {
                    plateau[NvLigne][NvColonne] = methodes.ChoixPromotion(pion);
                } else {
                    methodes.AffichageSituation(pion, ligne, colonne, NvLigne, NvColonne, avancer, direction);
                    plateau[NvLigne][NvColonne] = pion;
                }
            }
        }
        else{
            methodes.mouvementPion(plateau, ligne, colonne, pion);
        }
    }

    //Méthode pour la Tour
    public static void tour(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1){
            methodes.BougeTour(plateau, ligne, colonne, couleur, joueur);
        } else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur);
        }
    }

    //Méthode pour le Fou
    public static void fou(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1) {
            methodes.BougeFou(plateau, ligne, colonne, couleur, joueur);
        } else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur);
        }
    }

    //Méthode pour le Roi
    public static void roi(int[][] plateau, int ligne, int colonne, int mvtTourLoin, int mvtTourProche, int mvtRoi, char joueur, int mode) {
        Scanner sc = new Scanner(System.in);

        int couleur = plateau[ligne][colonne]; //couleur de la pièce
        int choix = 0;
        int oui=0;

        boolean TourLointaine = methodes.TourPasBougee(mvtTourLoin);
        boolean TourProche = methodes.TourPasBougee(mvtTourProche);

        boolean mouvementAzero = methodes.nbMouvementsTourRoi(TourLointaine, TourProche, mvtRoi);
        boolean petit = methodes.PetitRoque(plateau, ligne, colonne, TourProche);
        boolean grand = methodes.GrandRoque(plateau, ligne, colonne, TourLointaine);

        boolean haut = methodes.haut(plateau, ligne, colonne, joueur);
        boolean gauche = methodes.gauche(plateau, ligne, colonne, joueur);
        boolean droite = methodes.droite(plateau, ligne, colonne, joueur);
        boolean bas = methodes.bas(plateau, ligne, colonne, joueur);

        boolean hautGauche = methodes.hautGauche(plateau, ligne, colonne, couleur);
        boolean hautDroite = methodes.hautDroite(plateau, ligne, colonne, couleur);
        boolean basGauche = methodes.basGauche(plateau, ligne, colonne, couleur);
        boolean basDroite = methodes.basDroite(plateau, ligne, colonne, couleur);

        boolean ligneDroite = (haut || gauche || droite || bas);
        boolean diagonale = (hautGauche || hautDroite || basGauche || basDroite);

        if(mouvementAzero && (petit || grand)){
            System.out.print("Voulez-vous roquer ? (1 pour oui, n'importe pour non) : ");
            oui = sc.nextInt();
        }
        if(oui==1){
            methodes.roque(plateau, couleur, ligne, colonne, TourLointaine, TourProche);
        }
        else {
            if (ligneDroite && diagonale) {
                while (choix != 1 && choix != 2) {
                    System.out.println("1 pour aller en ligne droite, 2 pour aller en diagonale");
                    choix = Integer.parseInt(sc.nextLine());
                }
            } else if (ligneDroite){
                choix = 1;
            } else if(diagonale){
                choix = 2;
            }

            if (choix == 1) {
                methodes.BougeTour(plateau, ligne, colonne, couleur, joueur);
            }
            else if (choix == 2){
                methodes.BougeFou(plateau, ligne, colonne, couleur, joueur);
            }
        }
    }

    //Méthode pour la Cavalier
    public static void cavalier(int[][] plateau, int ligne, int colonne, int mode) {
        int NvColonne;
        int NvLigne;
        String col = methodes.conversionEnString(colonne);
        String NvCol = "";
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if (mode == 1){
            int hautBas = 0;    //valeur pour le mouvement Haut ou Bas selon la demande
            int gaucheDroite = 0;   //valeur pour le mouvement gauche ou droite selon la demande

            int direction = methodes.directionCavalier(plateau, ligne, colonne, couleur);

            switch (direction) {
                case 1:     //L Grand Haut Gauche
                    hautBas = -2;
                    gaucheDroite = -1;
                    break;
                case 2:        //L Grand Haut Droit
                    hautBas = -2;
                    gaucheDroite = 1;
                    break;
                case 3:         //L Petit Haut Gauche
                    hautBas = -1;
                    gaucheDroite = -2;
                    break;
                case 4:         //L Petit Haut Droit
                    hautBas = -1;
                    gaucheDroite = 2;
                    break;
                case 5:         //L Grand Bas Gauche
                    hautBas = 2;
                    gaucheDroite = -1;
                    break;
                case 6:         //L Grand Bas Droit
                    hautBas = 2;
                    gaucheDroite = 1;
                    break;
                case 7:         //L Petit Bas Gauche
                    hautBas = 1;
                    gaucheDroite = -2;
                    break;
                case 8:         //L Petit Bas Droit
                    hautBas = 1;
                    gaucheDroite = 2;
                    break;
            }
            NvLigne = ligne + hautBas;
            NvColonne = colonne + gaucheDroite;

            System.out.println("Le cavalier s'est déplacé en L de la case (" + (ligne + 1) + ',' + col + ") jusqu'à la case (" + (NvLigne + 1) + "," + NvCol + ")");
            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }
        else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur);
        }
    }

    public static void dame(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale : ");
            choix = Integer.parseInt(sc.nextLine());
        } while (choix != 1 && choix != 2);

        if (choix == 1) {
            tour(plateau, ligne, colonne, mode, joueur);
        } else {
            fou(plateau, ligne, colonne, mode, joueur);
        }
    }
}
