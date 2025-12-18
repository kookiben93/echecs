import java.sql.SQLOutput;
import java.util.Scanner;

public class methodes {

    public static void main(String[] args) {

    }

    public static void coordonnees(int[][] tab){
        Scanner scanner = new Scanner(System.in);
        int colonne;
        int ligne;

        System.out.print("Quelle pièce voulez vous jouer ? ");
        System.out.print("Entrez le numéro de la ligne : ");
        ligne = (scanner.nextInt()) - 1;
        while (ligne < 0 || ligne > 7) {
            System.out.println("Coordonnées impossible");
            System.out.print("Entrez le numéro de la ligne : ");
            ligne = (scanner.nextInt()) - 1;
        }
        System.out.print("Entrez le numéro de la colonne : ");
        colonne = (scanner.nextInt()) - 1;
        while (colonne < 0 || colonne > 7) {
            System.out.println("Coordonnées impossible");
            System.out.print("Entrez le numéro de la colonne : ");
            colonne = (scanner.nextInt()) - 1;
        }
        appelPiece(tab, ligne, colonne);
    }

    //appel des méthodes en fonction de la pièce jouée
    public static void appelPiece(int[][] tab, int ligne, int colonne){
        if (tab[ligne][colonne] == 6){
            pieces.pionJ(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 12){
            pieces.pionB(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 7 || tab[ligne][colonne] == 1) {
            if(pieceAutour(tab, ligne, colonne)){
                System.out.println("Impossible de bouger cette pièce");
                coordonnees(tab);
            }
            else {
                pieces.tour(tab, ligne, colonne);
            }
        }
        else if (tab[ligne][colonne] == 9 || tab[ligne][colonne] == 3) {
            if (pieceAutour3(tab, ligne, colonne)){
                System.out.println("Impossible de bouger cette pièce");
                coordonnees(tab);
            }
            else {
                pieces.fou(tab, ligne, colonne);
            }
        }
        else if (tab[ligne][colonne] == 10 || tab[ligne][colonne] == 5){
            if (pieceAutour2(tab, ligne, colonne) && pieceAutour(tab, ligne, colonne)){
                System.out.println("Impossible de bouger cette pièce");
                coordonnees(tab);
            }
            else {
                pieces.roi(tab, ligne, colonne);
            }
        }
        else if (tab[ligne][colonne] == 8 || tab[ligne][colonne] == 2){
            pieces.cavalier(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 4 || tab[ligne][colonne] == 11){
            pieces.dame(tab, ligne, colonne);
        }
        else {
            System.out.println("Case vide, veuillez recommencez");
            coordonnees(tab);
        }
    }

    //booleen qui prend que les cases valide du plateau
    public static boolean caseValide(int ligne, int colonne) {
        return (ligne >= 0 && ligne < 8) && (colonne >= 0 && colonne < 8);
    }

    public static boolean mouvementCavalier(int[][] tab, int ligne, int colonne, int nvLigne, int nvColonne) {
        if(caseValide(nvLigne, nvColonne) &&
                (tab[nvLigne][nvColonne]!=tab[ligne-2][colonne+1] && tab[nvLigne][nvColonne]!=tab[ligne+2][colonne+1] &&
                tab[nvLigne][nvColonne]!=tab[ligne-2][colonne-1] && tab[nvLigne][nvColonne]!=tab[ligne+2][colonne-1] &&
                tab[nvLigne][nvColonne]!=tab[ligne-1][colonne+2] && tab[nvLigne][nvColonne]!=tab[ligne+1][colonne+2] &&
                tab[nvLigne][nvColonne]!=tab[ligne-1][colonne-2] && tab[nvLigne][nvColonne]!=tab[ligne+1][colonne-2])){
            return false;
        }
        return true;
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche et de droite
    //sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour(int[][] tab, int ligne, int colonne){
        int piece = tab[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {0, 0, -1, 1};
        int[] colonnes = {-1, 1, 0, 0};

        for(int i=0; i<4; i++){
            nouvelleL = ligne+lignes[i];
            nouvelleC = colonne+colonnes[i];

            if(caseValide(nouvelleL, nouvelleC)==true){
                if(tab[nouvelleL][nouvelleC]==0){
                    return false;
                }
                boolean pieceEnFace = tab[nouvelleL][nouvelleC] > 6;

                if (pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche et de droite
    //sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour3(int[][] tab, int ligne, int colonne){
        int piece = tab[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {-1, 1, 1, -1};
        int[] colonnes = {1, -1, 1, -1};

        for(int i=0; i<4; i++){
            nouvelleL = ligne+lignes[i];
            nouvelleC = colonne+colonnes[i];

            if(caseValide(nouvelleL, nouvelleC)==true){
                if(tab[nouvelleL][nouvelleC]==0){
                    return false;
                }
                boolean pieceEnFace = tab[nouvelleL][nouvelleC] > 6;

                if (pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche, de droite
    //et les 4 diagonales sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour2(int[][] tab, int ligne, int colonne){
        int piece = tab[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {-1, 1, -1, 1};
        int[] colonnes = {-1, 1, 1, -1};

        for(int i=0; i<4; i++){
            nouvelleL = ligne+lignes[i];
            nouvelleC = colonne+colonnes[i];

            if(caseValide(nouvelleL, nouvelleC)==true){
                if(tab[nouvelleL][nouvelleC]==0){
                    return false;
                }
                boolean pieceEnFace = tab[nouvelleL][nouvelleC] > 6;

                if (pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;
    }

    //affichage du plateau
    public static void plateau(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = 0; //case vide
                tab[1][j] = 6; //Pion Noir
                tab[6][j] = 12; //Pion Blanc
            }
        }

        //Tour Jaune
        tab[0][0] = 1;
        tab[0][7] = 1;

        //Tour Bleu
        tab[7][0] = 7;
        tab[7][7] = 7;

        //Cavalier Jaune
        tab[0][1] = 2;
        tab[0][6] = 2;

        //Cavalier Bleu
        tab[7][1] = 8;
        tab[7][6] = 8;

        //Fou Jaune
        tab[0][2] = 3;
        tab[0][5] = 3;

        //Fou Bleu
        tab[7][2] = 9;
        tab[7][5] = 9;

        //Dame Jaune
        tab[0][3] = 4;

        //Roi Bleu
        tab[7][4] = 10;

        //Roi Jaune
        tab[0][4] = 5;

        //Dame Bleu
        tab[7][3] = 11;
    }

    //Affichage des pièces sur le plateau
    public static void remplir(int[][] tab) {
        String RESET  = "\u001B[0m";
        String BLEU   = "\u001B[34m";
        String JAUNE  = "\u001B[33m";

        System.out.println("        1           2           3           4           5           6           7           8");;
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i+1 + " ");
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] == 1){
                    System.out.print("|" + JAUNE + "   Tour   " + RESET + "|");
                }
                else if (tab[i][j] == 2){
                    System.out.print("|" + JAUNE + " Cavalier " + RESET + "|");
                }
                else if (tab[i][j] == 3){
                    System.out.print("|" + JAUNE + "   Fou    " + RESET + "|");
                }
                else if (tab[i][j] == 4){
                    System.out.print("|" + JAUNE + "   Dame   " + RESET + "|");
                }
                else if (tab[i][j] == 5){
                    System.out.print("|" + JAUNE + "   Roi    " + RESET + "|");
                }
                else if (tab[i][j] == 6){
                    System.out.print("|" + JAUNE + "   Pion   " + RESET + "|");
                }
                else if(tab[i][j] == 7){
                    System.out.print("|" + BLEU + "   Tour   " + RESET + "|");
                }
                else if (tab[i][j] == 8){
                    System.out.print("|" + BLEU + " Cavalier " + RESET + "|");
                }
                else if (tab[i][j] == 9){
                    System.out.print("|" + BLEU + "   Fou    " + RESET + "|");
                }
                else if (tab[i][j] == 10){
                    System.out.print("|" + BLEU + "   Roi    " + RESET + "|");
                }
                else if (tab[i][j] == 11){
                    System.out.print("|" + BLEU + "   Dame   " + RESET + "|");
                }
                else if (tab[i][j] == 12){
                    System.out.print("|" + BLEU + "   Pion   " + RESET + "|");
                }
                else {
                    System.out.print("|          |");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    //méthode qui vérifie si le mouvement est possible en vérifiant
    //s'il y a des pièces empêchant un mouvement de plusieurs
    //lignes (sah g la flemme de bien expliquer sorry)
    public static boolean empechement (int[][] tab, int ligne, int colonne, int couleur, int distance, int direction){
        int dLigne = 0;
        int dColonne = 0;

        if (direction == 1) {          // haut
            dLigne = -1;
        } else if (direction == 2) {   // gauche
            dColonne = -1;
        } else if (direction == 3) {   // droite
            dColonne = 1;
        } else if (direction == 4) {   // bas
            dLigne = 1;
        }

        for (int i = 1; i < distance-1; i++) {
            int l = ligne + i * dLigne;
            int c = colonne + i * dColonne;

            if (i<distance && caseValide(l, c) && tab[l][c] != 0){
                return true;
            }
        }
        return false;
    }

    //méthode qui vérifie si la pièce actuelle est de la même couleur que
    //celle de la couleur de la pièce de notre choix en fonction de sa
    //position dans le tableau
    public static boolean memeCouleur(int[][] tab, int ligne, int colonne, int couleur) {
        if (tab[ligne][colonne] == 0) return false;

        boolean bleu = couleur > 6;
        boolean pieceEnFace = tab[ligne][colonne] > 6;

        return bleu == pieceEnFace;
    }

    //vérifie les déplacements possible grâce à un code spécifique pour chaque mouvements
    //possibles
    public static int valeurDeplacement(int[][] tab, int ligne, int colonne, int couleur){
        int valeur=0;

        if(caseValide(ligne-1, colonne) && (tab[ligne-1][colonne] == 0 || !(memeCouleur(tab, ligne-1, colonne, couleur)))) {
            valeur=valeur+4;
        }
        if(caseValide(ligne, colonne-1) && (tab[ligne][colonne-1] == 0 || !(memeCouleur(tab, ligne, colonne-1, couleur)))) {
            valeur=valeur+2;
        }
        if(caseValide(ligne, colonne+1) && (tab[ligne][colonne+1] == 0 || !(memeCouleur(tab, ligne, colonne+1, couleur)))) {
            valeur=valeur+1;
        }
        if(caseValide(ligne+1, colonne) && (tab[ligne+1][colonne] == 0 || !(memeCouleur(tab, ligne+1, colonne, couleur)))) {
            valeur=valeur+10;
        }
        return valeur;
    }

    //affichage des directions en fonctions de la position de la pièce actuelle
    public static int affichageDirections(int[][] tab, int ligne, int colonne, int couleur){
        Scanner sc = new Scanner(System.in);
        int valeurDep = valeurDeplacement(tab, ligne, colonne, couleur);
        int direction=0;


        /*if (valeurDep == 7) {                 //haut+gauche+droite
            System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 17) {
            System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 13) {           //gauche+droite+bas
            System.out.print("2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 16) {           //haut+gauche+bas
            System.out.print("1 pour aller en haut, 2 pour aller à gauche, 4 pour aller en bas : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 15) {           //haut+droite+bas
            System.out.print("1 pour aller en haut, 3 pour aller à droite, 4 pour aller en bas : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 6) {            //haut+gauche
            System.out.print("1 pour aller en haut, 2 pour aller à gauche : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 5) {            //haut+droite
            System.out.print("1 pour aller en haut, 3 pour aller à droite : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 14) {           //haut+bas
            System.out.print("1 pour aller en haut, 4 pour aller en bas : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 3) {            //gauche+droite
            System.out.print("2 pour aller à gauche, 3 pour aller à droite : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 12) {           //gauche+bas
            System.out.print("2 pour aller à gauche, 4 pour aller en bas : ");
            direction = Integer.parseInt(sc.nextLine());
        } else if (valeurDep == 11) {           //bas+droite
            System.out.print("3 pour aller à droite, 4 pour aller en bas : ");
            direction = Integer.parseInt(sc.nextLine());
        }*/
        return direction;
    }
}
