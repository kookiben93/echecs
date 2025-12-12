import java.util.Scanner;

public class methodes {

    public static void main(String[] args) {

    }

    public static int[][] coordonnees(int[][] tab){
        Scanner scanner = new Scanner(System.in);
        int colonne;
        int ligne;

        System.out.println("Quelle pièce voulez vous jouer ? ");
        System.out.print("ligne : ");
        ligne = (scanner.nextInt()) - 1;
        System.out.print("colonne : ");
        colonne = (scanner.nextInt()) - 1;

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
        return tab;
    }

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
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] == 1){
                    System.out.print("[ Tour_N ]");
                }
                else if (tab[i][j] == 2){
                    System.out.print("[Cheval_N]");
                }
                else if (tab[i][j] == 3){
                    System.out.print("[ Fou_N  ]");
                }
                else if (tab[i][j] == 4){
                    System.out.print("[ Roi_N  ]");
                }
                else if (tab[i][j] == 5){
                    System.out.print("[ Dame_N ]");
                }
                else if (tab[i][j] == 6){
                    System.out.print("[ Pion_N ]");
                }
                else if(tab[i][j] == 7){
                    System.out.print("[ Tour_B ]");
                }
                else if (tab[i][j] == 8){
                    System.out.print("[Cheval_B]");
                }
                else if (tab[i][j] == 9){
                    System.out.print("[ Fou_B  ]");
                }
                else if (tab[i][j] == 10){
                    System.out.print("[ Roi_B  ]");
                }
                else if (tab[i][j] == 11){
                    System.out.print("[ Dame_B ]");
                }
                else if (tab[i][j] == 12){
                    System.out.print("[ Pion_B ]");
                }
                else{
                    System.out.print("[        ]");
                }
            }
            System.out.println();
        }

        System.out.println();
    }

    //Méthode pour le pion Blanc
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

    //Méthode pour le Pion Noir
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
                System.out.println("prendre la pièce en digonale droite ? (1 pour oui 0 pour non)");
                prise = Integer.parseInt(sc.nextLine());
                if (prise == 1) {
                    tab[ligne][colonne] = 0;
                    tab[ligne + 1][colonne + 1] = 6;
                }
            }
            if (colonne - 1 >= 0 && tab[ligne + 1][colonne - 1] >= 7) {
                System.out.println("prendre la pièce en digonale gauche ? (2 pour oui)");
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
        int manger = 0; //il veut prendre une pièce en général
        int direction;
        int couleur=0; //couleur de la pièce

        if (tab[ligne][colonne] == 7) {
            couleur=7;
        }
        else if (tab[ligne][colonne] == 1) {
            couleur=1;
        }

        System.out.print("1 pour aller devant, 2 pour aller à gauche, 3 pour aller à droite ");
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

        System.out.print("1 diagonale haut gauche, 2 diagonale haut droite, 3 diagonale bas droite, 4 diagonale bas gauche ");
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

    //Méthode pour le Roi Blanc
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
            System.out.println("Tu veux aller en ligne droite (tape 1) ou allez en diagonale (tape 2) ");
            choix1 = Integer.parseInt(sc.nextLine());
        }

        if (choix1 == 1 && ((ligne != 7 && tab[ligne+1][colonne] <= 6 ) || (ligne != 0 && tab[ligne-1][colonne] <= 6) || (colonne != 7 && tab[ligne][colonne+1] <= 6) || (colonne != 0 && tab[ligne][colonne-1] <= 6))){
            System.out.println("Tu veux aller en bas (tape 1), en haut (tape 2), a gauche (tape 3) ou a droite (tape 4) ");
            choix2 = Integer.parseInt(sc.nextLine());
            if (choix2 == 1){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne] = 10;
            }
            else if (choix2 == 2){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne] = 10;
            }
            else if (choix2 == 3){
                tab[ligne][colonne] = 0;
                tab[ligne][colonne-1] = 10;
            }
            else{
                tab[ligne][colonne] = 0;
                tab[ligne][colonne+1] = 10;
            }
        }
        else if (choix1 == 2 && ((ligne != 7 && colonne != 7 && tab[ligne+1][colonne+1] <= 6) || (ligne != 7 && colonne != 0 && tab[ligne+1][colonne-1] <= 6) || (ligne != 0 && colonne != 7 && tab[ligne-1][colonne+1] <= 6) || (ligne != 0 && colonne != 0 && tab[ligne-1][colonne-1] <= 6))){
            System.out.println("Tu veux aller en bas a gauche(tape 1), en haut a gauche (tape 2), en bas a droite (tape 3) ou en haut a droite (tape 4) ");
            choix2 = Integer.parseInt(sc.nextLine());
            if (choix2 == 1){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne-1] = 10;
            }
            else if (choix2 == 2){
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne-1] = 10;
            }
            else if (choix2 == 3){
                tab[ligne][colonne] = 0;
                tab[ligne+1][colonne+1] = 10;
            }
            else{
                tab[ligne][colonne] = 0;
                tab[ligne-1][colonne+1] = 10;
            }

        }
        else {
            System.out.println("Impossible d'avancer ");
        }

    }
}
