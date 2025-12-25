import java.util.Scanner;

public class pieces {
    public static void Main(String args){

    }
    //Méthode pour le Pion Bleu
    public static void pionB(int[][] plateau, int ligne, int colonne) {
        Scanner sc = new Scanner(System.in);
        int avancer; //il veut avancer de combien

        //si la case en diagonale droite est dans le plateau et est une pièce bleue
        boolean diagoDroite = methodes.caseValide(ligne - 1, colonne + 1) && !methodes.memeCouleurEtVide(plateau, ligne - 1, colonne + 1, plateau[ligne][colonne]);
        //si la case en diagonale gauche est dans le plateau et est une pièce bleue
        boolean diagoGauche = methodes.caseValide(ligne - 1, colonne - 1) && !methodes.memeCouleurEtVide(plateau, ligne - 1, colonne - 1, plateau[ligne][colonne]);
        //si la première case devant le pion est vide
        boolean avanceUn = plateau[ligne - 1][colonne] == 0;
        //si le pion n'a pas bougé la deuxième case devant lui est vide
        boolean avanceDeux = ligne == 6 && plateau[ligne - 2][colonne] == 0;

        int choix = methodes.affichageChoixPion(diagoDroite, diagoGauche, avanceUn);    //il veut prendre une pièce ou avancer

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
        boolean diagoDroite = methodes.caseValide(ligne + 1, colonne + 1) && !methodes.memeCouleurEtVide(plateau, ligne+1, colonne+1, plateau[ligne][colonne]);
        //si la case en diagonale gauche est dans le plateau et est une pièce bleue
        boolean diagoGauche = methodes.caseValide(ligne + 1, colonne - 1) && !methodes.memeCouleurEtVide(plateau, ligne+1, colonne-1, plateau[ligne][colonne]);
        //si la première case devant le pion est vide
        boolean avanceUn = plateau[ligne + 1][colonne] == 0;
        //si le pion n'a pas bougé la deuxième case devant lui est vide
        boolean avanceDeux = ligne == 1 && plateau[ligne + 2][colonne] == 0;

        int choix = methodes.affichageChoixPion(diagoDroite, diagoGauche, avanceUn);    //il veut prendre une pièce ou avancer

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
    public static void tour(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce
        int valeurDirection = 0;

        if(mode==1){
            boolean haut = methodes.haut(plateau, ligne, colonne, joueur);
            boolean gauche = methodes.gauche(plateau, ligne, colonne, joueur);
            boolean droite = methodes.droite(plateau, ligne, colonne, joueur);
            boolean bas = methodes.bas(plateau, ligne, colonne, joueur);

            int direction = methodes.affichageDirections(haut, gauche, droite, bas, joueur);

            int hautBas=0;    //valeur pour le mouvement Haut ou Bas selon la demande
            int gaucheDroite=0;   //valeur pour le mouvement gauche ou droite selon la demande

            if(haut && !gauche && !droite && !bas || direction==1){          //hautBas prend la valeur de -1 pour monter
                hautBas = -1;
                valeurDirection = 1;
            }else if(!haut && gauche && !droite && !bas || direction==2){     //gaucheDroite prend la valeur de -1 pour aller à gauche
                gaucheDroite = -1;
                valeurDirection = 2;
            }else if(!haut && !gauche && droite && !bas || direction==3){     //gaucheDroite prend la valeur de 1 pour aller à droite
                gaucheDroite = 1;
                valeurDirection = 3;
                System.out.println("La tour va à droite");
            }else if(!haut && !gauche && !droite && bas || direction==4){     //hautBas prend la valeur de 1 pour descendre
                hautBas = 1;
                valeurDirection = 4;
                System.out.println("La tour descend");
            }
            methodes.Methode1(plateau, ligne, colonne, couleur, hautBas, gaucheDroite, valeurDirection);
        } else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur);
        }
    }

    //Méthode pour le Fou
    public static void fou(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
        int couleur = plateau[ligne][colonne]; //couleur de la pièce
        int valeurDirection=0;

        if(mode==1){
            boolean hautGauche = methodes.hautGauche(plateau, ligne, colonne, couleur);
            boolean hautDroite = methodes.hautDroite(plateau, ligne, colonne, couleur);
            boolean basGauche = methodes.basGauche(plateau, ligne, colonne, couleur);
            boolean basDroite = methodes.basDroite(plateau, ligne, colonne, couleur);

            int direction = methodes.affichageDirectionsFou(hautGauche, hautDroite, basGauche, basDroite, joueur);

            int hautBas=0;    //valeur pour le mouvement Haut ou Bas selon la demande
            int gaucheDroite=0;   //valeur pour le mouvement gauche ou droite selon la demande

            if(hautGauche && !hautDroite && !basGauche && !basDroite || direction==1){
                hautBas = -1;
                gaucheDroite = -1;
                valeurDirection = 1;
            }else if(!hautGauche && hautDroite && !basGauche && !basDroite || direction==2){
                hautBas = -1;
                gaucheDroite = 1;
                valeurDirection = 2;
            }else if(!hautGauche && !hautDroite && basGauche && !basDroite || direction==3){
                hautBas = 1;
                gaucheDroite = -1;
                valeurDirection = 3;
            }else if(!hautGauche && !hautDroite && !basGauche && basDroite || direction==4){
                hautBas = 1;
                gaucheDroite = 1;
                valeurDirection = 4;
            }
            methodes.Methode1(plateau, ligne, colonne, couleur, hautBas, gaucheDroite, valeurDirection);
        } else{
            methodes.destinationPiece(plateau, ligne, colonne, couleur);
        }
    }

    //Méthode pour le Roi
    public static void roi(int[][] plateau, int ligne, int colonne, int mvtRoi, int mvtTour, char joueur) {
        Scanner sc = new Scanner(System.in);
        int couleur = plateau[ligne][colonne]; //couleur de la pièce
        int choix = 0;
        int oui=0;
        boolean mouvementAzero = methodes.nbMouvementsTourRoi(mvtRoi, mvtTour);
        boolean petit = methodes.PetitRoque(plateau, ligne, colonne);
        boolean grand = methodes.GrandRoque(plateau, ligne, colonne);

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
            methodes.roque(plateau, couleur, ligne, colonne);
        }
        else{
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

            int hautBas = 0;        //valeur pour le mouvement Haut ou Bas selon la demande
            int gaucheDroite = 0;   //valeur pour le mouvement gauche ou droite selon la demande

            if (choix == 1) {
                int direction1 = methodes.affichageDirections(haut, gauche, droite, bas, joueur);

                if (haut && !gauche && !droite && !bas || direction1 == 1) {          //hautBas prend la valeur de -1 pour monter
                    System.out.println("Le roi avance en haut");
                    hautBas = -1;
                } else if (!haut && gauche && !droite && !bas || direction1 == 2) {     //gaucheDroite prend la valeur de -1 pour aller à gauche
                    System.out.println("Le roi avance à gauche");
                    gaucheDroite = -1;
                } else if (!haut && !gauche && droite && !bas || direction1 == 3) {     //gaucheDroite prend la valeur de 1 pour aller à droite
                    System.out.println("Le roi avance à droite");
                    gaucheDroite = 1;
                } else if (!haut && !gauche && !droite && bas || direction1 == 4){     //hautBas prend la valeur de 1 pour descendre
                    System.out.println("Le roi avance en bas");
                    hautBas = 1;
                }
            }
            else if (choix == 2){
                int direction2 = methodes.affichageDirectionsFou(hautGauche, hautDroite, basGauche, basDroite, joueur);

                if ((hautGauche && !hautDroite && !basGauche && !basDroite) || direction2 == 1) {
                    System.out.println("Le roi avance en haut à gauche");
                    hautBas = -1;
                    gaucheDroite = -1;
                } else if (!hautGauche && hautDroite && !basGauche && !basDroite || direction2 == 2) {
                    System.out.println("Le roi avance en haut à droite");
                    hautBas = -1;
                    gaucheDroite = 1;
                } else if (!hautGauche && !hautDroite && basGauche && !basDroite || direction2 == 3) {
                    System.out.println("Le roi avance en bas à gauche");
                    hautBas = 1;
                    gaucheDroite = -1;
                } else if (!hautGauche && !hautDroite && !basGauche && basDroite || direction2 == 4) {
                    System.out.println("Le roi avance en bas à droite");
                    hautBas = 1;
                    gaucheDroite = 1;
                }
            }
            int NvLigne = ligne + hautBas;            //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }
    }


    //Méthode pour la Cavalier
    public static void cavalier(int[][] plateau, int ligne, int colonne, int mode){
        Scanner sc = new Scanner(System.in);
        int NvColonne;
        int NvLigne;
        int couleur = plateau[ligne][colonne]; //couleur de la pièce

        System.out.println("Où veux-tu aller ?");
        System.out.print("Entrez le numéro de la ligne : ");
        NvLigne = (sc.nextInt())-1;
        while ((NvLigne<0 || NvLigne>7) || !methodes.ligneCavalier(plateau, ligne, NvLigne)) {
            System.out.println("Le cavalier ne peut pas aller là");
            System.out.print("Entrez un autre numéro de ligne : ");
            NvLigne = (sc.nextInt())-1;
        }
        System.out.print("Entrez le numéro de la colonne : ");
        NvColonne = (sc.nextInt())-1;
        while ((NvColonne<0 || NvColonne>7) || !methodes.mouvementCavalier(plateau, ligne, colonne, NvLigne, NvColonne) || methodes.memeCouleur(plateau, NvLigne, NvColonne, couleur)) {
            System.out.println("Le cavalier ne peut pas aller là");
            System.out.print("Entrez un autre numéro de colonne : ");
            NvColonne = (sc.nextInt())-1;
        }
        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = couleur;
    }

    public static void dame(int[][] plateau, int ligne, int colonne, int mode, char joueur) {
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
