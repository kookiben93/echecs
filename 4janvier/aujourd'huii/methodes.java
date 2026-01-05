import java.util.Scanner;

public class methodes {
    static int mvtTourA1 = 0;       //Mouvements de la Tour Bleue Loin initialisés à 0
    static int mvtTourA8 = 0;       //Mouvements de la Tour Jaune Loin initialisés à 0
    static int mvtTourH1 = 0;       //Mouvements de la Tour Bleue Proche initialisés à 0
    static int mvtTourH8 = 0;       //Mouvements de la Tour Jaune Proche initialisés à 0
    static int mvtRoiJ = 0;         //Mouvements du Roi Jaune initialisés à 0
    static int mvtRoiB = 0;         //Mouvements du Roi Bleu initialisés à 0

    static boolean pep=false;       //Initialisation de la possibilité de prise en passant à faux
    static int ligneAvant;          //Variable prenant comme valeur la ligne du Pion précédent ayant avancé de 2 cases d'un coup
    static int colonneAvant;        //Variable prenant comme valeur la colonne du Pion précédent ayant avancé de 2 cases d'un coup

    public static int debut() {
        int choix;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Jouer au jeu d'échecs");
        System.out.println("2. Connaître les régles");
        System.out.println("3. Quitter le jeu");
        String choixS = scanner.nextLine();

        choix = methodes.conversionEnInt(choixS);

        return choix;
    }

    //Fonction demandant la valeur de la ligne choisie en String
    //et la retourne en int
    public static int coordonneeLigne() {
        Scanner scanner = new Scanner(System.in);
        int ligne;

        System.out.print("ligne : ");
        String li = scanner.nextLine();     //enregistre la valeur de la ligne en String

        if(li.equals("abandon")){       //si la valeur de li est "abandon"
            ligne = 10;                 //la ligne prend 10 comme valeur
        }
        else {
            ligne = conversionLigneEnInt(li);       //appel de méthode pour transformer la valeur String en int

            while (ligne < 0 || ligne > 7) {        //on force de la saisie tant que la valeur est supérieure aux indices possibles du tableau
                System.out.println("❌ Coordonnées impossible");
                System.out.print("ligne : ");
                li = scanner.nextLine();
                ligne = conversionLigneEnInt(li);
            }
        }
        return ligne;
    }

    //Fonction demandant la valeur de la colonne choisie en String
    //et la retourne en int
    public static int coordonneeColonne() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("colonne : ");
        String col = scanner.nextLine();        //enregistre la valeur de la colonne en String

        int colonne = conversionColEnInt(col);      //appel de méthode pour transformer la valeur String en int

        while (colonne < 0 || colonne > 7 && colonne != 10) {       //on force de la saisie tant que la valeur est supérieure aux indices possibles du tableau
            System.out.println("❌ Coordonnées impossible");
            System.out.print("colonne : ");
            col = scanner.nextLine();
            colonne = conversionColEnInt(col);
        }
        return colonne;
    }

    public static void coordonnees(int[][] plateau, char joueur, int mode) {
        int colonne=10;
        int ligne;

        do{
            System.out.println("Quelle pièce voulez vous jouer ? ");
            ligne = coordonneeLigne();

            if(ligne == 10)
                Main.abandon(plateau, joueur, mode);
            else
                colonne = coordonneeColonne();
        } while (ligne!=10 && colonne==10);

        if(ligne!=10) {
            appelPiece(plateau, ligne, colonne, joueur, mode);
        }
    }

    public static boolean mouvementPionRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne, int piece) {
        int sens;
        int ligneDepart;

        if (piece == 12) {
            sens = -1;
            ligneDepart = 6;
        } else {
            sens = 1;
            ligneDepart = 1;
        }

        if (!caseValide(nvLigne, nvColonne)) {
            return false;
        }

        int distL = (nvLigne - ligne) * sens;
        int distC = Math.abs(nvColonne - colonne);

        // Avancer de 1 case tout droit
        if (distC == 0 && distL == 1 && plateau[nvLigne][nvColonne] == 0) {
            return true;
        }

        // Avancer de 2 cases
        else if (distC == 0 && distL == 2 && ligne == ligneDepart && plateau[ligne + sens][colonne] == 0 && plateau[nvLigne][nvColonne] == 0) {
            return true;
        }
        // Manger en diagonale
        else if (distC == 1 && distL == 1 && plateau[nvLigne][nvColonne] != 0 && !memeCouleur(plateau, nvLigne, nvColonne, piece)) {
            return true;
        }
        return false;
    }

    public static boolean pepRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne, int pion){
        boolean mouvementValide = false;
        if(pepPossibleGauche(plateau, ligne, colonne, pion)) {
            if (pion == 12 && nvLigne == ligne-1 && nvColonne == colonne-1) {
                mouvementValide = true;
            } else if (pion == 6 && nvLigne == ligne+1 && nvColonne == colonne-1){
                mouvementValide = true;
            }
        } else if(pepPossibleDroite(plateau, ligne, colonne, pion)){
            if (pion == 12 && nvLigne == ligne-1 && nvColonne == colonne+1) {
                mouvementValide = true;
            } else if (pion == 6 && nvLigne == ligne+1 && nvColonne == colonne+1){
                mouvementValide = true;
            }
        }
        return mouvementValide;
    }

    public static boolean mouvementRoiRobot(int ligne, int col, int nvL, int nvC) {
        int distLigne = Math.abs(nvL - ligne);
        int distColonne = Math.abs(nvC - col);

        return (distLigne <= 1 && distColonne <= 1 && (distLigne != 0 || distColonne != 0));
    }

    public static void robotChoix(int[][] plateau, char joueur) {
        int li;
        int col;
        int colonne;
        int ligne;
        boolean bouger = true;
        boolean mouvement = false;
        do {
            col = (int) (Math.random() * 8);
            li = (int) (Math.random() * 8);

            if (plateau[li][col] == 7 || plateau[li][col] == 1) {       //tours
                bouger = pieceAutour(plateau, li, col);
            } else if (plateau[li][col] == 6 || plateau[li][col] == 12){    //pions
                bouger = pieceAutour3(plateau, li, col);
            } else if (plateau[li][col] == 9 || plateau[li][col] == 3){     //fous
                bouger = pieceAutour2(plateau, li, col);
            } else if(plateau[li][col]==8||plateau[li][col]==2){        //cavaliers
                bouger = bougerCavalier(plateau, li, col);
            } else {
                bouger = pieceAutour(plateau, li, col) && pieceAutour2(plateau, li, col);
            }
        } while(!caseValide(li, col) || plateau[li][col]==0 || !couleurJoueur(plateau, li, col, joueur) || bouger);

        int couleur = plateau[li][col];
        do {
            colonne = (int) (Math.random() * 8);
            ligne = (int) (Math.random() * 8);

            if (plateau[li][col] == 7 || plateau[li][col] == 1)       //tours
                mouvement = mouvementTour(plateau, li, col, ligne, colonne);
            else if (plateau[li][col] == 6 || plateau[li][col] == 12) {    //pions
                mouvement = mouvementPionRobot(plateau, li, col, ligne, colonne, couleur) || pepRobot(plateau, li, col, ligne, colonne, couleur);
                priseEnPassantRobot(plateau, li, col, ligne, colonne, couleur);
            }
            else if (plateau[li][col] == 9 || plateau[li][col] == 3)    //fous
                mouvement = mouvementFou(plateau, li, col, ligne, colonne);
            else if (plateau[li][col] == 8 || plateau[li][col] == 2)        //cavaliers
                mouvement = mouvementCavalier(li, col, ligne, colonne);
            else if (plateau[li][col] == 4 || plateau[li][col] == 11)       //dames
                mouvement = mouvementFou(plateau, li, col, ligne, colonne) || mouvementTour(plateau, li, col, ligne, colonne);
            else        //rois
                mouvement = mouvementRoiRobot(li, col, ligne, colonne);

        } while(!caseValide(ligne, colonne) || memeCouleur(plateau, ligne, colonne, couleur) || !mouvement);

        AffichageSituation(plateau, joueur, couleur, li, col, ligne, colonne, -1, -1);
        plateau[li][col]=0;
        plateau[ligne][colonne]=couleur;
    }

    //Converti un String pris en paramètre en un int spécifique
    public static int conversionColEnInt(String colonne) {
        int coordonnee = 8;

        if (colonne.equals("a") || colonne.equals("A"))
            coordonnee = 0;
        else if (colonne.equals("b") || colonne.equals("B"))
            coordonnee = 1;
        else if (colonne.equals("c") || colonne.equals("C"))
            coordonnee = 2;
        else if (colonne.equals("d") || colonne.equals("D"))
            coordonnee = 3;
        else if (colonne.equals("e") || colonne.equals("E"))
            coordonnee = 4;
        else if (colonne.equals("f") || colonne.equals("F"))
            coordonnee = 5;
        else if (colonne.equals("g") || colonne.equals("G"))
            coordonnee = 6;
        else if (colonne.equals("h") || colonne.equals("H"))
            coordonnee = 7;
        else if (colonne.equals("x") || colonne.equals("X"))
            coordonnee = 10;

        return coordonnee;
    }

    public static int conversionLigneEnInt(String ligne) {
        int coordonnee = 8;

        switch(ligne){
            case "1": coordonnee = 7;
                break;
            case "2": coordonnee = 6;
                break;
            case "3": coordonnee = 5;
                break;
            case "4": coordonnee = 4;
                break;
            case "5": coordonnee = 3;
                break;
            case "6": coordonnee = 2;
                break;
            case "7": coordonnee = 1;
                break;
            case "8": coordonnee = 0;
                break;
            case "10": coordonnee = 10;
        }
        return coordonnee;
    }

    public static int conversionEnInt(String ligne) {
        int coordonnee = 9;

        switch(ligne){
            case "1": coordonnee = 1;
                break;
            case "2": coordonnee = 2;
                break;
            case "3": coordonnee = 3;
                break;
            case "4": coordonnee = 4;
                break;
            case "5": coordonnee = 5;
                break;
            case "6": coordonnee = 6;
                break;
            case "7": coordonnee = 7;
                break;
            case "8": coordonnee = 8;
                break;
        }
        return coordonnee;
    }

    //Reconverti un int pris en paramètre en sa valeur en String
    public static String conversionEnString(int colonne) {
        String coordonnee = "r";

        switch (colonne) {
            case 0: coordonnee = "a";
                break;
            case 1: coordonnee = "b";
                break;
            case 2: coordonnee = "c";
                break;
            case 3: coordonnee = "d";
                break;
            case 4: coordonnee = "e";
                break;
            case 5: coordonnee = "f";
                break;
            case 6: coordonnee = "g";
                break;
            case 7: coordonnee = "h";
                break;
        }
        return coordonnee;
    }

    public static int[][] copiePlateau(int[][] plateau) {
        int[][] copie = new int[8][8];
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                copie[ligne][colonne] = plateau[ligne][colonne];
            }
        }
        return copie;
    }

    public static void plateauPrecedent(int[][] plateau, int[][] plateauPrecedent) {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                plateau[ligne][colonne] = plateauPrecedent[ligne][colonne];
            }
        }
    }

    //appel des méthodes en fonction de la pièce jouée
    public static void appelPiece(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        int[][] sauvegarde = copiePlateau(plateau);

        if (couleurJoueur(plateau, ligne, colonne, joueur)) {        //Si le joueur joue des pièces à lui

            //appel des pions
            if (plateau[ligne][colonne] == 6 || plateau[ligne][colonne] == 12) {
                if (pieceAutour3(plateau, ligne, colonne)) {
                    System.out.println("❌ Impossible de bouger le pion");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.pion(plateau, ligne, colonne, joueur, mode);
                }

                //appel des tours
            } else if (plateau[ligne][colonne] == 7 || plateau[ligne][colonne] == 1) {
                if (pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("❌ Impossible de bouger la tour");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.tour(plateau, ligne, colonne, mode, joueur);
                    if (ligne == 0 && colonne == 0)
                        mvtTourA8++;
                    else if (ligne == 0 && colonne == 7)
                        mvtTourH8++;
                    else if (ligne == 7 && colonne == 0)
                        mvtTourA1++;
                    else if (ligne == 7 && colonne == 7)
                        mvtTourH1++;
                }

                //appel des fous
            } else if (plateau[ligne][colonne] == 9 || plateau[ligne][colonne] == 3) {
                if (pieceAutour2(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger le fou");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.fou(plateau, ligne, colonne, mode, joueur);
                }

                //appel des rois
            } else if (plateau[ligne][colonne] == 10 || plateau[ligne][colonne] == 5) {
                if (pieceAutour2(plateau, ligne, colonne) && pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("❌ Impossible de bouger le roi");
                } else {
                    if (plateau[ligne][colonne] == 5) {
                        pieces.roi(plateau, ligne, colonne, mvtTourA8, mvtTourH8, mvtRoiJ, joueur, mode);
                        mvtRoiJ++;
                    } else {
                        pieces.roi(plateau, ligne, colonne, mvtTourA1, mvtTourH1, mvtRoiB, joueur, mode);
                        mvtRoiB++;
                    }
                }

                //appel des cavaliers
            } else if (plateau[ligne][colonne] == 8 || plateau[ligne][colonne] == 2) {
                if (bougerCavalier(plateau, ligne, colonne)) {
                    System.out.println("❌ Impossible de bouger le cavalier");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.cavalier(plateau, ligne, colonne, mode, joueur);
                }

                //appel des dames
            } else if (plateau[ligne][colonne] == 4 || plateau[ligne][colonne] == 11) {
                if (pieceAutour2(plateau, ligne, colonne) && pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("❌ Impossible de bouger la dame");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.dame(plateau, ligne, colonne, joueur, mode);
                }
            } else {
                System.out.println("❌ Case vide, veuillez recommencer");
                coordonnees(plateau, joueur, mode);
            }
            if (estEnEchec(plateau, joueur)) {
                System.out.println("❌ Mouvement interdit : votre roi est en échec");

                plateauPrecedent(plateau, sauvegarde);

                coordonnees(plateau, joueur, mode);
            }
        } else {       //Si le joueur joue des pièces de l'adversaire
            System.out.print("Ce ne sont pas vos pièces ! ");
            coordonnees(plateau, joueur, mode);
        }
    }

    //Regarde si la Tour prise en paramètre a déjà bougé (pour le roque)
    public static boolean TourPasBougee(int compteurTour) {
        return compteurTour == 0;
    }

    //Regarde si la pièce du plateau jouée par le joueur lui appartient et
    // renvoi un booléen en fonction de si c'est le cas ou non
    public static boolean couleurJoueur(int[][] plateau, int ligne, int colonne, char joueur) {
        boolean valeur = true;

        if (joueur == 'B') {
            if (plateau[ligne][colonne] <= 6 && plateau[ligne][colonne] > 0) {
                valeur = false;
            }
        } else {
            if (plateau[ligne][colonne] > 6) {
                valeur = false;
            }
        }
        return valeur;
    }

    //booleen qui prend que les cases valide du plateau
    public static boolean caseValide(int ligne, int colonne) {
        return (ligne >= 0 && ligne < 8) && (colonne >= 0 && colonne < 8);
    }

    //booleen qui regarde si le mouvement du Cavalier est possible (mode 2)
    public static boolean bougerCavalier(int[][] plateau, int ligne, int colonne) {
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {2, 2, 1, 1, -2, -2, 1, 1};
        int[] colonnes = {-1, 1, 2, -2, -1, 1, -2, 2};

        for (int i = 0; i < 8; i++) {
            nouvelleL = ligne + lignes[i];
            nouvelleC = colonne + colonnes[i];

            if (caseValide(nouvelleL, nouvelleC)) {
                if (plateau[nouvelleL][nouvelleC] == 0) {
                    return false;
                }
                boolean pieceEnFace = plateau[nouvelleL][nouvelleC] > 6;

                if (pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean LGrandHautGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 2, colonne - 1) && !(memeCouleur(plateau, ligne - 2, colonne - 1, couleur));
    }

    public static boolean LGrandHautDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 2, colonne + 1) && !(memeCouleur(plateau, ligne - 2, colonne + 1, couleur));
    }

    public static boolean LPetitHautGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne - 2) && !(memeCouleur(plateau, ligne - 1, colonne - 2, couleur));
    }

    public static boolean LPetitHautDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne + 2) && !(memeCouleur(plateau, ligne - 1, colonne + 2, couleur));
    }

    public static boolean LGrandBasGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 2, colonne - 1) && !(memeCouleur(plateau, ligne + 2, colonne - 1, couleur));
    }

    public static boolean LGrandBasDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 2, colonne + 1) && !(memeCouleur(plateau, ligne + 2, colonne + 1, couleur));
    }

    public static boolean LPetitBasGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne - 2) && !(memeCouleur(plateau, ligne + 1, colonne - 2, couleur));
    }

    public static boolean LPetitBasDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne + 2) && !(memeCouleur(plateau, ligne + 1, colonne + 2, couleur));
    }

    public static int directionCavalier(int[][] plateau, int ligne, int colonne, int couleur) {
        Scanner sc = new Scanner(System.in);

        int possibilites = 0;
        boolean valide = false;
        int choix = 0;

        String colEt1 = conversionEnString(colonne + 1);
        String colEt2 = conversionEnString(colonne + 2);
        String colSans1 = conversionEnString(colonne - 1);
        String colSans2 = conversionEnString(colonne - 2);

        boolean LgrandHautGauche = LGrandHautGauche(plateau, ligne, colonne, couleur);
        boolean LgrandHautDroit = LGrandHautDroit(plateau, ligne, colonne, couleur);
        boolean LpetitHautGauche = LPetitHautGauche(plateau, ligne, colonne, couleur);
        boolean LpetitHautDroit = LPetitHautDroit(plateau, ligne, colonne, couleur);
        boolean LgrandBasGauche = LGrandBasGauche(plateau, ligne, colonne, couleur);
        boolean LgrandBasDroit = LGrandBasDroit(plateau, ligne, colonne, couleur);
        boolean LpetitBasGauche = LPetitBasGauche(plateau, ligne, colonne, couleur);
        boolean LpetitBasDroit = LPetitBasDroit(plateau, ligne, colonne, couleur);

        if (LgrandHautGauche) {
            possibilites++;
            choix = 1;
        }
        if (LgrandHautDroit) {
            possibilites++;
            choix = 2;
        }
        if (LpetitHautGauche) {
            possibilites++;
            choix = 3;
        }
        if (LpetitHautDroit) {
            possibilites++;
            choix = 4;
        }
        if (LgrandBasGauche) {
            possibilites++;
            choix = 5;
        }
        if (LgrandBasDroit) {
            possibilites++;
            choix = 6;
        }
        if (LpetitBasGauche) {
            possibilites++;
            choix = 7;
        }
        if (LpetitBasDroit) {
            possibilites++;
            choix = 8;
        }

        if (possibilites > 1) {
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if (LgrandHautGauche)
                    System.out.print("1 pour aller en (" + colSans1 + "," + (10-ligne)+ ") ");
                if (LgrandHautDroit)
                    System.out.print("2 pour aller en (" + colEt1 + "," + (10-ligne)+ ") ");
                if (LpetitHautGauche)
                    System.out.print("3 pour aller en (" + colSans2 + "," + (9-ligne) + ") ");
                if (LpetitHautDroit)
                    System.out.print("4 pour aller en (" + colEt2 + "," + (9-ligne) + ") ");
                if (LgrandBasGauche)
                    System.out.print("5 pour aller en (" + colSans1 + "," + (6-ligne) + ") ");
                if (LgrandBasDroit)
                    System.out.print("6 pour aller en (" + colEt1 + "," + (6-ligne) + ") ");
                if (LpetitBasGauche)
                    System.out.print("7 pour aller en (" + colSans2 + "," + (7-ligne) + ") ");
                if (LpetitBasDroit)
                    System.out.print("8 pour aller en (" + colEt2 + "," + (7-ligne) + ") ");

                System.out.print(": ");
                String choixS = sc.nextLine();
                choix = methodes.conversionEnInt(choixS);

                if ((choix == 1 && LgrandHautGauche) || (choix == 2 && LgrandHautDroit) || (choix == 3 && LpetitHautGauche) || (choix == 4 && LpetitHautDroit)
                        || (choix == 5 && LgrandBasGauche) || (choix == 6 && LgrandBasDroit) || (choix == 7 && LpetitBasGauche) || (choix == 8 && LpetitBasDroit)) {
                    valide = true;
                } else {
                    System.out.println("❌ Direction impossible, recommencez.");
                }
            }
        }
        return choix;
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche et de droite
    //sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour(int[][] plateau, int ligne, int colonne) {
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {0, 0, -1, 1};
        int[] colonnes = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            nouvelleL = ligne + lignes[i];
            nouvelleC = colonne + colonnes[i];

            if (caseValide(nouvelleL, nouvelleC)) {
                if (plateau[nouvelleL][nouvelleC] == 0) {
                    return false;
                }
                boolean pieceEnFace = plateau[nouvelleL][nouvelleC] > 6;

                if (pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche, de droite
    //et les 4 diagonales sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour2(int[][] plateau, int ligne, int colonne) {
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {-1, 1, -1, 1};
        int[] colonnes = {-1, 1, 1, -1};

        for (int i = 0; i < 4; i++) {
            nouvelleL = ligne + lignes[i];
            nouvelleC = colonne + colonnes[i];

            if (caseValide(nouvelleL, nouvelleC)) {
                if (plateau[nouvelleL][nouvelleC] == 0) {
                    return false;
                }
                boolean pieceEnFace = plateau[nouvelleL][nouvelleC] > 6;

                if (pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean pieceAutour3(int[][] plateau, int ligne, int colonne) {
        int pion = plateau[ligne][colonne];
        int sens;

        // Déterminer le sens selon la couleur
        if (pion == 12) {
            sens = -1; // Bleu monte
        } else {
            sens = 1;  // Jaune descend
        }

        // On vérifie les 3 possibilités de mouvement
        boolean diagoGauche = caseValide(ligne + sens, colonne - 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne - 1, pion);
        boolean diagoDroite = caseValide(ligne + sens, colonne + 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne + 1, pion);
        boolean peutAvancer = caseValide(ligne + sens, colonne) && plateau[ligne + sens][colonne] == 0;

        // Si une des options est possible, on renvoie vrai
        if (diagoGauche || diagoDroite || peutAvancer) {
            return false;
        } else {
            return true;
        }
    }

    //affichage du plateau
    public static void plateau(int[][] plateau) {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                plateau[i][j] = 0; //case vide
                plateau[1][j] = 6; //Pion Jaune
                plateau[6][j] = 12; //Pion Bleu
            }
        }

        //Tour Jaune
        plateau[0][0] = 1;
        plateau[0][7] = 1;

        //Tour Bleu
        plateau[7][0] = 7;
        plateau[7][7] = 7;

        //Cavalier Jaune
        plateau[0][1] = 2;
        plateau[0][6] = 2;

        //Cavalier Bleu
        plateau[7][1] = 8;
        plateau[7][6] = 8;

        //Fou Jaune
        plateau[0][2] = 3;
        plateau[0][5] = 3;

        //Fou Bleu
        plateau[7][2] = 9;
        plateau[7][5] = 9;

        //Dame Jaune
        plateau[0][3] = 4;

        //Roi Bleu
        plateau[7][4] = 10;

        //Roi Jaune
        plateau[0][4] = 5;

        //Dame Bleu
        plateau[7][3] = 11;
    }

    //Affichage des pièces sur le plateau
    public static void remplir(int[][] plateau) {
        String RESET = "\u001B[0m";
        String BLEU = "\u001B[34m";
        String JAUNE = "\u001B[33m";

        System.out.println("      A         B         C         D         E         F         G         H");
        for (int i = 0; i < plateau.length; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 1) {
                    System.out.print("|" + JAUNE + "  Tour  " + RESET + "|");
                } else if (plateau[i][j] == 2) {
                    System.out.print("|" + JAUNE + "Cavalier" + RESET + "|");
                } else if (plateau[i][j] == 3) {
                    System.out.print("|" + JAUNE + "  Fou   " + RESET + "|");
                } else if (plateau[i][j] == 4) {
                    System.out.print("|" + JAUNE + "  Dame  " + RESET + "|");
                } else if (plateau[i][j] == 5) {
                    System.out.print("|" + JAUNE + "  Roi   " + RESET + "|");
                } else if (plateau[i][j] == 6) {
                    System.out.print("|" + JAUNE + "  Pion  " + RESET + "|");
                } else if (plateau[i][j] == 7) {
                    System.out.print("|" + BLEU + "  Tour  " + RESET + "|");
                } else if (plateau[i][j] == 8) {
                    System.out.print("|" + BLEU + "Cavalier" + RESET + "|");
                } else if (plateau[i][j] == 9) {
                    System.out.print("|" + BLEU + "  Fou   " + RESET + "|");
                } else if (plateau[i][j] == 10) {
                    System.out.print("|" + BLEU + "  Roi   " + RESET + "|");
                } else if (plateau[i][j] == 11) {
                    System.out.print("|" + BLEU + "  Dame  " + RESET + "|");
                } else if (plateau[i][j] == 12) {
                    System.out.print("|" + BLEU + "  Pion  " + RESET + "|");
                } else {
                    if ((i + j) % 2 == 0) {
                        System.out.print("|        |");
                    } else {
                        System.out.print("| ****** |");
                    }
                }
            }
            System.out.print(" " + (8-i));
            System.out.println();
        }
        System.out.println("      A         B         C         D         E         F         G         H");
        System.out.println();
    }

    public static void remplir2(int[][] plateau) {
        String RESET = "\u001B[0m";
        String BLEU = "\u001B[34m";
        String JAUNE = "\u001B[33m";

        System.out.println("      H         G         F         E         D         C         B         A");
        for (int i = 0; i < plateau.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < plateau[i].length; j++) {
                int piece = plateau[7 - i][7 - j];

                if (piece == 1) {
                    System.out.print("|" + JAUNE + "  Tour  " + RESET + "|");
                } else if (piece == 2) {
                    System.out.print("|" + JAUNE + "Cavalier" + RESET + "|");
                } else if (piece == 3) {
                    System.out.print("|" + JAUNE + "  Fou   " + RESET + "|");
                } else if (piece == 4) {
                    System.out.print("|" + JAUNE + "  Dame  " + RESET + "|");
                } else if (piece == 5) {
                    System.out.print("|" + JAUNE + "  Roi   " + RESET + "|");
                } else if (piece == 6) {
                    System.out.print("|" + JAUNE + "  Pion  " + RESET + "|");
                } else if (piece == 7) {
                    System.out.print("|" + BLEU + "  Tour  " + RESET + "|");
                } else if (piece == 8) {
                    System.out.print("|" + BLEU + "Cavalier" + RESET + "|");
                } else if (piece == 9) {
                    System.out.print("|" + BLEU + "  Fou   " + RESET + "|");
                } else if (piece == 10) {
                    System.out.print("|" + BLEU + "  Roi   " + RESET + "|");
                } else if (piece == 11) {
                    System.out.print("|" + BLEU + "  Dame  " + RESET + "|");
                } else if (piece == 12) {
                    System.out.print("|" + BLEU + "  Pion  " + RESET + "|");
                } else {
                    if ((i + j) % 2 == 0) {
                        System.out.print("|        |");
                    } else {
                        System.out.print("| ****** |");
                    }
                }
            }
            System.out.print(" " + (i+1));
            System.out.println();
        }
        System.out.println("      H         G         F         E         D         C         B         A");
        System.out.println();
    }

    //méthode qui vérifie si le mouvement est possible en vérifiant
    //s'il y a des pièces empêchant un mouvement de plusieurs
    //lignes (sah g la flemme de bien expliquer sorry)
    public static boolean empechement(int[][] plateau, int ligne, int colonne, int distance, int directionLigne, int directionColonne) {
        for (int i = 1; i < distance; i++) {
            int l = ligne + i * directionLigne;
            int c = colonne + i * directionColonne;

            if (caseValide(l, c) && plateau[l][c] != 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean empechementRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        int directionLigne = 0;
        int directionColonne = 0;

        //Direction des lignes
        if (nvLigne > ligne)
            directionLigne = 1;
        else if (nvLigne < ligne)
            directionLigne = -1;

        //Direction des colonnes
        if (nvColonne > colonne)
            directionColonne = 1;
        else if (nvColonne < colonne)
            directionColonne = -1;

        int l = ligne + directionLigne;
        int c = colonne + directionColonne;

        while (l != nvLigne || c != nvColonne) {
            if (caseValide(l, c) && plateau[l][c] != 0) {
                return true;
            }
            l = l + directionLigne;
            c = c + directionColonne;
        }
        return false;
    }


    //méthode qui vérifie si la pièce actuelle est de la même couleur que
    //celle de la couleur de la pièce de notre choix en fonction de sa
    //position dans le plateau
    public static boolean memeCouleur(int[][] plateau, int ligne, int colonne, int couleur) {
        if (plateau[ligne][colonne] == 0){
            return false;
        }

        boolean bleu = couleur > 6;
        boolean pieceEnFace = plateau[ligne][colonne] > 6;

        return bleu == pieceEnFace;
    }

    public static boolean memeCouleurEtVide(int[][] plateau, int ligne, int colonne, int couleur) {
        if (plateau[ligne][colonne] == 0) return true;

        boolean bleu = couleur > 6;
        boolean pieceEnFace = plateau[ligne][colonne] > 6;

        return bleu == pieceEnFace;
    }

    public static int affichageDirections(int[][] plateau, int ligne, int colonne, int couleur, char joueur) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        boolean haut = haut(plateau, ligne, colonne, couleur);
        boolean gauche = gauche(plateau, ligne, colonne, couleur);
        boolean droite = droite(plateau, ligne, colonne, couleur);
        boolean bas = bas(plateau, ligne, colonne, couleur);

        if (haut) {
            possibilites++;
            choix = 1;
        }
        if (bas) {
            possibilites++;
            choix = 4;
        }
        if (gauche) {
            possibilites++;
            choix = 2;
        }
        if (droite) {
            possibilites++;
            choix = 3;
        }

        if (possibilites > 1) {
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if (joueur == 'B') {
                    if (haut)
                        System.out.print("1 pour aller en haut ");
                    if (gauche)
                        System.out.print("2 pour aller à gauche ");
                    if (droite)
                        System.out.print("3 pour aller à droite ");
                    if (bas)
                        System.out.print("4 pour aller en bas ");
                } else {
                    if (haut)
                        System.out.print("1 pour aller en bas ");
                    if (gauche)
                        System.out.print("2 pour aller à droite ");
                    if (droite)
                        System.out.print("3 pour aller à gauche ");
                    if (bas)
                        System.out.print("4 pour aller en haut ");
                }
                System.out.print(": ");
                String choixS = sc.nextLine();
                choix = methodes.conversionEnInt(choixS);

                if ((choix == 1 && haut) || (choix == 2 && gauche) || (choix == 3 && droite) || (choix == 4 && bas)) {
                    valide = true;
                } else {
                    System.out.println("❌ Direction impossible, recommencez.");
                }
            }
        }
        return choix;
    }

    public static int affichageChoixPion(boolean diagoDroite, boolean diagoGauche, boolean avanceUn, char joueur) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        if(diagoGauche)
            possibilites++;
        if(diagoDroite)
            possibilites++;
        if(avanceUn)
            possibilites++;

        if(possibilites>1) {
            while (!valide) {
                if (joueur == 'B') {
                    if (diagoGauche)
                        System.out.print("1 prendre la pièce en diagonale gauche ");
                    if (diagoDroite)
                        System.out.print("2 prendre la pièce en diagonale droite ");
                } else {
                    if (diagoGauche)
                        System.out.print("1 prendre la pièce en diagonale droite ");
                    if (diagoDroite)
                        System.out.print("2 prendre la pièce en diagonale gauche ");
                }
                if (avanceUn)
                    System.out.print("3 pour avancer ");
                System.out.print(": ");
                String choixS = sc.nextLine();
                choix = methodes.conversionEnInt(choixS);

                if ((choix == 1 && diagoGauche) || (choix == 2 && diagoDroite) || (choix == 3 && avanceUn)) {
                    valide = true;
                } else {
                    System.out.println("❌ Direction impossible, recommencez.");
                }
            }
        }

        return choix;
    }

    public static int ChoixPromotion(int pion){
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        int promotion = -1;

        while (choix != 1 && choix != 2 && choix != 3 && choix != 4) {
            System.out.print("1 promouvoir en dame, 2 promouvoir en tour, 3 promouvoir en fou, 4 promouvoir en cavalier : ");
            String choixS = sc.nextLine();
            choix = methodes.conversionEnInt(choixS);
        }
        if (pion == 12) { // Cas du pion bleu
            if (choix == 1){
                promotion = 11;
            }
            else if (choix == 2){
                promotion = 7;
            }
            else if (choix == 3){
                promotion = 9;
            }
            else {
                promotion = 8;
            }
        }
        else {          // Cas du pion jaune
            if (choix == 1){
                promotion = 4;
            }
            else if (choix == 2){
                promotion = 1;
            }
            else if (choix == 3){
                promotion = 3;
            }
            else {
                promotion = 2;
            }
        }

        return promotion;
    }

    public static void priseEnPassantRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne, int pion) {
        boolean priseEnPassantG = pepPossibleGauche(plateau, ligne, colonne, pion);
        boolean priseEnPassantD = pepPossibleDroite(plateau, ligne, colonne, pion);

        if (pepRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion)) {
            if (priseEnPassantG)
                PriseEnPassantG(plateau, ligne, colonne, pion);
            else if (priseEnPassantD)
                PriseEnPassantD(plateau, ligne, colonne, pion);
        }

        if (Math.abs(ligne - nvLigne) == 2) {
            pep = true;
            ligneAvant = nvLigne;
            colonneAvant = colonne;
        } else {
            pep = false;
        }
    }

    public static void mouvementPion(int[][] plateau, int ligne, int colonne, int pion, char joueur) {
        int nvLigne;
        int nvColonne;
        boolean mouvementValide;

        int ligneFin;

        if (pion == 12) { // Pion Bleu
            ligneFin = 0;
        } else {           // Pion Jaune
            ligneFin = 7;
        }
        do {
            do {
                System.out.println("Où voulez-vous aller avec votre pion ?");
                nvLigne = coordonneeLigne();
                nvColonne = coordonneeColonne();

            } while (nvColonne == 10);

            mouvementValide = mouvementPionRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion) || pepRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion);

            priseEnPassantRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion);

            if (!mouvementValide) {
                System.out.println("Le pion ne peut pas aller là");
            }
        } while (!mouvementValide);

        // Mise à jour du plateau
        plateau[ligne][colonne] = 0;
        plateau[nvLigne][nvColonne] = pion;

        // Affichage du mouvement
        if(!pepRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion)) {
            AffichageSituation(plateau, joueur, pion, ligne, colonne, nvLigne, nvColonne, -1, -1);
        }

        // Vérification de la promotion
        if (nvLigne == ligneFin) {
            plateau[nvLigne][nvColonne] = ChoixPromotion(pion);
        }
    }

    public static void mouvementRoi(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        int nvLigne, nvColonne;
        boolean mouvementValide = false;

        do {
            do {
                System.out.println("Où voulez-vous aller avec votre roi ?");
                nvLigne = coordonneeLigne();
                nvColonne = coordonneeColonne();

            }while(nvColonne==10);

            // Calcul de la distance parcourue
            int distL = Math.abs(nvLigne - ligne);
            int distC = Math.abs(nvColonne - colonne);

            // Le roi se déplace d'une case et il doit bouger
            if (distL <= 1 && distC <= 1 && (distL != 0 || distC != 0)) {

                // On vérifie qu'il ne va pas sur une case avec ses propres pièces
                if (!memeCouleur(plateau, nvLigne, nvColonne, piece)) {
                    mouvementValide = true;
                } else {
                    System.out.println("Le roi ne peut pas aller là");
                }
            } else {
                System.out.println("Le roi ne peut pas aller là");
            }
        } while (!mouvementValide);

        // Mise à jour du plateau
        plateau[ligne][colonne] = 0;
        plateau[nvLigne][nvColonne] = piece;

        // Affichage du mouvement
        AffichageSituation(plateau, joueur, piece, ligne, colonne, nvLigne, nvColonne, -1, -1);

    }

    //booleen qui recense tous les mouvements possibles du cavalier (mode 2)
    public static boolean mouvementCavalier(int ligne, int colonne, int nvLigne, int nvColonne) {
        return (Math.abs(nvLigne-ligne)==2 && Math.abs(nvColonne-colonne)==1) || (Math.abs(nvLigne-ligne)==1 && Math.abs(nvColonne-colonne)==2);
    }

    //booleen qui recense tous les mouvements possibles de la Tour (mode 2)
    public static boolean mouvementTour(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        return (nvLigne==ligne && nvColonne!=colonne) || (nvLigne!=ligne && nvColonne==colonne) && !empechementRobot(plateau, ligne, colonne, nvLigne, nvColonne);    //mouvements seulement droits
    }

    //booleen qui recense tous les mouvements possibles du fou (mode 2)
    public static boolean mouvementFou(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        return Math.abs(nvLigne - ligne) == Math.abs(nvColonne - colonne) && !empechementRobot(plateau, ligne, colonne, nvLigne, nvColonne);      //mouvements seulement en diagonale
    }

    public static void destinationPiece(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        int NvLigne, NvColonne;
        boolean mouvementValide;
        String col;

        do {
            do {
                mouvementValide = false;
                System.out.println("Où veux-tu aller ?");
                NvLigne = coordonneeLigne();

                NvColonne = coordonneeColonne();
            } while (NvColonne == 10);

            if (piece == 1 || piece == 7 || piece == 4 || piece == 11) {
                if (mouvementTour(plateau, ligne, colonne, NvLigne, NvColonne) && !empechementRobot(plateau, ligne, colonne, NvLigne, NvColonne)){
                    mouvementValide = true;
                }
            }
            if (piece == 3 || piece == 9 || piece == 4 || piece == 11) {
                if (mouvementFou(plateau, ligne, colonne, NvLigne, NvColonne)) {
                    mouvementValide = true;
                }
            }
            if (piece == 2 || piece == 8) {
                mouvementValide = mouvementCavalier(ligne, colonne, NvLigne, NvColonne);
            }

            if (!(caseValide(NvLigne, NvColonne)) || memeCouleur(plateau, NvLigne, NvColonne, piece)) {
                mouvementValide = false;
                System.out.println("La pièce ne peut pas aller là");
            }

        } while (!mouvementValide);

        AffichageSituation(plateau, joueur, piece, ligne, colonne, NvLigne, NvColonne, -1, -1);

        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = piece;
    }

    public static boolean hautGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne - 1) && !(memeCouleur(plateau, ligne - 1, colonne - 1, couleur));
    }

    public static boolean hautDroite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne + 1) && !(memeCouleur(plateau, ligne - 1, colonne + 1, couleur));
    }

    public static boolean basGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne - 1) && !(memeCouleur(plateau, ligne + 1, colonne - 1, couleur));
    }

    public static boolean basDroite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne + 1) && !(memeCouleur(plateau, ligne + 1, colonne + 1, couleur));
    }

    public static boolean haut(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne) && !(memeCouleur(plateau, ligne - 1, colonne, couleur));
    }

    public static boolean gauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne, colonne - 1) && !(memeCouleur(plateau, ligne, colonne - 1, couleur));
    }

    public static boolean droite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne, colonne + 1) && !(memeCouleur(plateau, ligne, colonne + 1, couleur));
    }

    public static boolean bas(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne) && !(memeCouleur(plateau, ligne + 1, colonne, couleur));
    }

    public static void BougePion(int[][] plateau, int ligne, int colonne, char joueur, int pion) {
        Scanner sc = new Scanner(System.in);

        int avancer = 1;
        int reponse = -1;
        boolean priseEnPassantG = pepPossibleGauche(plateau, ligne, colonne, pion);
        boolean priseEnPassantD = pepPossibleDroite(plateau, ligne, colonne, pion);
        // On définit les règles selon la couleur du pion
        int sens;           // -1 pour monter, 1 pour descendre
        int ligneDepart;    // La ligne où il peut avancer de 2
        int ligneFin;       // La ligne où il a une promotion
        int NvLigne = ligne;
        int NvColonne = colonne;
        /*int direction = 0;*/

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
        boolean diagoDroite = caseValide(ligne + sens, colonne + 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne + 1, pion);
        boolean diagoGauche = caseValide(ligne + sens, colonne - 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne - 1, pion);
        boolean peutAvancerUn = plateau[ligne + sens][colonne] == 0;
        boolean peutAvancerDeux = (ligne == ligneDepart) && plateau[ligne + (2 * sens)][colonne] == 0;

        // Demander au joueur ce qu'il veut faire
        int choix = affichageChoixPion(diagoDroite, diagoGauche, peutAvancerUn, joueur);

        // On vide la case de départ
        plateau[ligne][colonne] = 0;

        if (priseEnPassantG || priseEnPassantD) {
            do {
                System.out.print("Voulez-vous faire une prise en passant ? (1 pour oui, 0 pour non) ");
                String reponseS = sc.nextLine();

                reponse = methodes.conversionEnInt(reponseS);
            } while (reponse != 1 && reponse != 0);
        }
        if (reponse == 1){
            if (priseEnPassantG)
                PriseEnPassantG(plateau, ligne, colonne, pion);
            else
                PriseEnPassantD(plateau, ligne, colonne, pion);
            pep = false;
        }
        else {
            pep = false;
            if ((diagoGauche && !diagoDroite && !peutAvancerUn) || choix == 1) { // Mouvement Diagonale Gauche
                NvLigne = ligne + sens;
                NvColonne = colonne - 1;
                /*direction = 5;*/
            } else if ((!diagoGauche && diagoDroite && !peutAvancerUn) || choix == 2) { // Mouvement Diagonale Droite
                NvLigne = ligne + sens;
                NvColonne = colonne + 1;
                /*direction = 6;*/
            } else if ((!diagoGauche && !diagoDroite && peutAvancerUn) || choix == 3) { // Avancer tout droit
                if (peutAvancerDeux) {
                    do {
                        System.out.print("Avancer de 1 ou 2 cases : ");
                        String avancerS = sc.nextLine();
                        avancer = methodes.conversionEnInt(avancerS);
                    } while (avancer != 1 && avancer != 2);
                    NvLigne = ligne + (avancer * sens);
                    if (avancer == 2) {
                        pep = true;
                        ligneAvant = NvLigne;
                        colonneAvant = colonne;
                    }
                } else {
                    NvLigne = ligne + sens;
                }
                /*direction = 1;*/
            }

            // On place le pion ou la promotion
            if (NvLigne == ligneFin) {
                plateau[NvLigne][NvColonne] = ChoixPromotion(pion);
            } else {
                AffichageSituation(plateau, joueur, pion, ligne, colonne, NvLigne, NvColonne, avancer, 0);
                plateau[NvLigne][NvColonne] = pion;
            }
        }
    }

    public static void BougeRoi(int[][] plateau, int ligne, int colonne, char joueur, int couleur, int mvtTourLoin, int mvtTourProche, int mvtRoi){
        Scanner sc = new Scanner(System.in);

        int choix = 0;
        int oui = 0;

        boolean TourLointaine = TourPasBougee(mvtTourLoin);
        boolean TourProche = TourPasBougee(mvtTourProche);

        boolean mouvementAzero = nbMouvementsTourRoi(TourLointaine, TourProche, mvtRoi);
        boolean petit = PetitRoque(plateau, ligne, colonne, TourProche, couleur);
        boolean grand = GrandRoque(plateau, ligne, colonne, TourLointaine, couleur);

        boolean haut = haut(plateau, ligne, colonne, joueur);
        boolean gauche = gauche(plateau, ligne, colonne, joueur);
        boolean droite = droite(plateau, ligne, colonne, joueur);
        boolean bas = bas(plateau, ligne, colonne, joueur);

        boolean hautGauche = hautGauche(plateau, ligne, colonne, couleur);
        boolean hautDroite = hautDroite(plateau, ligne, colonne, couleur);
        boolean basGauche = basGauche(plateau, ligne, colonne, couleur);
        boolean basDroite = basDroite(plateau, ligne, colonne, couleur);

        boolean ligneDroite = (haut || gauche || droite || bas);
        boolean diagonale = (hautGauche || hautDroite || basGauche || basDroite);

        if (mouvementAzero && (petit || grand)) {
            System.out.print("Voulez-vous roquer ? (1 pour oui, n'importe pour non) : ");
            String ouiS = sc.nextLine();
            oui = methodes.conversionEnInt(ouiS);
        }
        if (oui == 1) {
            roque(plateau, couleur, ligne, colonne, TourLointaine, TourProche);
        } else {
            if (ligneDroite && diagonale) {
                while (choix != 1 && choix != 2) {
                    System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale");
                    String choixS = sc.nextLine();
                    choix = methodes.conversionEnInt(choixS);
                }
            } else if (ligneDroite) {
                choix = 1;
            } else if (diagonale) {
                choix = 2;
            }

            if (choix == 1) {
                BougeTour(plateau, ligne, colonne, couleur, joueur);
            } else if (choix == 2) {
                BougeFou(plateau, ligne, colonne, couleur, joueur);
            }
        }
    }

    public static void BougeDame(int[][] plateau, int ligne, int colonne, char joueur, int couleur, int mode){
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        boolean haut = haut(plateau, ligne, colonne, joueur);
        boolean gauche = gauche(plateau, ligne, colonne, joueur);
        boolean droite = droite(plateau, ligne, colonne, joueur);
        boolean bas = bas(plateau, ligne, colonne, joueur);

        boolean hautGauche = hautGauche(plateau, ligne, colonne, couleur);
        boolean hautDroite = hautDroite(plateau, ligne, colonne, couleur);
        boolean basGauche = basGauche(plateau, ligne, colonne, couleur);
        boolean basDroite = basDroite(plateau, ligne, colonne, couleur);

        boolean ligneDroite = (haut || gauche || droite || bas);
        boolean diagonale = (hautGauche || hautDroite || basGauche || basDroite);

        if (ligneDroite && diagonale) {
            while (choix != 1 && choix != 2) {
                System.out.print("1 pour aller en ligne droite, 2 pour aller en diagonale : ");
                String choixS = sc.nextLine();
                choix = methodes.conversionEnInt(choixS);
            }
        } else if (ligneDroite) {
            choix = 1;
        } else if (diagonale) {
            choix = 2;
        }

        if (choix == 1) {
            pieces.tour(plateau, ligne, colonne, mode, joueur);
        } else {
            pieces.fou(plateau, ligne, colonne, mode, joueur);
        }
    }

    public static int affichageDirectionsFou(int[][] plateau, int ligne, int colonne, int couleur, char joueur) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        boolean hautGauche = hautGauche(plateau, ligne, colonne, couleur);
        boolean hautDroite = hautDroite(plateau, ligne, colonne, couleur);
        boolean basGauche = basGauche(plateau, ligne, colonne, couleur);
        boolean basDroite = basDroite(plateau, ligne, colonne, couleur);

        if (hautGauche) {
            possibilites++;
            choix = 1;
        }
        if (hautDroite) {
            possibilites++;
            choix = 2;
        }
        if (basGauche) {
            possibilites++;
            choix = 3;
        }
        if (basDroite) {
            possibilites++;
            choix = 4;
        }

        if (possibilites > 1) {
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if (joueur == 'B') {
                    if (hautGauche)
                        System.out.print("1 pour aller en haut à gauche ");
                    if (hautDroite)
                        System.out.print("2 pour aller en haut à droite ");
                    if (basGauche)
                        System.out.print("3 pour aller en bas à gauche ");
                    if (basDroite)
                        System.out.print("4 pour aller en bas à droite ");
                } else {
                    if (hautGauche)
                        System.out.print("1 pour aller en bas à droite ");
                    if (hautDroite)
                        System.out.print("2 pour aller en bas à gauche ");
                    if (basGauche)
                        System.out.print("3 pour aller en haut à droite ");
                    if (basDroite)
                        System.out.print("4 pour aller en haut à gauche ");
                }
                System.out.print(": ");
                String choixS = sc.nextLine();
                choix = methodes.conversionEnInt(choixS);

                if ((choix == 1 && hautGauche) || (choix == 2 && hautDroite) || (choix == 3 && basGauche) || (choix == 4 && basDroite)) {
                    valide = true;
                } else {
                    System.out.println("❌ Direction impossible, recommencez.");
                }
            }
        }
        return choix;
    }

    public static void BougeTour(int[][] plateau, int ligne, int colonne, int couleur, char joueur) {
        int valeurDirection = 0;
        int direction = affichageDirections(plateau, ligne, colonne, couleur, joueur);

        int hautBas = 0;    //valeur pour le mouvement Haut ou Bas selon la demande
        int gaucheDroite = 0;   //valeur pour le mouvement gauche ou droite selon la demande

        switch (direction) {          //hautBas prend la valeur de -1 pour monter
            case 1:     //hautBas prend la valeur de -1 pour aller en haut
                hautBas = -1;
                valeurDirection = 1;
                break;
            case 2:     //gaucheDroite prend la valeur de -1 pour aller à gauche
                gaucheDroite = -1;
                valeurDirection = 2;
                break;
            case 3:     //gaucheDroite prend la valeur de 1 pour aller à droite
                gaucheDroite = 1;
                valeurDirection = 3;
                break;
            case 4:     //hautBas prend la valeur de 1 pour descendre
                hautBas = 1;
                valeurDirection = 4;
                break;
        }
        if(couleur == 1 || couleur == 7 || couleur == 4 || couleur == 11) {      //tours+dame
            Methode1(plateau, ligne, colonne, couleur, hautBas, gaucheDroite, valeurDirection, joueur);
        }
        else{       //rois
            int NvLigne = ligne + hautBas;            //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

            AffichageSituation(plateau, joueur, couleur, ligne, colonne, NvLigne, NvColonne, 1, valeurDirection);

            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }
    }

    public static void BougeFou(int[][] plateau, int ligne, int colonne, int couleur, char joueur) {
        int direction = affichageDirectionsFou(plateau, ligne, colonne, couleur, joueur);
        int valeurDirection = 0;

        int hautBas = 0;    //valeur pour le mouvement Haut ou Bas selon la demande
        int gaucheDroite = 0;   //valeur pour le mouvement gauche ou droite selon la demande

        switch(direction) {
            case 1:
                hautBas = -1;
                gaucheDroite = -1;
                valeurDirection = 5;
                break;
            case 2:
                hautBas = -1;
                gaucheDroite = 1;
                valeurDirection = 6;
                break;
            case 3:
                hautBas = 1;
                gaucheDroite = -1;
                valeurDirection = 7;
                break;
            case 4:
                hautBas = 1;
                gaucheDroite = 1;
                valeurDirection = 8;
                break;
        }
        if(couleur == 3 || couleur == 9 || couleur == 4 || couleur == 11) {
            Methode1(plateau, ligne, colonne, couleur, hautBas, gaucheDroite, valeurDirection, joueur);
        }
        else{
            int NvLigne = ligne + hautBas;            //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

            AffichageSituation(plateau, joueur, couleur, ligne, colonne, NvLigne, NvColonne, 1, valeurDirection);

            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }
    }

    public static void roque(int[][] plateau, int Roi, int ligne, int colonne, boolean tourLoin, boolean tourProche){
        int Tour;
        String couleur;
        int choix = demandeRoque(plateau, ligne, colonne, tourLoin, tourProche, Roi);
        String col4 = conversionEnString(colonne+4);
        String col5 = conversionEnString(5-colonne);

        if(Roi==5) {    //roi jaune
            Tour = 1;
            couleur = "jaune";
        } else {
            Tour = 7;
            couleur = "bleu";
        }

        if(choix==1){
            System.out.println();
            System.out.println("\uD83C\uDF1F Le roi " + couleur + " a roqué avec la tour (" + col4 + "," + (ligne+1) + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne+2] = Roi;
            plateau[ligne][colonne+3] = 0;
            plateau[ligne][colonne+1] = Tour;
        }
        else if(choix==2){
            System.out.println();
            System.out.println("\uD83C\uDF1F Le roi " + couleur + " a roqué avec la tour (" + col5 + "," + (ligne+1) + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne-2] = Roi;
            plateau[ligne][colonne-4] = 0;
            plateau[ligne][colonne-1] = Tour;
        }
    }
    public static int demandeRoque(int[][] plateau, int ligne, int colonne, boolean TourLoin, boolean TourProche, int roi){
        Scanner scanner = new Scanner(System.in);

        boolean petit = PetitRoque(plateau, ligne, colonne, TourProche, roi);
        boolean grand = GrandRoque(plateau, ligne, colonne, TourLoin, roi);
        int choixRoque;

        if(petit && grand){
            do {
                System.out.print("Tapez 1 pour un petit Roque (avec la Tour la plus proche) ou tapez 2 pour un grand Roque (avec la Tour la plus éloignée)  : ");
                String choixRoqueS = scanner.nextLine();

                choixRoque = methodes.conversionEnInt(choixRoqueS);
            } while(choixRoque!=1 && choixRoque!=2);
        }
        else if(petit){
            choixRoque=1;
        }
        else{
            choixRoque=2;
        }
        return choixRoque;
    }

    public static boolean nbMouvementsTourRoi(boolean TourLoin, boolean TourProche, int nbMouvementR){
        return (TourLoin || TourProche) && nbMouvementR==0;
    }
    public static boolean PetitRoque(int[][] plateau, int ligne, int colonne, boolean TourProche, int roi){
        return TourProche && plateau[ligne][colonne+1]==0 && !estEnEchecCoordonnee(plateau, ligne, colonne+1, roi)
                && plateau[ligne][colonne+2]==0 && !estEnEchecCoordonnee(plateau, ligne, colonne+2, roi);
    }
    public static boolean GrandRoque(int[][] plateau, int ligne, int colonne, boolean TourLoin, int roi){
        return TourLoin && plateau[ligne][colonne-1]==0 && !estEnEchecCoordonnee(plateau, ligne, colonne-1, roi) &&
                plateau[ligne][colonne-2]==0 && !estEnEchecCoordonnee(plateau, ligne, colonne-2, roi)
                && plateau[ligne][colonne-3]==0 && !estEnEchecCoordonnee(plateau, ligne, colonne-3, roi) ;
    }

    public static void PriseEnPassantD(int[][] plateau, int ligne, int colonne, int pion){
        String couleur;
        String col = conversionEnString(colonne+1);

        if(pion==6) {    //pion jaune
            couleur = "jaune";
        } else {
            couleur = "bleu";
        }

        System.out.println();
        System.out.println("\uD83C\uDF1F le pion " + couleur + " a fais une prise en passant en (" + col + "," + (ligne + 2) + ")");

        plateau[ligne][colonne+1] = 0;
        plateau[ligneAvant][colonneAvant] = 0;

        if (pion == 12)
            plateau[ligne-1][colonne+1] = pion;
        else
            plateau[ligne+1][colonne+1] = pion;
    }

    public static void PriseEnPassantG(int[][] plateau, int ligne, int colonne, int pion){
        String couleur;
        String col = conversionEnString(colonne-1);

        if(pion==6) {    //pion jaune
            couleur = "jaune";
        } else {
            couleur = "bleu";
        }

        System.out.println();
        System.out.println("\uD83C\uDF1F le pion " + couleur + " a fais une prise en passant en (" + col + "," + ligne + ")");

        plateau[ligne][colonne-1] = 0;
        plateau[ligneAvant][colonneAvant] = 0;

        if (pion == 12)
            plateau[ligne-1][colonne-1] = pion;
        else
            plateau[ligne+1][colonne-1] = pion;
    }

    public static boolean pepPossibleGauche(int[][] plateau, int ligne, int colonne, int couleur){
        boolean possible = false;

        if(pep) {
            if (couleur == 12 && ligne == 3) {       //bleus
                if (ligneAvant==3 && colonneAvant==(colonne-1)) {
                    possible = true;
                }
            } else if (couleur == 6 && ligne == 4) {    //jaunes
                if (ligneAvant==4 && colonneAvant==(colonne-1)) {
                    possible = true;
                }
            }
        }
        return possible;
    }

    public static boolean pepPossibleDroite(int[][] plateau, int ligne, int colonne, int couleur){
        boolean possible = false;

        if(pep) {
            if (couleur == 12 && ligne == 3) {       //bleus
                if (ligneAvant==3 && colonneAvant==(colonne+1)) {
                    possible = true;
                }
            } else if (couleur == 6 && ligne == 4) {    //jaunes
                if (ligneAvant==4 && colonneAvant==(colonne+1)) {
                    possible = true;
                }
            }
        }
        return possible;
    }

    public static void Methode1(int[][] plateau, int ligne, int colonne, int couleur, int hautBas, int gaucheDroite, int direction, char joueur){
        Scanner sc = new Scanner(System.in);
        int NvLigne, NvColonne;
        int choix=1;

        if(!empechement(plateau, ligne, colonne, 3, hautBas, gaucheDroite)) {
            System.out.print("Tu veux avancer de combien ? ");      //demande de combien l'utilisateur veut se déplacer sans prendre en compte la direction
            String choixS = sc.nextLine();
            choix = methodes.conversionEnInt(choixS);
        }
        boolean empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);
        NvLigne = ligne + (hautBas * choix);        //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
        NvColonne = colonne + (gaucheDroite * choix);   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

        while (!(caseValide(NvLigne, NvColonne)) || memeCouleur(plateau, NvLigne, NvColonne, couleur) || empechement) {
            System.out.println("❌ impossible d'avancer jusque là");
            System.out.print("de combien veux-tu avancer ? : ");
            String choixS = sc.nextLine();
            choix = methodes.conversionEnInt(choixS);

            empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);

            NvLigne = ligne + (hautBas * choix);
            NvColonne = colonne + (gaucheDroite * choix);
        }

        AffichageSituation(plateau, joueur, couleur, ligne, colonne, NvLigne, NvColonne, choix, direction);
        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = couleur;
    }

    public static String piece(int piece){
        String pieces="vide";

        if(piece == 1 || piece == 7)
            pieces = "La tour ";
        else if(piece == 2 || piece == 8)
            pieces = "Le cavalier ";
        else if(piece == 3 || piece == 9)
            pieces = "Le fou ";
        else if(piece == 5 || piece == 10)
            pieces = "Le roi ";
        else if(piece == 4 || piece == 11)
            pieces = "La dame ";
        else if(piece == 6 || piece == 12)
            pieces = "Le pion ";

        return pieces;
    }
    public static String direction(int direction, int piece){
        String directions="vide";

        if(piece<=6) {
            switch (direction) {
                case 1: directions = "vers le haut";
                    break;
                case 2: directions = "vers la gauche";
                    break;
                case 3: directions = "vers la droite";
                    break;
                case 4: directions = "vers le bas";
                    break;
                case 5: directions = "vers le haut gauche en diagonale";
                    break;
                case 6: directions = "vers le haut droit en diagonale";
                    break;
                case 7: directions = "vers le bas gauche en diagonale";
                    break;
                case 8: directions = "vers Le bas droit en diagonale";
                    break;
            }
        }
        else{
            switch (direction) {
                case 1: directions = "vers le bas";
                    break;
                case 2: directions = "vers la droite";
                    break;
                case 3: directions = "vers la gauche";
                    break;
                case 4: directions = "vers le haut";
                    break;
                case 5: directions = "vers le bas droit en diagonale";
                    break;
                case 6: directions = "vers le bas gauche en diagonale";
                    break;
                case 7: directions = "vers le haut droit en diagonale";
                    break;
                case 8: directions = "vers le haut gauche en diagonale";
                    break;
                default: directions="";
                    break;
            }
        }
        return directions;
    }

    public static void AffichageSituation(int[][] plateau, char joueur, int pieceC, int ligne, int colonne, int nvLigne, int nvColonne, int distance, int direction) {
        String piece = piece(pieceC);
        String directions = direction(direction, pieceC);
        String couleur;
        String col = conversionEnString(colonne);
        String nvCol = conversionEnString(nvColonne);

        if (!estEnEchec(plateau, joueur)) {
            if (pieceC > 6)
                couleur = "bleue ";
            else
                couleur = "jaune ";

            System.out.println();
            if (distance > 0) {
                System.out.println("\uD83C\uDF1F " + piece + couleur + "avance de " + distance + " cases " + directions + " (" + col + "," + (8-ligne) + ") -> (" + nvCol + "," + (8 - nvLigne) + ")");
            } else {
                System.out.println("\uD83C\uDF1F " + piece + couleur + "s'est déplacé de la case (" + col + "," + (8 - ligne) + ") vers la case (" + nvCol + "," + (8 - nvLigne)+ ")");
            }
        }
    }

    public static boolean MaterielInsuffisant(int[][] plateau){
        int nbFou = 0;
        int nbCavalier = 0;
        int nbAutres = 0; // dames, tours, pions
        int FouCaseB = 0; // nombre de fou de case Noire
        int FouCaseN = 0; // nombre de fou de case Blanche

        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {
                int piece = plateau[ligne][colonne];

                if (piece != 0) {
                    if (piece == 3 || piece == 9) {             //Fous
                        nbFou++;
                        if ((ligne + colonne) % 2 == 0)         // 0 = noire, 1 = blanche
                            FouCaseN++;
                        else
                            FouCaseB++;
                    }
                    else if (piece == 2 || piece == 8)      //Cavaliers
                        nbCavalier++;
                    else if (piece != 5 && piece != 10)     //Autre pièces en dehors du Roi
                        nbAutres++;
                }
            }
        }

        if (nbAutres > 0)
            return false;
        if (nbFou == 0 && nbCavalier == 0)      //Roi contre Roi
            return true;
        if (nbFou == 1 && nbCavalier == 0)    //Roi+Fou contre Roi
            return true;
        if (nbFou == 0 && nbCavalier == 1)    //Roi+Cavalier contre Roi
            return true;
        if ((FouCaseN == 2 ^ FouCaseB ==2) && nbCavalier == 0)      //Roi+Fou contre Roi+Fou avec des Fous de même couleur
            return true;

        return false;
    }

    /*public static boolean peutAttaquer(int[][] plateau, int ligneEnnemi, int colonneEnnemi, int ligneRoi, int colonneRoi) {
        int piece = plateau[ligneEnnemi][colonneEnnemi];
        boolean possible = false;

        // cas du cavalier
        if (piece == 2 || piece == 8) {
            if (mouvementCavalier(ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                possible = true;
            }
        }

        // cas de la tour
        else if (piece == 1 || piece == 7) {
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                    possible = true;
                }
            }

        // cas du fou
        else if (piece == 3 || piece == 9) {
            if (mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                    possible = true;
                }
            }

        // cas de la dame
        else if (piece == 4 || piece == 11) {
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi) ||
                    mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                    possible = true;
                }
            }

        // cas du pion
        else if (piece == 6) { // Pion Jaune
            if (ligneRoi == ligneEnnemi + 1 && (colonneRoi == colonneEnnemi + 1 || colonneRoi == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        else if (piece == 12) { // Pion Bleu
            if (ligneRoi == ligneEnnemi - 1 && (colonneRoi == colonneEnnemi + 1 || colonneRoi == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        return possible;
    }*/

    public static boolean peutAttaquer(int[][] plateau, int ligneEnnemi, int colonneEnnemi, int ligneRoi, int colonneRoi) {
        int piece = plateau[ligneEnnemi][colonneEnnemi];
        boolean possible = false;

        // cas du cavalier
        if (piece == 2 || piece == 8) {
            if (mouvementCavalier(ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                possible = true;
            }
        }

        // cas de la tour
        else if (piece == 1 || piece == 7) {
            // On regarde si la tour est dans la même ligne ou même colonne que le roi
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                // On calcule la distance et la direction du mouvement
                int distance = Math.max(Math.abs(ligneRoi - ligneEnnemi), Math.abs(colonneRoi - colonneEnnemi));

                int directionLigne = 0;
                if (ligneRoi > ligneEnnemi){    // La tour attaque du haut
                    directionLigne = 1;
                }
                else if (ligneRoi < ligneEnnemi){   // La tour attaque du bas
                    directionLigne = -1;
                }
                int directionColonne = 0;
                if (colonneRoi > colonneEnnemi){    // La tour attaque de la gauche
                    directionColonne = 1;
                }
                else if (colonneRoi < colonneEnnemi){   // La tour attaque de la droite
                    directionColonne = -1;
                }

                // On vérifie s'il y a un obstacle sur le chemin
                if (!empechement(plateau, ligneEnnemi, colonneEnnemi, distance, directionLigne, directionColonne)) {
                    possible = true;
                }
            }
        }

        // cas du fou
        else if (piece == 3 || piece == 9) {
            if (mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                int distance = Math.abs(ligneRoi - ligneEnnemi);

                int directionLigne = 0;
                if (ligneRoi > ligneEnnemi){        //le fou attaque du haut
                    directionLigne = 1;
                }
                else if (ligneRoi < ligneEnnemi){   //le fou attaque du bas
                    directionLigne = -1;
                }

                int directionColonne = 0;
                if (colonneRoi > colonneEnnemi){        //le fou attaque de la gauche
                    directionColonne = 1;
                }
                else if (colonneRoi < colonneEnnemi){   //le fou attaque de la droite
                    directionColonne = -1;
                }

                if (!empechement(plateau, ligneEnnemi, colonneEnnemi, distance, directionLigne, directionColonne)) {
                    possible = true;
                }
            }
        }

        // cas de la dame
        else if (piece == 4 || piece == 11) {
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi) ||
                    mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {

                int distance = Math.max(Math.abs(ligneRoi - ligneEnnemi), Math.abs(colonneRoi - colonneEnnemi));

                int directionLigne = 0;
                if (ligneRoi > ligneEnnemi){        //la dame attaque du haut
                    directionLigne = 1;
                }
                else if (ligneRoi < ligneEnnemi){   //la dame attaque du bas
                    directionLigne = -1;
                }
                int directionColonne = 0;
                if (colonneRoi > colonneEnnemi){    //la dame attaque de la gauche
                    directionColonne = 1;
                }
                else if (colonneRoi < colonneEnnemi){   //le fou attaque de la droite
                    directionColonne = -1;
                }

                if (!empechement(plateau, ligneEnnemi, colonneEnnemi, distance, directionLigne, directionColonne)) {
                    possible = true;
                }
            }
        }

        // cas du pion
        else if (piece == 6) { // Pion Jaune
            if (ligneRoi == ligneEnnemi + 1 && (colonneRoi == colonneEnnemi + 1 || colonneRoi == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        else if (piece == 12) { // Pion Bleu
            if (ligneRoi == ligneEnnemi - 1 && (colonneRoi == colonneEnnemi + 1 || colonneRoi == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        return possible;
    }




    public static boolean estEnEchec(int[][] plateau, char joueur) {
        //Couleur du roi
        int Roi;
        if (joueur == 'B') {
            Roi = 10;
        } else {
            Roi = 5;
        }

        // Trouver la position du Roi sur le plateau
        int roiL = -1;
        int roiC = -1;
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                if (plateau[l][c] == Roi) {
                    roiL = l;
                    roiC = c;
                }
            }
        }

        // Parcourir tout le plateau pour trouver les pièces ennemies
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                int piece = plateau[l][c];
                if (piece != 0 && !memeCouleur(plateau, l, c, Roi) && peutAttaquer(plateau, l, c, roiL, roiC)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean estEnEchecCoordonnee(int[][] plateau, int ligne, int colonne, int Roi) {
        // Parcourir tout le plateau pour trouver les pièces ennemies
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                int piece = plateau[l][c];

                if (piece != 0 && !memeCouleur(plateau, l, c, Roi)) {
                    if (peutAttaquer(plateau, l, c, ligne, colonne)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean mouvementDame(int[][] plateau, int l, int c, int nl, int nc) {
        return mouvementTour(plateau, l, c, nl, nc) ||
                mouvementFou(plateau, l, c, nl, nc);
    }

    public static boolean estEnEchecEtMat(int[][] plateau, char joueur) {

        if (!estEnEchec(plateau, joueur)) return false;

        boolean joueurBleu = (joueur == 'B');

        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {

                int piece = plateau[l][c];
                if (piece != 0) {
                    if ((joueurBleu && piece > 6) || (!joueurBleu && piece <= 6)) {

                        for (int nl = 0; nl < 8; nl++) {
                            for (int nc = 0; nc < 8; nc++) {

                                boolean mouvementValide = false;
                                if (caseValide(nl, nc) && !memeCouleur(plateau, nl, nc, piece)) {

                                    if (piece == 5 || piece == 10) {
                                        mouvementValide = mouvementRoiRobot(l, c, nl, nc);
                                    } else if (piece == 6 || piece == 12) {
                                        mouvementValide = mouvementPionRobot(plateau, l, c, nl, nc, piece);
                                    }else if (piece == 8 || piece == 2) {
                                        mouvementValide = mouvementCavalier(l, c, nl, nc);
                                    } else {
                                        mouvementValide = peutAttaquer(plateau, l, c, nl, nc);
                                    }
                                }

                                if (mouvementValide) {

                                    int[][] copie = copiePlateau(plateau);
                                    copie[nl][nc] = piece;
                                    copie[l][c] = 0;

                                    if (!estEnEchec(copie, joueur)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
