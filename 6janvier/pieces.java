
public class pieces {

    //Méthode pour le Pion
    public static void pion(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        int pion = plateau[ligne][colonne];

        if (mode == 1) {
            methodes.BougePion(plateau, ligne, colonne, joueur, pion);
        }
        else{
            methodes.mouvementPion(plateau, ligne, colonne, pion, joueur);
        }
    }

    //Méthode pour la Tour
    public static void tour(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1){
            methodes.BougeTour(plateau, ligne, colonne, couleur, joueur);
        } else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur, joueur);
        }
    }

    //Méthode pour le Fou
    public static void fou(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1) {
            methodes.BougeFou(plateau, ligne, colonne, couleur, joueur);
        } else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur, joueur);
        }
    }

    //Méthode pour le Roi
    public static void roi(int[][] plateau, int ligne, int colonne, int mvtTourLoin, int mvtTourProche, int mvtRoi, char joueur, int mode) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if (mode == 1) {
            methodes.BougeRoi(plateau, ligne, colonne, joueur, couleur, mvtTourLoin, mvtTourProche, mvtRoi);
        } else {
            methodes.mouvementRoi(plateau, ligne, colonne, couleur, joueur);
        }
    }

    //Méthode pour le Cavalier
    public static void cavalier(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
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
            methodes.destinationPiece(plateau, ligne, colonne, couleur, joueur);
        }
    }

    //Méthode pour la Dame
    public static void dame(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        int couleur = plateau[ligne][colonne];

        if (mode == 1) {
            methodes.BougeDame(plateau, ligne, colonne, joueur, couleur, mode);
        } else {
            methodes.destinationPiece(plateau, ligne, colonne, couleur, joueur);
        }
    }
}
