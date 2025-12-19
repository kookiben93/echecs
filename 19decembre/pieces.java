import java.util.Scanner;

public class pieces {
    //Méthode pour le Pion Bleu
    public static void pionB(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int prise = 0; //il veut prendre une pièce précisément

        if (methodes.caseValide(ligne -1, colonne+1) && tab[ligne - 1][colonne + 1] <= 6 && tab[ligne - 1][colonne + 1] != 0) {
            do {
                System.out.print("prendre la pièce en digonale droite ? (1 pour oui 0 pour non) : ");
                prise = Integer.parseInt(sc.nextLine());
            } while (prise != 0 && prise != 1);
            if (prise == 1) {
                tab[ligne][colonne] = 0;
                if (ligne - 1 == 0){
                    tab[ligne - 1][colonne + 1] = 11;
                } else {
                    tab[ligne - 1][colonne + 1] = 12;
                }
            }
        }
        if (methodes.caseValide(ligne -1, colonne -1) && tab[ligne - 1][colonne - 1] <= 6 && tab[ligne - 1][colonne - 1] != 0 && prise == 0) {
            do {
                System.out.print("prendre la pièce en digonale gauche ? (2 pour oui 0 pour non) : ");
                prise = Integer.parseInt(sc.nextLine());
            } while (prise != 0 && prise != 2);
            if (prise == 2) {
                tab[ligne][colonne] = 0;
                if (ligne - 1 == 0){
                    tab[ligne - 1][colonne - 1] = 11;
                } else {
                    tab[ligne - 1][colonne - 1] = 12;
                }
            }
        }
        if (ligne == 6 && tab[ligne - 2][colonne] == 0 && tab[ligne - 1][colonne] == 0 && prise == 0) {
            do {
                System.out.print("Tu veux avancer de 1 ou de 2 : ");
                choix = Integer.parseInt(sc.nextLine());
            } while (choix != 1 && choix != 2);
            if (choix == 1) {
                tab[ligne][colonne] = 0;
                tab[ligne - 1][colonne] = 12;
            } else {
                tab[ligne][colonne] = 0;
                tab[ligne - 2][colonne] = 12;
            }
        } else if (methodes.caseValide(ligne-1, colonne) && tab[ligne - 1][colonne] == 0 && prise == 0) {
            System.out.println("Le pion avance de 1");
            tab[ligne][colonne] = 0;
            if (ligne - 1 == 0){
                tab[ligne - 1][colonne] = 11;
            } else {
                tab[ligne - 1][colonne] = 12;
            }
        } else if (prise == 0){
            System.out.println("Le pion n'a pas de mouvement ");
        }
    }

    //Méthode pour le Pion Jaune
    public static void pionJ(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int prise = 0; //il veut prendre une pièce précisément

        if (methodes.caseValide(ligne + 1, colonne + 1) && tab[ligne + 1][colonne + 1] >= 7) {
            do {
                System.out.print("Prendre la pièce en digonale droite ? (1 pour oui 0 pour non) : ");
                prise = Integer.parseInt(sc.nextLine());
            } while (prise != 0 && prise != 1);
            if (prise == 1) {
                tab[ligne][colonne] = 0;
                if (ligne + 1 == 7){
                    tab[ligne + 1][colonne + 1] = 4;
                } else {
                    tab[ligne + 1][colonne + 1] = 6;
                }
            }
        }
        if (methodes.caseValide(ligne + 1, colonne - 1) && tab[ligne + 1][colonne - 1] >= 7 && prise == 0) {
            do {
                System.out.print("Prendre la pièce en digonale gauche ? (2 pour oui 0 pour non) : ");
                prise = Integer.parseInt(sc.nextLine());
            } while (prise != 0 && prise != 2);
            if (prise == 2) {
                tab[ligne][colonne] = 0;
                if (ligne + 1 == 7){
                    tab[ligne + 1][colonne - 1] = 4;
                } else {
                    tab[ligne + 1][colonne - 1] = 6;
                }
            }
        }
        if (ligne == 1 && tab[ligne + 2][colonne] == 0 && tab[ligne + 1][colonne] == 0 && prise == 0) {
            do {
                System.out.print("Tu veux avancer de 1 ou de 2 : ");
                choix = Integer.parseInt(sc.nextLine());
            } while (choix != 1 && choix != 2);
            if (choix == 1) {
                tab[ligne][colonne] = 0;
                tab[ligne + 1][colonne] = 6;
            } else {
                tab[ligne][colonne] = 0;
                tab[ligne + 2][colonne] = 6;
            }
        } else if (methodes.caseValide(ligne + 1, colonne) && tab[ligne + 1][colonne] == 0 && prise == 0) {
            System.out.println("Le pion avance de 1");
            tab[ligne][colonne] = 0;
            if (ligne + 1 == 7){
                tab[ligne + 1][colonne] = 4;
            } else {
                tab[ligne + 1][colonne] = 6;
            }
        } else if (prise == 0) {
            System.out.println("Le pion n'a pas de mouvement ");
        }
    }
    //Méthode pour la Tour
    public static void tour(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int couleur = tab[ligne][colonne]; //couleur de la pièce
        int direction = methodes.affichageDirections(tab, ligne, colonne, couleur);

        boolean haut = (methodes.caseValide(ligne-1, colonne) && (tab[ligne-1][colonne] == 0 || !(methodes.memeCouleur(tab, ligne-1, colonne, couleur)))); //haut
        boolean gauche = (methodes.caseValide(ligne, colonne-1) && (tab[ligne][colonne-1] == 0 || !(methodes.memeCouleur(tab, ligne, colonne-1, couleur)))); //gauche
        boolean droite = (methodes.caseValide(ligne, colonne+1) && (tab[ligne][colonne+1] == 0 || !(methodes.memeCouleur(tab, ligne, colonne+1, couleur)))); //droite
        boolean bas = (methodes.caseValide(ligne+1, colonne) && (tab[ligne+1][colonne] == 0 || !(methodes.memeCouleur(tab, ligne+1, colonne, couleur)))); //bas


        if ((haut && !gauche && !droite && !bas) || direction == 1) {           //aller en haut
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne-choix, colonne)) || methodes.empechement(tab, ligne, colonne, choix, 1) || methodes.memeCouleur(tab, ligne-choix, colonne, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne - choix][colonne] = couleur;

        } else if ((!haut && gauche && !droite && !bas) || direction == 2) {    //aller à gauche
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne, colonne-choix)) || methodes.empechement(tab, ligne, colonne, choix, 2) || methodes.memeCouleur(tab, ligne, colonne-choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne][colonne-choix] = couleur;

        } else if ((!haut && !gauche && droite && !bas) || direction == 3) {     //aller à droite
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne, colonne + choix)) || methodes.empechement(tab, ligne, colonne, choix, 3) || methodes.memeCouleur(tab, ligne, colonne + choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne][colonne + choix] = couleur;

        } else if ((!haut && !gauche && !droite && bas) || direction == 4) {    //aller en bas
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne + choix, colonne)) || methodes.empechement(tab, ligne, colonne, choix, 4) || methodes.memeCouleur(tab, ligne + choix, colonne, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne + choix][colonne] = couleur;

        } else {
            System.out.println("Impossible d'avancer ");
        }
    }

    //Méthode pour le Fou
    /*public static void fou(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int couleur = tab[ligne][colonne]; //couleur de la pièce
        int valeurDep = methodes.valeurDeplacement(tab, ligne, colonne, couleur);
        int direction = methodes.affichageDirections(tab, ligne, colonne, couleur);


        if (valeurDep == 8 || direction == 5) {           //aller en haut-gauche
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne - choix, colonne - choix)) || methodes.empechement(tab, ligne, colonne, choix, 5) || methodes.memeCouleur(tab, ligne - choix, colonne - choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne - choix][colonne - choix] = couleur;

        } else if (valeurDep == 16 || direction == 6) {    //aller en haut-droite
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne - choix, colonne - choix)) || methodes.empechement(tab, ligne, colonne, choix, 6) || methodes.memeCouleur(tab, ligne - choix, colonne - choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne - choix][colonne + choix] = couleur;

        } else if (valeurDep == 32 || direction == 7) {     //aller en bas-gauche
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne + choix, colonne + choix)) || methodes.empechement(tab, ligne, colonne, choix, 7) || methodes.memeCouleur(tab, ligne + choix, colonne + choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne + choix][colonne - choix] = couleur;

        } else if (valeurDep == 64 || direction == 8) {    //aller en bas-droite
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes.caseValide(ligne + choix, colonne + choix)) || methodes.empechement(tab, ligne, colonne, choix, 8) || methodes.memeCouleur(tab, ligne + choix, colonne + choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            tab[ligne][colonne] = 0;
            tab[ligne + choix][colonne + choix] = couleur;

        } else {
            System.out.println("Impossible d'avancer ");
        }
    }*/

    //Méthode pour le Roi
    public static void roi(int[][] tab, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix1 = 0;
        int choix2 = 0;
        int couleur;

        couleur = tab[ligne][colonne];

        while (choix1 != 1 && choix1 != 2) {
            System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale : ");
            choix1 = Integer.parseInt(sc.nextLine());
        }

        boolean choix21 = methodes.caseValide(ligne + 1, colonne) && !methodes.memeCouleur(tab, ligne + 1, colonne, couleur);
        boolean choix22 = methodes.caseValide(ligne - 1, colonne) && !methodes.memeCouleur(tab, ligne - 1, colonne, couleur);
        boolean choix23 = methodes.caseValide(ligne, colonne - 1) && !methodes.memeCouleur(tab, ligne, colonne - 1, couleur);
        boolean choix24 = methodes.caseValide(ligne, colonne + 1) && !methodes.memeCouleur(tab, ligne, colonne + 1, couleur);

        boolean choix31 = methodes.caseValide(ligne + 1, colonne + 1) && !methodes.memeCouleur(tab, ligne + 1, colonne + 1, couleur);
        boolean choix32 = methodes.caseValide(ligne + 1, colonne - 1) && !methodes.memeCouleur(tab, ligne + 1, colonne - 1, couleur);
        boolean choix33 = methodes.caseValide(ligne - 1, colonne + 1) && !methodes.memeCouleur(tab, ligne - 1, colonne + 1, couleur);
        boolean choix34 = methodes.caseValide(ligne - 1, colonne - 1) && !methodes.memeCouleur(tab, ligne - 1, colonne - 1, couleur);

        if (choix1 == 1 && (choix21 || choix22 || choix23 || choix24 )) {
            while (choix2 != 1 && choix2 != 2 && choix2 != 3 && choix2 != 4) {
                System.out.print("1 pour aller en bas, 2 pour aller en haut, 3 pour aller à gauche, 4 pour aller à droite : ");
                choix2 = Integer.parseInt(sc.nextLine());
            }
            if (choix2 == 1 && choix21) {
                tab[ligne][colonne] = 0;
                tab[ligne + 1][colonne] = couleur;
            } else if (choix2 == 2 && choix22) {
                tab[ligne][colonne] = 0;
                tab[ligne - 1][colonne] = couleur;
            } else if (choix2 == 3 && choix23) {
                tab[ligne][colonne] = 0;
                tab[ligne][colonne - 1] = couleur;
            } else if (choix2 == 4 && choix24) {
                tab[ligne][colonne] = 0;
                tab[ligne][colonne + 1] = couleur;
            } else {
                System.out.println("Impossible d'avancer ");
            }
        }

        else if (choix1 == 2 && (choix31 || choix32 || choix33 || choix34 )) {
            while (choix2 != 1 && choix2 != 2 && choix2 != 3 && choix2 != 4) {
                System.out.print("1 diagonale bas gauche, 2 diagonale haut gauche, 3 diagonale bas droite, 4 diagonale haut droite : ");
                choix2 = Integer.parseInt(sc.nextLine());
            }
            if (choix2 == 3 && choix31) {
                tab[ligne][colonne] = 0;
                tab[ligne + 1][colonne + 1] = couleur;
            } else if (choix2 == 1 && choix32) {
                tab[ligne][colonne] = 0;
                tab[ligne + 1][colonne - 1] = couleur;
            } else if (choix2 == 4 && choix33) {
                tab[ligne][colonne] = 0;
                tab[ligne - 1][colonne + 1] = couleur;
            } else if (choix2 == 2 && choix34) {
                tab[ligne][colonne] = 0;
                tab[ligne - 1][colonne - 1] = couleur;
            } else {
                System.out.println("Impossible d'avancer ");
            }

        } else {
            System.out.println("Impossible d'avancer ");
        }
    }

    //Méthode pour la Cavalier
    public static void cavalier(int[][] tab, int ligne, int colonne){
        Scanner sc = new Scanner(System.in);
        int NvColonne;
        int NvLigne;
        int couleur = tab[ligne][colonne]; //couleur de la pièce

        System.out.println("Où veux-tu aller ?");
        System.out.print("Entrez le numéro de la ligne : ");
        NvLigne = (sc.nextInt())-1;
        while ((NvLigne<0 || NvLigne>7) || !methodes.ligneCavalier(tab, ligne, NvLigne)) {
            System.out.println("Le cavalier ne peut pas aller là");
            System.out.print("Entrez un autre numéro de ligne : ");
            NvLigne = (sc.nextInt())-1;
        }
        System.out.print("Entrez le numéro de la colonne : ");
        NvColonne = (sc.nextInt())-1;
        while ((NvColonne<0 || NvColonne>7) || !methodes.mouvementCavalier(tab, ligne, colonne, NvLigne, NvColonne) || methodes.memeCouleur(tab, NvLigne, NvColonne, couleur)) {
            System.out.println("Le cavalier ne peut pas aller là");
            System.out.print("Entrez un autre numéro de colonne : ");
            NvColonne = (sc.nextInt())-1;
        }
        tab[ligne][colonne] = 0;
        tab[NvLigne][NvColonne] = couleur;
    }

        public static void dame(int[][] tab, int ligne, int colonne) {
            Scanner sc = new Scanner(System.in);
            int choix;

            do {
                System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale : ");
                choix = Integer.parseInt(sc.nextLine());
            } while (choix != 1 && choix != 2);

            if (choix == 1) {
                tour(tab, ligne, colonne);
            } /*else {
                fou(tab, ligne, colonne);
            }*/
        }
    }
