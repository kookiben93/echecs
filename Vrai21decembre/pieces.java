import java.util.Scanner;

public class pieces {
    public static void Main(String args){
        
    }
    //Méthode pour le Pion Bleu
    public static void pionB(int[][] plateau, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int avancer; //il veut avancer de combien

        //si la case en diagonale droite est dans le plateau et est une pièce bleue
        boolean diagoDroite = methodes3.caseValide(ligne - 1, colonne + 1) && !methodes3.memeCouleurEtVide(plateau, ligne - 1, colonne + 1, plateau[ligne][colonne]);
        //si la case en diagonale gauche est dans le plateau et est une pièce bleue
        boolean diagoGauche = methodes3.caseValide(ligne - 1, colonne - 1) && !methodes3.memeCouleurEtVide(plateau, ligne - 1, colonne - 1, plateau[ligne][colonne]);
        //si la première case devant le pion est vide
        boolean avanceUn = plateau[ligne - 1][colonne] == 0;
        //si le pion n'a pas bougé la deuxième case devant lui est vide
        boolean avanceDeux = ligne == 6 && plateau[ligne - 2][colonne] == 0;

        int choix = methodes3.affichageChoixPion(diagoDroite, diagoGauche, avanceUn);    //il veut prendre une pièce ou avancer

        if ((diagoGauche && !diagoDroite && !avanceUn) || choix == 1) {
            System.out.println("Le pion prend la pièce en diagonale gauche");
            plateau[ligne][colonne] = 0;                //case précédente devient vide
            if (ligne - 1 == 0) {                        //si la prochaine case est la dernière du plateau
                plateau[ligne - 1][colonne - 1] = 11;    //le pion prend la pièce et devient une dame
            } else {
                plateau[ligne - 1][colonne - 1] = 12;    //sinon il prend la pièce mais reste un pion
            }
        } else if ((!diagoGauche && diagoDroite && !avanceUn) || choix == 2) {
            System.out.println("Le pion prend la pièce en diagonale droite");
            plateau[ligne][colonne] = 0;                //case précédente devient vide
            if (ligne - 1 == 0) {                        //si la prochaine case est la dernière du plateau
                plateau[ligne - 1][colonne + 1] = 11;    //le pion prend la pièce et devient une dame
            } else {
                plateau[ligne - 1][colonne + 1] = 12;    //sinon il prend la pièce mais reste un pion
            }
        } else if ((!diagoGauche && !diagoDroite && avanceUn) || choix == 3) {
            if (avanceDeux) {
                do {
                    System.out.print("Tu veux avancer de 1 ou de 2 : ");
                    avancer = Integer.parseInt(sc.nextLine());
                } while (avancer != 1 && avancer != 2);
                plateau[ligne][colonne] = 0;                    //case précédente devient vide
                if (avancer == 1) {                             //si le joueur choisit d'avancer de 1
                    System.out.println("Le pion avance de 1");
                    plateau[ligne - 1][colonne] = 12;            //le pion avance de 1
                } else {
                    System.out.println("Le pion avance de 2");
                    plateau[ligne - 2][colonne] = 12;            //sinon il avance de 2
                }
            } else {
                System.out.println("Le pion avance de 1");
                plateau[ligne][colonne] = 0;                    //case précédente devient vide
                if (ligne - 1 == 0) {                           //si la prochaine case est la dernière du plateau
                    plateau[ligne - 1][colonne] = 11;            //le pion avance de 1 et devient une dame
                } else {
                    plateau[ligne - 1][colonne] = 12;            //sinon le pion avance juste de 1
                }
            }
        } else {
            System.out.println("Le pion n'a pas de mouvement ");
        }
    }

    //Méthode pour le Pion Jaune
    public static void pionJ(int[][] plateau, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int avancer; //il veut avancer de combien

        //si la case en diagonale droite est dans le plateau et est une pièce bleue
        boolean diagoDroite = methodes3.caseValide(ligne + 1, colonne + 1) && !methodes3.memeCouleurEtVide(plateau, ligne+1, colonne+1, plateau[ligne][colonne]);
        //si la case en diagonale gauche est dans le plateau et est une pièce bleue
        boolean diagoGauche = methodes3.caseValide(ligne + 1, colonne - 1) && !methodes3.memeCouleurEtVide(plateau, ligne+1, colonne-1, plateau[ligne][colonne]);
        //si la première case devant le pion est vide
        boolean avanceUn = plateau[ligne + 1][colonne] == 0;
        //si le pion n'a pas bougé la deuxième case devant lui est vide
        boolean avanceDeux = ligne == 1 && plateau[ligne + 2][colonne] == 0;

        int choix = methodes3.affichageChoixPion(diagoDroite, diagoGauche, avanceUn);    //il veut prendre une pièce ou avancer

        if ((diagoGauche && !diagoDroite && !avanceUn) || choix == 1) {
            System.out.println("Le pion prend la pièce en diagonale gauche");
            plateau[ligne][colonne] = 0;                //case précédente devient vide
            if (ligne + 1 == 7){                        //si la prochaine case est la dernière du plateau
                plateau[ligne + 1][colonne - 1] = 4;    //le pion prend la pièce et devient une dame
            } else {
                plateau[ligne + 1][colonne - 1] = 6;    //sinon il prend la pièce mais reste un pion
            }
        }

        else if ((!diagoGauche && diagoDroite && !avanceUn) || choix == 2) {
            System.out.println("Le pion prend la pièce en diagonale droite");
            plateau[ligne][colonne] = 0;                //case précédente devient vide
            if (ligne + 1 == 7){                        //si la prochaine case est la dernière du plateau
                plateau[ligne + 1][colonne + 1] = 4;    //le pion prend la pièce et devient une dame
            } else {
                plateau[ligne + 1][colonne + 1] = 6;    //sinon il prend la pièce mais reste un pion
            }
        }

        else if ((!diagoGauche && !diagoDroite && avanceUn) || choix == 3) {
            if (avanceDeux) {
                do {
                    System.out.print("Tu veux avancer de 1 ou de 2 : ");
                    avancer = Integer.parseInt(sc.nextLine());
                } while (avancer != 1 && avancer != 2);
                plateau[ligne][colonne] = 0;                    //case précédente devient vide
                if (avancer == 1) {                             //si le joueur choisit d'avancer de 1
                    System.out.println("Le pion avance de 1");
                    plateau[ligne + 1][colonne] = 6;            //le pion avance de 1
                } else {
                    System.out.println("Le pion avance de 2");
                    plateau[ligne + 2][colonne] = 6;            //sinon il avance de 2
                }
            }
            else {
                System.out.println("Le pion avance de 1");
                plateau[ligne][colonne] = 0;                    //case précédente devient vide
                if (ligne + 1 == 7) {                           //si la prochaine case est la dernière du plateau
                    plateau[ligne + 1][colonne] = 4;            //le pion avance de 1 et devient une dame
                } else {
                    plateau[ligne + 1][colonne] = 6;            //sinon le pion avance juste de 1
                }
            }
        }
        else {
                System.out.println("Le pion n'a pas de mouvement ");
        }
    }

    //Méthode pour la Tour
    public static void tour(int[][] plateau, int ligne, int colonne, int mode) {
        Scanner sc = new Scanner(System.in);
        
        int NvColonne;  //Colonne après les choix de l'utilisateur
        int NvLigne;    //Ligne après les choix de l'utilisateur
        int choix;      //de combien veut avancer l'utilisateur
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        if(mode==1){
            boolean haut = (methodes3.caseValide(ligne-1, colonne) && (plateau[ligne-1][colonne] == 0 || !(methodes3.memeCouleur(plateau, ligne-1, colonne, couleur)))); //haut
            boolean gauche = (methodes3.caseValide(ligne, colonne-1) && (plateau[ligne][colonne-1] == 0 || !(methodes3.memeCouleur(plateau, ligne, colonne-1, couleur)))); //gauche
            boolean droite = (methodes3.caseValide(ligne, colonne+1) && (plateau[ligne][colonne+1] == 0 || !(methodes3.memeCouleur(plateau, ligne, colonne+1, couleur)))); //droite
            boolean bas = (methodes3.caseValide(ligne+1, colonne) && (plateau[ligne+1][colonne] == 0 || !(methodes3.memeCouleur(plateau, ligne+1, colonne, couleur)))); //bas

            int direction = methodes3.affichageDirections(haut, gauche, droite, bas);

            int hautBas = 0;    //valeur pour le mouvement Haut ou Bas selon la demande
            int gaucheDroite = 0;   //valeur pour le mouvement gauche ou droite selon la demande

            switch(direction){
                case 1: hautBas = -1;   //hautBas prend la valeur de -1 si l'utilisateur veut aller en haut
                    break;
                case 2: gaucheDroite = -1;  //gaucheDroite prend la valeur de -1 si l'utilisateur veut aller en gauche
                    break;
                case 3: gaucheDroite = 1;   //gaucheDroite prend la valeur de 1 si l'utilisateur veut aller à droite
                    break;
                case 4: hautBas = 1;    //hautBas prend la valeur de 1 si l'utilisateur veut aller en bas
                    break;
                default:
                    break;
            }

            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());    //demande de combien l'utilisateur veut se déplacer sans prendre en compte la direction

            NvLigne = ligne+(hautBas*choix);        //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
            NvColonne = colonne+(gaucheDroite*choix);   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

            while (!(methodes3.caseValide(NvLigne, NvColonne)) || methodes3.empechement(plateau, ligne, colonne, choix, direction) || methodes3.memeCouleur(plateau, NvLigne, NvColonne, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());

                NvLigne = ligne+(hautBas*choix);
                NvColonne = colonne+(gaucheDroite*choix);
            }
            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }

        else if(mode==2){
            methodes3.destinationPiece(plateau, ligne, colonne, couleur);
        }
    }

    //Méthode pour le Fou
    /*public static void fou(int[][] plateau, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix; //il veut avancer de combien
        int couleur = plateau[ligne][colonne]; //couleur de la pièce
        int valeurDep = methodes33.valeurDeplacement(plateau, ligne, colonne, couleur);
        int direction = methodes33.affichageDirections(plateau, ligne, colonne, couleur);


        if (valeurDep == 8 || direction == 5) {           //aller en haut-gauche
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes33.caseValide(ligne - choix, colonne - choix)) || methodes33.empechement(plateau, ligne, colonne, choix, 5) || methodes33.memeCouleur(plateau, ligne - choix, colonne - choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            plateau[ligne][colonne] = 0;
            plateau[ligne - choix][colonne - choix] = couleur;

        } else if (valeurDep == 16 || direction == 6) {    //aller en haut-droite
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes33.caseValide(ligne - choix, colonne - choix)) || methodes33.empechement(plateau, ligne, colonne, choix, 6) || methodes33.memeCouleur(plateau, ligne - choix, colonne - choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            plateau[ligne][colonne] = 0;
            plateau[ligne - choix][colonne + choix] = couleur;

        } else if (valeurDep == 32 || direction == 7) {     //aller en bas-gauche
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes33.caseValide(ligne + choix, colonne + choix)) || methodes33.empechement(plateau, ligne, colonne, choix, 7) || methodes33.memeCouleur(plateau, ligne + choix, colonne + choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            plateau[ligne][colonne] = 0;
            plateau[ligne + choix][colonne - choix] = couleur;

        } else if (valeurDep == 64 || direction == 8) {    //aller en bas-droite
            System.out.print("Tu veux avancer de combien ? ");
            choix = Integer.parseInt(sc.nextLine());

            while (!(methodes33.caseValide(ligne + choix, colonne + choix)) || methodes33.empechement(plateau, ligne, colonne, choix, 8) || methodes33.memeCouleur(plateau, ligne + choix, colonne + choix, couleur)) {
                System.out.println("impossible d'avancer jusque là");
                System.out.print("de combien veux-tu avancer ? : ");
                choix = Integer.parseInt(sc.nextLine());
            }
            plateau[ligne][colonne] = 0;
            plateau[ligne + choix][colonne + choix] = couleur;

        } else {
            System.out.println("Impossible d'avancer ");
        }
    }*/

    //Méthode pour le Roi
    public static void roi(int[][] plateau, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int choix1 = 0;
        int choix2 = 0;
        int couleur;

        couleur = plateau[ligne][colonne];

        while (choix1 != 1 && choix1 != 2) {
            System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale : ");
            choix1 = Integer.parseInt(sc.nextLine());
        }

        boolean choix21 = methodes3.caseValide(ligne + 1, colonne) && !methodes3.memeCouleur(plateau, ligne + 1, colonne, couleur);
        boolean choix22 = methodes3.caseValide(ligne - 1, colonne) && !methodes3.memeCouleur(plateau, ligne - 1, colonne, couleur);
        boolean choix23 = methodes3.caseValide(ligne, colonne - 1) && !methodes3.memeCouleur(plateau, ligne, colonne - 1, couleur);
        boolean choix24 = methodes3.caseValide(ligne, colonne + 1) && !methodes3.memeCouleur(plateau, ligne, colonne + 1, couleur);

        boolean choix31 = methodes3.caseValide(ligne + 1, colonne + 1) && !methodes3.memeCouleur(plateau, ligne + 1, colonne + 1, couleur);
        boolean choix32 = methodes3.caseValide(ligne + 1, colonne - 1) && !methodes3.memeCouleur(plateau, ligne + 1, colonne - 1, couleur);
        boolean choix33 = methodes3.caseValide(ligne - 1, colonne + 1) && !methodes3.memeCouleur(plateau, ligne - 1, colonne + 1, couleur);
        boolean choix34 = methodes3.caseValide(ligne - 1, colonne - 1) && !methodes3.memeCouleur(plateau, ligne - 1, colonne - 1, couleur);

        if (choix1 == 1 && (choix21 || choix22 || choix23 || choix24 )) {
            while (choix2 != 1 && choix2 != 2 && choix2 != 3 && choix2 != 4) {
                System.out.print("1 pour aller en bas, 2 pour aller en haut, 3 pour aller à gauche, 4 pour aller à droite : ");
                choix2 = Integer.parseInt(sc.nextLine());
            }
            if (choix2 == 1 && choix21) {
                plateau[ligne][colonne] = 0;
                plateau[ligne + 1][colonne] = couleur;
            } else if (choix2 == 2 && choix22) {
                plateau[ligne][colonne] = 0;
                plateau[ligne - 1][colonne] = couleur;
            } else if (choix2 == 3 && choix23) {
                plateau[ligne][colonne] = 0;
                plateau[ligne][colonne - 1] = couleur;
            } else if (choix2 == 4 && choix24) {
                plateau[ligne][colonne] = 0;
                plateau[ligne][colonne + 1] = couleur;
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
                plateau[ligne][colonne] = 0;
                plateau[ligne + 1][colonne + 1] = couleur;
            } else if (choix2 == 1 && choix32) {
                plateau[ligne][colonne] = 0;
                plateau[ligne + 1][colonne - 1] = couleur;
            } else if (choix2 == 4 && choix33) {
                plateau[ligne][colonne] = 0;
                plateau[ligne - 1][colonne + 1] = couleur;
            } else if (choix2 == 2 && choix34) {
                plateau[ligne][colonne] = 0;
                plateau[ligne - 1][colonne - 1] = couleur;
            } else {
                System.out.println("Impossible d'avancer ");
            }

        } else {
            System.out.println("Impossible d'avancer ");
        }
    }

    //Méthode pour la Cavalier
    public static void cavalier(int[][] plateau, int ligne, int colonne){
        Scanner sc = new Scanner(System.in);
        int NvColonne;
        int NvLigne;
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        System.out.println("Où veux-tu aller ?");
        System.out.print("Entrez le numéro de la ligne : ");
        NvLigne = (sc.nextInt())-1;
        while ((NvLigne<0 || NvLigne>7) || !methodes3.ligneCavalier(plateau, ligne, NvLigne)) {
            System.out.println("Le cavalier ne peut pas aller là");
            System.out.print("Entrez un autre numéro de ligne : ");
            NvLigne = (sc.nextInt())-1;
        }
        System.out.print("Entrez le numéro de la colonne : ");
        NvColonne = (sc.nextInt())-1;
        while ((NvColonne<0 || NvColonne>7) || !methodes3.mouvementCavalier(plateau, ligne, colonne, NvLigne, NvColonne) || methodes3.memeCouleur(plateau, NvLigne, NvColonne, couleur)) {
            System.out.println("Le cavalier ne peut pas aller là");
            System.out.print("Entrez un autre numéro de colonne : ");
            NvColonne = (sc.nextInt())-1;
        }
        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = couleur;
    }

        public static void dame(int[][] plateau, int ligne, int colonne, int mode) {
            Scanner sc = new Scanner(System.in);
            int choix;

            do {
                System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale : ");
                choix = Integer.parseInt(sc.nextLine());
            } while (choix != 1 && choix != 2);

            if (choix == 1) {
                tour(plateau, ligne, colonne, mode);
            } /*else {
                fou(plateau, ligne, colonne);
            }*/
        }
    }
}
