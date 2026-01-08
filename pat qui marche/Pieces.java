public class Pieces {

    //Méthode pour le Pion
    public static void pion(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        int pion = plateau[ligne][colonne]; //couleur de la pièce

        if (mode == 1) {
            Methodes.BougePion(plateau, ligne, colonne, joueur, pion);
        }
        else{
            Methodes.mouvementPion(plateau, ligne, colonne, pion, joueur);
        }
    }

    //Méthode pour la Tour
    public static void tour(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int tour = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1){
            Methodes.BougeTour(plateau, ligne, colonne, tour, joueur);
        } else{
            Methodes.destinationPiece(plateau, ligne, colonne, tour, joueur);
        }
    }

    //Méthode pour le Fou
    public static void fou(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int fou = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1) {
            Methodes.BougeFou(plateau, ligne, colonne, fou, joueur);
        } else{
            Methodes.destinationPiece(plateau, ligne, colonne, fou, joueur);
        }
    }

    //Méthode pour le Roi
    public static void roi(int[][] plateau, int ligne, int colonne, int mvtTourLoin, int mvtTourProche, int mvtRoi, char joueur, int mode) {
        int roi = plateau[ligne][colonne]; //couleur de la pièce

        if (mode == 1) {
            Methodes.demandeRoque(plateau, ligne, colonne, roi, joueur, mvtTourLoin, mvtTourProche, mvtRoi, 1);
        } else {
            Methodes.demandeRoque(plateau, ligne, colonne, roi, joueur, mvtTourLoin, mvtTourProche, mvtRoi, 2);
        }
    }

    //Méthode pour le Cavalier
    public static void cavalier(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int NvColonne;
        int NvLigne;
        String col = Methodes.conversionEnString(colonne);
        int cavalier = plateau[ligne][colonne]; //couleur de la pièce

        if (mode == 1){
            int hautBas = 0;    //valeur pour le mouvement Haut ou Bas selon la demande
            int gaucheDroite = 0;   //valeur pour le mouvement gauche ou droite selon la demande

            //appel de méthode pour connaître la mouvement en L du cavalier choisi par le joueur
            int direction = Methodes.AffichageDirectionsCavalier(plateau, ligne, colonne, cavalier);

            //coordoonées des ligne et colonne en fonction de la direction choisie
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
            //création des nouvelles coordoonnées en fonction de la direction choisie
            NvLigne = ligne + hautBas;
            NvColonne = colonne + gaucheDroite;

            String NvCol = Methodes.conversionEnString(NvColonne);

            //affichage du mouvement et déplacement de la pièce
            System.out.println();
            System.out.println("\uD83C\uDF1F " + "Le cavalier s'est déplacé en L de la case (" + (8 - ligne) + ',' + col + ") jusqu'à la case (" + (8 - NvLigne) + "," + NvCol + ")");
            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = cavalier;
        }
        else{           //mode 2 cavalier
            Methodes.destinationPiece(plateau, ligne, colonne, cavalier, joueur);
        }
    }

    //Méthode pour la Dame
    public static void dame(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        int dame = plateau[ligne][colonne];

        if (mode == 1) {
            Methodes.BougeDame(plateau, ligne, colonne, joueur, dame, mode);
        } else {
            Methodes.destinationPiece(plateau, ligne, colonne, dame, joueur);
        }
    }
}
