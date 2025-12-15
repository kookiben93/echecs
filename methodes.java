import java.util.Scanner;

public class methodes {

    public static void main(String[] args) {

    }

    public static int[][] coordonnees(int[][] tab){
        Scanner scanner = new Scanner(System.in);
        int colonne;
        int ligne;

        System.out.println("Quelle pièce voulez vous jouer ? ");
        System.out.println("Entrez le numéro de la ligne : ");
        ligne = (scanner.nextInt()) - 1;
        while (ligne < 0 || ligne > 7) {
            System.out.println("Coordonnées impossible");
            System.out.println("Entrez le numéro de la ligne : ");
            ligne = (scanner.nextInt()) - 1;
        }
        System.out.println("Entrez le numéro de la colonne : ");
        colonne = (scanner.nextInt()) - 1;
        while (colonne < 0 || colonne > 7) {
           System.out.println("Coordonnées impossible");
           System.out.println("Entrez le numéro de la colonne : ");
           colonne = (scanner.nextInt()) - 1;
       }

        //appel des méthodes en fonction de la pièce jouée
        if (tab[ligne][colonne] == 6){
            pionN(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 12){
            pionB(tab, ligne, colonne);
        }
        else if (tab[ligne][colonne] == 7 || tab[ligne][colonne] == 1) {
            tour(tab, ligne, colonne);
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
        else if (tab[ligne][colonne] == 4 || tab[ligne][colonne] == 11){
            dame(tab, ligne, colonne);
        }
        else {
            System.out.println("Case vide, veuillez recommencez");
        }
        return tab;
    }

    public static void plateau(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = 0; //case vide
                tab[1][j] = 6; //Pion Jaune
                tab[6][j] = 12; //Pion Bleu
            }
        }
        int c = 21;
        for (int i = 0; i < tab.length; i++){
            tab[8][i] = c;
            tab[i][8] = c;
            c++;
        }

        //Tour Jaune
        tab[0][0] = 1;
        tab[0][7] = 1;

        //Tour Bleu
        tab[7][0] = 7;
        tab[7][7] = 7;

        //Cheval Jaune
        tab[0][1] = 2;
        tab[0][6] = 2;

        //Cheval Bleu
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
                else if (tab[i][j] == 21){
                    System.out.print("     1      ");
                }
                else if (tab[i][j] == 22){
                    System.out.print("     2      ");
                }
                else if (tab[i][j] == 23){
                    System.out.print("     3      ");
                }
                else if (tab[i][j] == 24){
                    System.out.print("     4      ");
                }
                else if (tab[i][j] == 25){
                    System.out.print("     5      ");
                }
                else if (tab[i][j] == 26){
                    System.out.print("     6      ");
                }
                else if (tab[i][j] == 27){
                    System.out.print("     7      ");
                }
                else if (tab[i][j] == 28){
                    System.out.print("     8      ");
                }
                else if (tab[i][j] == 0){
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
                    tab[ligne - 1][colonne + 1] = 12;
                }
            }
            if (colonne - 1 >= 0 && tab[ligne-1][colonne-1] <= 6 && tab[ligne-1][colonne-1] != 0) {
                System.out.println("prendre la pièce en digonale gauche ? (2 pour oui)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 2) {
                    tab[ligne][colonne] = 0;
                    tab[ligne - 1][colonne - 1] = 12;
                }
            }
        }
        else {
            if (ligne == 6 && tab[ligne - 1][colonne] == 0 && tab[ligne - 2][colonne] == 0) {
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

        if((colonne != 7 && tab[ligne + 1][colonne + 1] >= 7 && tab[ligne + 1][colonne + 1] < 20) || (colonne != 0 && tab[ligne + 1][colonne - 1] >= 7 && tab[ligne + 1][colonne - 1] < 20)) {
            System.out.println("Veux tu prendre une pièce ? (1 pour oui)");
            manger = Integer.parseInt(sc.nextLine());
        }

        if (manger == 1) {
            if (colonne + 1 <= 7 && tab[ligne + 1][colonne + 1] >= 7 && tab[ligne + 1][colonne + 1] < 20) {
                    System.out.println("Prendre la pièce en digonale droite ? (1 pour oui)");
                    prise = Integer.parseInt(sc.nextLine());
                if (prise == 1) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne + 1] = 6;
                }
            }
            if (colonne - 1 >= 0 && tab[ligne + 1][colonne - 1] >= 7 && tab[ligne + 1][colonne - 1] < 20) {
                System.out.println("Prendre la pièce en digonale gauche ? (2 pour oui)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 2) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne - 1] = 6;
                }
            }
        } else {
            if (ligne == 1 && tab[ligne + 1][colonne] == 0 && tab[ligne + 2][colonne] == 0) {
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
        int manger = 0; //il veut prendre une pièce en général
        int direction;
        int couleur=0; //couleur de la pièce

        if (tab[ligne][colonne] == 7) {
            couleur=7;
        }
        else if (tab[ligne][colonne] == 1) {
            couleur=1;
        }

        System.out.println("1 pour aller devant, 2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en arrière :");
        direction = Integer.parseInt(sc.nextLine());

        if (direction == 1 && tab[ligne - 1][colonne] == 0) {
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne - choix][colonne] = 7;
        } else if (direction == 3 && tab[ligne][colonne + 1] == 0) {
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne][colonne + choix] = 7;
        } else if (direction == 2 && tab[ligne][colonne - 1] == 0) {
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne][colonne - choix] = 7;
        } else if (direction == 4 && tab[ligne][colonne-1] == 0){
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne+choix][colonne] = 7;
        } else {
            System.out.println("Impossible d'avancer ");
        }
    }

    //Méthode pour le Fou
    public static void fou(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int manger = 0; //il veut prendre une pièce en général
        int direction;
        int couleur=0; //couleur de la pièce

        if (tab[ligne][colonne] == 9) {
            couleur=9;
        }
        else if (tab[ligne][colonne] == 3) {
            couleur=3;
        }

        System.out.println("1 diagonale haut gauche, 2 diagonale haut droite, 3 diagonale bas droite, 4 diagonale bas gauche :");
        direction = Integer.parseInt(sc.nextLine());

        if (direction == 1 && tab[ligne-1][colonne-1] == 0) {
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne-choix][colonne-choix] = couleur;
        } else if (direction == 3 && tab[ligne-1][colonne+1] == 0) {
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne+choix][colonne+choix] = couleur;
        } else if (direction == 2 && tab[ligne-1][colonne+1] == 0) {
            System.out.println("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());
            /*} while (choix != 1 && choix != 2);*/
            tab[ligne][colonne] = 0;
            tab[ligne-choix][colonne+choix] = couleur;
        } else if (direction == 4 && tab[ligne+1][colonne-1] == 0) {
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

    public static void dame(int[][] tab, int ligne, int colonne){
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale");
            choix = Integer.parseInt(sc.nextLine());
        } while (choix != 1 && choix != 2);

        if (choix == 1){
            tour(tab, ligne, colonne);
        }

        else{
            fou(tab, ligne, colonne);
        }


    }
}
