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
            pionN(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 12){
            pionB(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 7 || tab[ligne][colonne] == 1) {
            if(pieceAutour(tab, ligne, colonne)==true){
                    System.out.println("Impossible de bouger cette pièce");
                    coordonnees(tab);
            }
            else {
                tour(tab, ligne, colonne);
            }
        }
        else if (tab[ligne][colonne] == 9 || tab[ligne][colonne] == 3) {
            fou(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 10){
            roiB(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 5){
            roiN(tab, ligne, colonne);
        }
        else {
            System.out.println("Case vide, veuillez recommencez");
            coordonnees(tab);
        }
    }

    //booleen qui prend que les cases valide du plateau
    static boolean caseValide(int ligne, int colonne) {
        return (ligne >= 0 && ligne < 8) && (colonne >= 0 && colonne < 8);
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche et de droite
    //sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour(int[][] tab, int ligne, int colonne){
        int piece = tab[ligne][colonne];
        int nouvelleL = 0;
        int nouvelleC = 0;
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

    //affichage du plateau
    public static void plateau(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = 0; //case vide
                tab[1][j] = 6; //Pion Noir
                tab[6][j] = 12; //Pion Blanc
            }
        }

        //Tour Noire
        tab[0][0] = 1;
        tab[0][7] = 1;

        //Tour Blanche
        tab[7][0] = 7;
        tab[7][7] = 7;

        //Cheval Noir
        tab[0][1] = 2;
        tab[0][6] = 2;

        //Cheval blanc
        tab[7][1] = 8;
        tab[7][6] = 8;

        //Fou noir
        tab[0][2] = 3;
        tab[0][5] = 3;

        //Fou Blanc
        tab[7][2] = 9;
        tab[7][5] = 9;

        //Roi Noir
        tab[0][3] = 4;

        //Roi Blanc
        tab[7][4] = 10;

        //Dame Noire
        tab[0][4] = 5;

        //Dame Blanche
        tab[7][3] = 11;
    }

    //Affichage des pièces sur le plateau
    public static void remplir(int[][] tab) {
        String RESET  = "\u001B[0m";
        String BLEU   = "\u001B[34m";
        String JAUNE  = "\u001B[33m";

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] == 1){
                    System.out.print("[" + JAUNE + "   Tour   " + RESET + "]");
                }
                else if (tab[i][j] == 2){
                    System.out.print("[" + JAUNE + " Cavalier " + RESET + "]");
                }
                else if (tab[i][j] == 3){
                    System.out.print("[" + JAUNE + "   Fou    " + RESET + "]");
                }
                else if (tab[i][j] == 4){
                    System.out.print("[" + JAUNE + "   Dame   " + RESET + "]");
                }
                else if (tab[i][j] == 5){
                    System.out.print("[" + JAUNE + "   Roi    " + RESET + "]");
                }
                else if (tab[i][j] == 6){
                    System.out.print("[" + JAUNE + "   Pion   " + RESET + "]");
                }
                else if(tab[i][j] == 7){
                    System.out.print("[" + BLEU + "   Tour   " + RESET + "]");
                }
                else if (tab[i][j] == 8){
                    System.out.print("[" + BLEU + " Cavalier " + RESET + "]");
                }
                else if (tab[i][j] == 9){
                    System.out.print("[" + BLEU + "   Fou    " + RESET + "]");
                }
                else if (tab[i][j] == 10){
                    System.out.print("[" + BLEU + "   Roi    " + RESET + "]");
                }
                else if (tab[i][j] == 11){
                    System.out.print("[" + BLEU + "   Dame   " + RESET + "]");
                }
                else if (tab[i][j] == 12){
                    System.out.print("[" + BLEU + "   Pion   " + RESET + "]");
                }
                else{
                    System.out.print("[          ]");
                }
            }
            System.out.println();
        }

        System.out.println();
    }

    //Méthode pour le Pion Bleu
    public static void pionB(int[][] tab, int ligne, int colonne){
        Scanner sc = new Scanner(System.in);
        int choix = 0; //il veut avancer de combien
        int manger = 0; //il veut prendre une pièce en général
        int prise = -1; //il veut prendre une pièce précisément

        if((colonne != 7 && tab[ligne-1][colonne+1] <= 6 && tab[ligne-1][colonne+1] != 0) || (colonne != 0 && tab[ligne-1][colonne-1] <= 6 && tab[ligne-1][colonne-1] != 0)){
            System.out.println("Veux tu prendre une pièce ? (1 pour oui, n'importe pour non)");
            manger = Integer.parseInt(sc.nextLine());
        }
        if (manger == 1) {

            if (colonne + 1 <= 7 && tab[ligne-1][colonne+1] <= 6 && tab[ligne-1][colonne+1] != 0) {
                System.out.println("prendre la pièce en digonale droite ? (1 pour oui 0 pour non)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 1) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne + 1] = 12;
                }
            }
            if (colonne - 1 >= 0 && tab[ligne-1][colonne-1] <= 6 && tab[ligne-1][colonne-1] != 0) {
                System.out.println("prendre la pièce en digonale gauche ? (2 pour oui)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 2) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne - 1] = 12;
                }
            }
        }
        else {
            if (ligne == 6 && tab[ligne - 2][colonne] == 0) {
                do {
                    System.out.println("Tu veux avancer de 1 ou de 2 ");
                    choix = Integer.parseInt(sc.nextLine());
                } while (choix != 1 && choix != 2);
                if (choix == 1) {
                    tab[ligne][colonne] = 0;
                    tab[ligne - 1][colonne] = 12;
                } else {
                    tab[ligne][colonne] = 0;
                    tab[ligne - 2][colonne] = 12;
                }
            } else if (tab[ligne - 1][colonne] == 0) {
                System.out.println("Le pion avance de 1 ");
                tab[ligne][colonne] = 0;
                tab[ligne - 1][colonne] = 12;
            } else {
                System.out.println("Le pion n'a pas de mouvement ");

            }
        }
    }

    //Méthode pour le Pion Jaune
    public static void pionN(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int manger = 0; //il veut prendre un pièce en général
        int prise = -1; //il veut prendre une pièce précisément

        if((colonne != 7 && tab[ligne + 1][colonne + 1] >= 7) || (colonne != 0 && tab[ligne + 1][colonne - 1] >= 7)) {
            System.out.println("Veux tu prendre une pièce ? (1 pour oui)");
            manger = Integer.parseInt(sc.nextLine());
        }

        if (manger == 1) {
            if (colonne + 1 <= 7 && tab[ligne + 1][colonne + 1] >= 7) {
                System.out.println("Prendre la pièce en digonale droite ? (1 pour oui 0 pour non)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 1) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne + 1] = 6;
                }
            }
            if (colonne - 1 >= 0 && tab[ligne + 1][colonne - 1] >= 7) {
                System.out.println("Prendre la pièce en digonale gauche ? (2 pour oui)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 2) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne - 1] = 6;
                }
            }
        } else {
            if (ligne == 1 && tab[ligne + 2][colonne] == 0) {
                do {
                    System.out.println("Tu veux avancer de 1 ou de 2 ");
                    choix = Integer.parseInt(sc.nextLine());
                } while (choix != 1 && choix != 2);
                if (choix == 1) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne] = 6;
                } else {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 2][colonne] = 6;
                }
            } else if (tab[ligne + 1][colonne] == 0) {
                System.out.println("Le pion avance de 1 ");
                tab[ligne][colonne] = 0;
                tab[ligne + 1][colonne] = 6;
            } else {
                System.out.println("Le pion n'a pas de mouvement ");
            }
        }
    }

    //Méthode pour la Tour
    public static void tour(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int direction;
        int couleur=0; //couleur de la pièce

        if (tab[ligne][colonne] == 7) {
            couleur=7;
        }
        else if (tab[ligne][colonne] == 1) {
            couleur=1;
        }

        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas ");
        direction = Integer.parseInt(sc.nextLine());

        if (direction == 1 && tab[ligne-1][colonne] == 0) {         //aller en haut
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            while(tab[ligne-choix][colonne]>5){ 
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
        tab[ligne-choix][colonne] = couleur;

        } else if (direction == 2 && tab[ligne][colonne - 1] == 0) {    //aller à gauche
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;

            while(tab[ligne][colonne-choix]>5){ 
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
        tab[ligne][colonne-choix] = couleur;

        } else if (direction == 3 && tab[ligne][colonne+1] == 0) {    //aller à droite
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;

            while(tab[ligne][colonne+choix]>5){ 
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
        tab[ligne][colonne+choix] = couleur;

        } else if (direction == 4 && tab[ligne][colonne-1] == 0){       //aller en bas
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;

            while(tab[ligne+choix][colonne]>5){ 
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
        tab[ligne+choix][colonne] = couleur;

        } else {
            System.out.println("Impossible d'avancer ");
        }
    }

    //Méthode pour le Fou
    public static void fou(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int direction;
        int couleur=0; //couleur de la pièce

        if (tab[ligne][colonne] == 9) {
            couleur=9;
        }
        else if (tab[ligne][colonne] == 3) {
            couleur=3;
        }

        System.out.print("1 diagonale haut gauche, 2 diagonale haut droite, 3 diagonale bas droite, 4 diagonale bas gauche ");
        direction = Integer.parseInt(sc.nextLine());

        if (direction == 1 && tab[ligne-1][colonne-1] == 0) {               //aller en haut gauche
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne-choix][colonne-choix] = couleur;
        } else if (direction == 2 && tab[ligne-1][colonne+1] == 0) {        //aller en haut droite
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne-choix][colonne+choix] = couleur;
        } else if (direction == 3 && tab[ligne-1][colonne+1] == 0) {        //aller en bas droite
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne+choix][colonne+choix] = couleur;
        } else if (direction == 4 && tab[ligne+1][colonne-1] == 0) {        //aller en bas gauche
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne+choix][colonne-choix] = couleur;
        } else {
            System.out.println("Impossible d'avancer ");
        }
    }

    //Méthode pour le Roi Bleu
    public static void roiB(int[][] tab, int ligne, int colonne){
        Scanner sc = new Scanner(System.in);
        int choix1 = 0;
        int choix2 = 0;

        if ((ligne != 7 && tab[ligne+1][colonne] <= 6 )
                || (ligne != 7 && colonne != 7 && tab[ligne+1][colonne+1] <= 6)
                || (ligne != 7 && colonne != 0 && tab[ligne+1][colonne-1] <= 6)
                || (ligne != 0 && tab[ligne-1][colonne] <= 6)
                || (colonne != 7 && tab[ligne][colonne+1] <= 6)
                || (colonne != 0 && tab[ligne][colonne-1] <= 6)
                || (ligne != 0 && colonne != 7 && tab[ligne-1][colonne+1] <= 6)
                || (ligne != 0 && colonne != 0 && tab[ligne-1][colonne-1] <= 6)){
            System.out.println("1 pour aller en ligne droite, 2 pour aller en diagonale");
            choix1 = Integer.parseInt(sc.nextLine());
        }

        if (choix1 == 1 && ((ligne != 7 && tab[ligne+1][colonne] <= 6 ) || (ligne != 0 && tab[ligne-1][colonne] <= 6) || (colonne != 7 && tab[ligne][colonne+1] <= 6) || (colonne != 0 && tab[ligne][colonne-1] <= 6))){
            System.out.println("1 pour aller en bas, 2 pour aller en haut, 3 pour aller à gauche, 4 pour aller à droite");
            choix2 = Integer.parseInt(sc.nextLine());
            if (choix2 == 1 && (ligne != 7 && tab[ligne+1][colonne] <= 6 )){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne] = 10;
            }
            else if (choix2 == 2 && (ligne != 0 && tab[ligne-1][colonne] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne] = 10;
            }
            else if (choix2 == 3 && (colonne != 0 && tab[ligne][colonne-1] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne][colonne-1] = 10;
            }
            else if (choix2 == 4 && (colonne != 7 && tab[ligne][colonne+1] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne][colonne+1] = 10;
            }
            else {
                System.out.println("Impossible d'avancer ");
            }
        }
        else if (choix1 == 2 && ((ligne != 7 && colonne != 7 && tab[ligne+1][colonne+1] <= 6) || (ligne != 7 && colonne != 0 && tab[ligne+1][colonne-1] <= 6) || (ligne != 0 && colonne != 7 && tab[ligne-1][colonne+1] <= 6) || (ligne != 0 && colonne != 0 && tab[ligne-1][colonne-1] <= 6))){
            System.out.println("1 diagonale bas gauche, 2 diagonale haut gauche, 3 diagonale bas droite, 4 diagonale haut droite");
            choix2 = Integer.parseInt(sc.nextLine());
            if (choix2 == 1 && (ligne != 7 && colonne != 0 && tab[ligne+1][colonne-1] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne-1] = 10;
            }
            else if (choix2 == 2 && (ligne != 0 && colonne != 0 && tab[ligne-1][colonne-1] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne-1] = 10;
            }
            else if (choix2 == 3 && (ligne != 7 && colonne != 7 && tab[ligne+1][colonne+1] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne+1] = 10;
            }
            else if (choix2 == 4 && (ligne != 0 && colonne != 7 && tab[ligne-1][colonne+1] <= 6)){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne+1] = 10;
            }
            else {
                System.out.println("Impossible d'avancer ");
            }

        } else {
            System.out.println("Impossible d'avancer ");
        }

    }

    //Méthode pour le Roi Jaune
    public static void roiN(int[][] tab, int ligne, int colonne){
        Scanner sc = new Scanner(System.in);
        int choix1 = 0;
        int choix2 = 0;

        if ((ligne != 7 && (tab[ligne+1][colonne] >= 7 || tab[ligne+1][colonne] == 0))
                || (ligne != 0 && (tab[ligne-1][colonne] >= 7 || tab[ligne-1][colonne] == 0))
                || (colonne != 7 && (tab[ligne][colonne+1] >= 7 || tab[ligne][colonne+1] == 0))
                || (colonne != 0 && (tab[ligne][colonne-1] >= 7 || tab[ligne][colonne-1] == 0))
                || (ligne != 7 && colonne != 7 && (tab[ligne+1][colonne+1] >= 7 || tab[ligne+1][colonne+1] == 0))
                || (ligne != 7 && colonne != 0 && (tab[ligne+1][colonne-1] >= 7 || tab[ligne+1][colonne-1] == 0))
                || (ligne != 0 && colonne != 7 && (tab[ligne-1][colonne+1] >= 7 || tab[ligne-1][colonne+1] == 0))
                || (ligne != 0 && colonne != 0 && (tab[ligne-1][colonne-1] >= 7 || tab[ligne-1][colonne-1] == 0))){
            System.out.println("1 pour aller en ligne droite, 2 pour aller en diagonale");
            choix1 = Integer.parseInt(sc.nextLine());
        }

        if (choix1 == 1 && ((ligne != 7 && (tab[ligne+1][colonne] >= 7 || tab[ligne+1][colonne] == 0))
                || (ligne != 0 && (tab[ligne-1][colonne] >= 7 || tab[ligne-1][colonne] == 0))
                || (colonne != 7 && (tab[ligne][colonne+1] >= 7 || tab[ligne][colonne+1] == 0))
                || (colonne != 0 && (tab[ligne][colonne-1] >= 7 || tab[ligne][colonne-1] == 0)))){
            System.out.println("1 pour aller en bas, 2 pour aller en haut, 3 pour aller à gauche, 4 pour aller à droite");
            choix2 = Integer.parseInt(sc.nextLine());
            if (choix2 == 1 && (ligne != 7 && (tab[ligne+1][colonne] >= 7 || tab[ligne+1][colonne] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne] = 5;
            }
            else if (choix2 == 2 && (ligne != 0 && (tab[ligne-1][colonne] >= 7 || tab[ligne-1][colonne] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne] = 5;
            }
            else if (choix2 == 3 && (colonne != 0 && (tab[ligne][colonne-1] >= 7 || tab[ligne][colonne-1] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne][colonne-1] = 5;
            }
            else if (choix2 == 4 && (colonne != 7 && (tab[ligne][colonne+1] >= 7 || tab[ligne][colonne+1] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne][colonne+1] = 5;
            }
            else {
                System.out.println("Impossible d'avancer ");
            }
        }
        else if (choix1 == 2 && ((ligne != 7 && colonne != 7 && (tab[ligne+1][colonne+1] >= 7 || tab[ligne+1][colonne+1] == 0))
                || (ligne != 7 && colonne != 0 && (tab[ligne+1][colonne-1] >= 7 || tab[ligne+1][colonne-1] == 0))
                || (ligne != 0 && colonne != 7 && (tab[ligne-1][colonne+1] >= 7 || tab[ligne-1][colonne+1] == 0))
                || (ligne != 0 && colonne != 0 && (tab[ligne-1][colonne-1] >= 7 || tab[ligne-1][colonne-1] == 0)))){
            System.out.println("1 diagonale bas gauche, 2 diagonale haut gauche, 3 diagonale bas droite, 4 diagonale haut droite");
            choix2 = Integer.parseInt(sc.nextLine());
            if (choix2 == 1 && (ligne != 7 && colonne != 0 && (tab[ligne+1][colonne-1] >= 7 || tab[ligne+1][colonne-1] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne-1] = 5;
            }
            else if (choix2 == 2 && (ligne != 0 && colonne != 0 && (tab[ligne-1][colonne-1] >= 7 || tab[ligne-1][colonne-1] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne-1] = 5;
            }
            else if (choix2 == 3 && (ligne != 7 && colonne != 7 && (tab[ligne+1][colonne+1] >= 7 || tab[ligne+1][colonne+1] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne+1] = 5;
            }
            else if (choix2 == 4 && (ligne != 0 && colonne != 7 && (tab[ligne-1][colonne+1] >= 7 || tab[ligne-1][colonne+1] == 0))){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne+1] = 5;
            }
            else {
                System.out.println("Impossible d'avancer ");
            }

        } else {
            System.out.println("Impossible d'avancer ");
        }

    }
}
