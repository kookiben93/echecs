import java.util.Scanner;

public class methodes {
    static int mvtTourA1 = 0;
    static int mvtTourA8 = 0;
    static int mvtTourH1 = 0;
    static int mvtTourH8 = 0;
    static int mvtRoiN = 0;
    static int mvtRoiB = 0;
    static boolean pep=false;
    static int ligneAvant;
    static int colonneAvant;

    public static int debut(int choix) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Jouer au jeu d'échecs");
        System.out.println("2. Connaître les régles");
        System.out.println("3. Quitter le jeu");
        choix = scanner.nextInt();

        return choix;
    }

    public static void coordonnees(int[][] plateau, char joueur, int mode) {
        Scanner scanner = new Scanner(System.in);
        String col;
        int colonne;
        int ligne;

        System.out.println("Quelle pièce voulez vous jouer ? ");
        System.out.print("ligne : ");
        ligne = (Integer.parseInt(scanner.nextLine())) - 1;
        while (ligne < 0 || ligne > 7) {
            System.out.println("❌ Coordonnées impossible");
            System.out.print("ligne : ");
            ligne = (Integer.parseInt(scanner.nextLine())) - 1;
        }
        System.out.print("colonne : ");
        col = scanner.nextLine();

        colonne = conversionEnInt(col);

        while (colonne < 0 || colonne > 7) {
            System.out.println("❌ Coordonnées impossible");
            System.out.print("colonne : ");
            col = scanner.nextLine();
            colonne = conversionEnInt(col);
        }

        appelPiece(plateau, ligne, colonne, joueur, mode);
    }

    //Converti un String pris en paramètre en un int spécifique
    public static int conversionEnInt(String colonne) {
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

        return coordonnee;
    }

    //Reconverti un int pris en paramètre en sa valeur en String
    public static String conversionEnString(int colonne) {
        String coordonnee = "r";

        switch (colonne) {
            case 0:
                coordonnee = "A";
                break;
            case 1:
                coordonnee = "B";
                break;
            case 2:
                coordonnee = "C";
                break;
            case 3:
                coordonnee = "D";
                break;
            case 4:
                coordonnee = "E";
                break;
            case 5:
                coordonnee = "F";
                break;
            case 6:
                coordonnee = "G";
                break;
            case 7:
                coordonnee = "H";
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
                        mvtTourA1++;
                    else if (ligne == 0 && colonne == 7)
                        mvtTourH1++;
                    else if (ligne == 7 && colonne == 0)
                        mvtTourA8++;
                    else if (ligne == 7 && colonne == 7)
                        mvtTourH8++;
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
                    Main.abandon(plateau, ligne, colonne, -1, -1, -1, joueur, mode);
                } else {
                    if (plateau[ligne][colonne] == 5) {
                        Main.abandon(plateau, ligne, colonne, mvtTourA1, mvtTourH1, mvtRoiN, joueur, mode);
                        mvtRoiN++;
                    } else {
                        Main.abandon(plateau, ligne, colonne, mvtTourA8, mvtTourH8, mvtRoiB, joueur, mode);
                        mvtRoiB++;
                    }
                }

                //appel des cavaliers
            } else if (plateau[ligne][colonne] == 8 || plateau[ligne][colonne] == 2) {
                if (bougerCavalier(plateau, ligne, colonne)) {
                    System.out.println("❌ Impossible de bouger le cavalier");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.cavalier(plateau, ligne, colonne, mode);
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
                System.out.println("❌ Case vide, veuillez recommencez");
                coordonnees(plateau, joueur, mode);
            }
            if (estEnEchec(plateau, joueur)) {
                System.out.println("❌ Mouvement interdit : votre roi est en échec");

                plateauPrecedent(plateau, sauvegarde);

                coordonnees(plateau, joueur, mode);
            }
        } else {       //Si le joueurs joue des pièces de l'adversaire
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
        return caseValide(ligne - 2, colonne - 1) && (plateau[ligne - 2][colonne - 1] == 0 && !(memeCouleur(plateau, ligne - 2, colonne - 1, couleur)));
    }

    public static boolean LGrandHautDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 2, colonne + 1) && (plateau[ligne - 2][colonne + 1] == 0 && !(memeCouleur(plateau, ligne - 2, colonne + 1, couleur)));
    }

    public static boolean LPetitHautGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne - 2) && (plateau[ligne - 1][colonne - 2] == 0 && !(memeCouleur(plateau, ligne - 1, colonne - 2, couleur)));
    }

    public static boolean LPetitHautDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne + 2) && (plateau[ligne - 1][colonne + 2] == 0 && !(memeCouleur(plateau, ligne - 1, colonne + 2, couleur)));
    }

    public static boolean LGrandBasGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 2, colonne - 1) && (plateau[ligne + 2][colonne - 1] == 0 && !(memeCouleur(plateau, ligne + 2, colonne - 1, couleur)));
    }

    public static boolean LGrandBasDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 2, colonne + 1) && (plateau[ligne + 2][colonne + 1] == 0 && !(memeCouleur(plateau, ligne + 2, colonne + 1, couleur)));
    }

    public static boolean LPetitBasGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne - 2) && (plateau[ligne + 1][colonne - 2] == 0 && !(memeCouleur(plateau, ligne + 1, colonne - 2, couleur)));
    }

    public static boolean LPetitBasDroit(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne + 2) && (plateau[ligne + 1][colonne + 2] == 0 && !(memeCouleur(plateau, ligne + 1, colonne + 2, couleur)));
    }

    public static int directionCavalier(int[][] plateau, int ligne, int colonne, int couleur) {
        Scanner sc = new Scanner(System.in);

        int possibilites = 0;
        boolean valide = false;
        int choix = 0;

        String colEt1 = methodes.conversionEnString(colonne + 1);
        String colEt2 = methodes.conversionEnString(colonne + 2);
        String colSans1 = methodes.conversionEnString(colonne - 1);
        String colSans2 = methodes.conversionEnString(colonne - 2);

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
                    System.out.print("1 pour aller en (" + (ligne - 1) + "," + colSans1 + ") ");
                if (LgrandHautDroit)
                    System.out.print("2 pour aller en (" + (ligne - 1) + "," + colEt1 + ") ");
                if (LpetitHautGauche)
                    System.out.print("3 pour aller en (" + ligne + "," + colSans2 + ") ");
                if (LpetitHautDroit)
                    System.out.print("4 pour aller en (" + ligne + "," + colEt2 + ") ");
                if (LgrandBasGauche)
                    System.out.print("5 pour aller en (" + (ligne + 3) + "," + colSans1 + ") ");
                if (LgrandBasDroit)
                    System.out.print("6 pour aller en (" + (ligne + 3) + "," + colEt1 + ") ");
                if (LpetitBasGauche)
                    System.out.print("7 pour aller en (" + (ligne + 2) + "," + colSans2 + ") ");
                if (LpetitBasDroit)
                    System.out.print("8 pour aller en (" + (ligne + 2) + "," + colEt2 + ") ");

                System.out.print(": ");
                choix = Integer.parseInt(sc.nextLine());

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
        boolean diagoGauche = methodes.caseValide(ligne + sens, colonne - 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne - 1, pion);
        boolean diagoDroite = methodes.caseValide(ligne + sens, colonne + 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne + 1, pion);
        boolean peutAvancer = methodes.caseValide(ligne + sens, colonne) && plateau[ligne + sens][colonne] == 0;

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

        for (int i = 0; i < plateau.length; i++) {
            System.out.print(i + 1 + " ");
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
                    System.out.print("|        |");
                }
            }
            System.out.println();
        }
        System.out.println("      A         B         C         D         E         F         G         H");
        System.out.println();
    }

    public static void remplir2(int[][] plateau) {
        String RESET = "\u001B[0m";
        String BLEU = "\u001B[34m";
        String JAUNE = "\u001B[33m";

        for (int i = 0; i < plateau.length; i++) {
            System.out.print(8 - i + " ");

            for (int j = 0; j < plateau[i].length; j++) {
                int piece = plateau[7 - i][7 - j];

                if (piece == 1) {
                    System.out.print("|" + JAUNE + "   Tour   " + RESET + "|");
                } else if (piece == 2) {
                    System.out.print("|" + JAUNE + " Cavalier " + RESET + "|");
                } else if (piece == 3) {
                    System.out.print("|" + JAUNE + "   Fou    " + RESET + "|");
                } else if (piece == 4) {
                    System.out.print("|" + JAUNE + "   Dame   " + RESET + "|");
                } else if (piece == 5) {
                    System.out.print("|" + JAUNE + "   Roi    " + RESET + "|");
                } else if (piece == 6) {
                    System.out.print("|" + JAUNE + "   Pion   " + RESET + "|");
                } else if (piece == 7) {
                    System.out.print("|" + BLEU + "   Tour   " + RESET + "|");
                } else if (piece == 8) {
                    System.out.print("|" + BLEU + " Cavalier " + RESET + "|");
                } else if (piece == 9) {
                    System.out.print("|" + BLEU + "   Fou    " + RESET + "|");
                } else if (piece == 10) {
                    System.out.print("|" + BLEU + "   Roi    " + RESET + "|");
                } else if (piece == 11) {
                    System.out.print("|" + BLEU + "   Dame   " + RESET + "|");
                } else if (piece == 12) {
                    System.out.print("|" + BLEU + "   Pion   " + RESET + "|");
                } else {
                    System.out.print("|          |");
                }
            }
            System.out.println();
        }
        System.out.println("        H           G           F           E           D           C           B           A");
        System.out.println();
    }

    //méthode qui vérifie si le mouvement est possible en vérifiant
    //s'il y a des pièces empêchant un mouvement de plusieurs
    //lignes (sah g la flemme de bien expliquer sorry)
    public static boolean empechement(int[][] plateau, int ligne, int colonne, int distance, int dLigne, int dColonne) {
        for (int i = 1; i < distance; i++) {
            int l = ligne + i * dLigne;
            int c = colonne + i * dColonne;

            if (caseValide(l, c) && plateau[l][c] != 0) {
                return true;
            }
        }
        return false;
    }

    //méthode qui vérifie si la pièce actuelle est de la même couleur que
    //celle de la couleur de la pièce de notre choix en fonction de sa
    //position dans le plateauleau
    public static boolean memeCouleur(int[][] plateau, int ligne, int colonne, int couleur) {
        if (plateau[ligne][colonne] == 0) return false;

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
                choix = Integer.parseInt(sc.nextLine());

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
                choix = Integer.parseInt(sc.nextLine());

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
            choix = Integer.parseInt(sc.nextLine());
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

    public static void mouvementPion(int[][] plateau, int ligne, int colonne, int pion) {
        Scanner sc = new Scanner(System.in);
        int nvLigne;
        int nvColonne;
        int reponse;
        boolean mouvementValide = false;
        boolean priseEnPassantG = methodes.pepPossibleGauche(plateau, ligne, colonne, pion, pep, ligneAvant, colonneAvant);
        boolean priseEnPassantD = methodes.pepPossibleDroite(plateau, ligne, colonne, pion, pep, ligneAvant, colonneAvant);

        int sens;
        int ligneDepart;
        int ligneFin;

        if (pion == 12) { // Pion Bleu
            sens = -1;
            ligneDepart = 6;
            ligneFin = 0;
        } else {           // Pion Jaune
            sens = 1;
            ligneDepart = 1;
            ligneFin = 7;
        }
        if (priseEnPassantG || priseEnPassantD) {
            do {
                System.out.print("Voulez-vous faire une prise en passant ? (1 pour oui, 0 pour non) ");
                reponse = sc.nextInt();
            }while(reponse!=1 && reponse!=0);

            if (reponse == 1 && priseEnPassantG) {
                methodes.PriseEnPassantG(plateau, ligne, colonne, pion);
            }
            else{
                methodes.PriseEnPassantD(plateau, ligne, colonne, pion);
            }
            pep = false;
        } else {
            pep = false;
            do {
                System.out.println("Où voulez-vous aller avec votre pion ?");
                System.out.print("ligne : ");
                nvLigne = Integer.parseInt(sc.nextLine()) - 1;
                System.out.print("colonne : ");
                String col = sc.nextLine();
                nvColonne = conversionEnInt(col);

                if (caseValide(nvLigne, nvColonne)) {
                    int distL = (nvLigne - ligne) * sens;
                    int distC = Math.abs(nvColonne - colonne);

                    // Avancer de 1 case tout droit
                    if (distC == 0 && distL == 1 && plateau[nvLigne][nvColonne] == 0) {
                        mouvementValide = true;
                    }
                    // Avancer de 2 cases
                    else if (distC == 0 && distL == 2 && ligne == ligneDepart
                            && plateau[ligne + sens][colonne] == 0 && plateau[nvLigne][nvColonne] == 0) {
                        mouvementValide = true;

                        pep = true;
                        ligneAvant = nvLigne;
                        colonneAvant = colonne;

                    }
                    // Manger en diagonale
                    else if (distC == 1 && distL == 1 && plateau[nvLigne][nvColonne] != 0
                            && !memeCouleur(plateau, nvLigne, nvColonne, pion)) {
                        mouvementValide = true;
                    }
                }

                if (!mouvementValide) {
                    System.out.println("Le pion ne peut pas aller là");
                }

            } while (!mouvementValide);

            // Mise à jour du plateau
            plateau[ligne][colonne] = 0;
            plateau[nvLigne][nvColonne] = pion;

            // Vérification de la promotion

            if (nvLigne == ligneFin) {
                plateau[nvLigne][nvColonne] = ChoixPromotion(pion);
            }
        }
    }

    //booleen qui recense tous les mouvements possibles du cavalier (mode 2)
    public static boolean mouvementCavalier(int ligne, int colonne, int nvLigne, int nvColonne) {
        return (Math.abs(nvLigne-ligne)==2 && Math.abs(nvColonne-colonne)==1) || (Math.abs(nvLigne-ligne)==1 && Math.abs(nvColonne-colonne)==2);
    }

    //booleen qui recense tous les mouvements possibles de la Tour (mode 2)
    public static boolean mouvementTour(int ligne, int colonne, int nvLigne, int nvColonne) {
        return (nvLigne==ligne && nvColonne!=colonne) || (nvLigne!=ligne && nvColonne==colonne);    //mouvements seulement droits
    }

    //booleen qui recense tous les mouvements possibles du fou (mode 2)
    public static boolean mouvementFou(int ligne, int colonne, int nvLigne, int nvColonne) {
        return Math.abs(nvLigne - ligne) == Math.abs(nvColonne - colonne);      //mouvements seulement en diagonale
    }

    public static void destinationPiece(int[][] plateau, int ligne, int colonne, int piece) {
        Scanner sc = new Scanner(System.in);
        int NvLigne, NvColonne;
        boolean mouvementValide;
        String colo = conversionEnString(colonne);
        String col;

        do {
            mouvementValide = true;
            System.out.println("Où veux-tu aller ?");
            System.out.print("ligne : ");
            NvLigne = Integer.parseInt(sc.nextLine()) - 1;

            System.out.print("colonne : ");
            col = sc.nextLine();

            NvColonne = conversionEnInt(col);

            if (piece == 1 || piece == 7) {
                mouvementValide = mouvementTour(ligne, colonne, NvLigne, NvColonne);
            } else if (piece == 3 || piece == 9) {
                mouvementValide = mouvementFou(ligne, colonne, NvLigne, NvColonne);
            } else if (piece == 2 || piece == 8) {
                mouvementValide = mouvementCavalier(ligne, colonne, NvLigne, NvColonne);
            }

            if (!(caseValide(NvLigne, NvColonne)) || memeCouleur(plateau, NvLigne, NvColonne, piece)) {
                mouvementValide = false;
                System.out.println("La pièce ne peut pas aller là");
            }
        } while (!mouvementValide);

        methodes.AffichageSituation(piece, ligne, colonne, NvLigne, NvColonne, -1, -1);

        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = piece;
    }

    public static boolean hautGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne - 1) && (plateau[ligne - 1][colonne - 1] == 0 && !(memeCouleur(plateau, ligne - 1, colonne - 1, couleur)));
    }

    public static boolean hautDroite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne + 1) && (plateau[ligne - 1][colonne + 1] == 0 && !(memeCouleur(plateau, ligne - 1, colonne + 1, couleur)));
    }

    public static boolean basGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne - 1) && (plateau[ligne + 1][colonne - 1] == 0 && !(memeCouleur(plateau, ligne + 1, colonne - 1, couleur)));
    }

    public static boolean basDroite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne + 1) && (plateau[ligne + 1][colonne + 1] == 0 && !(memeCouleur(plateau, ligne + 1, colonne + 1, couleur)));
    }

    public static boolean haut(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne - 1, colonne) && (plateau[ligne - 1][colonne] == 0 && !(memeCouleur(plateau, ligne - 1, colonne, couleur)));
    }

    public static boolean gauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne, colonne - 1) && (plateau[ligne][colonne - 1] == 0 && !(memeCouleur(plateau, ligne, colonne - 1, couleur)));
    }

    public static boolean droite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne, colonne + 1) && (plateau[ligne][colonne + 1] == 0 && !(memeCouleur(plateau, ligne, colonne + 1, couleur)));
    }

    public static boolean bas(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne) && (plateau[ligne + 1][colonne] == 0 && !(memeCouleur(plateau, ligne + 1, colonne, couleur)));
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
                choix = Integer.parseInt(sc.nextLine());

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
        int direction = methodes.affichageDirections(plateau, ligne, colonne, couleur, joueur);

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
        if(couleur == 1 || couleur == 7) {      //tours
            methodes.Methode1(plateau, ligne, colonne, couleur, hautBas, gaucheDroite, valeurDirection);
        }
        else{       //rois
            int NvLigne = ligne + hautBas;            //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

            methodes.AffichageSituation(couleur, ligne, colonne, NvLigne, NvColonne, 1, valeurDirection);

            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }
    }

    public static void BougeFou(int[][] plateau, int ligne, int colonne, int couleur, char joueur) {
        int direction = methodes.affichageDirectionsFou(plateau, ligne, colonne, couleur, joueur);
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
        if(couleur == 3 || couleur == 9) {
            methodes.Methode1(plateau, ligne, colonne, couleur, hautBas, gaucheDroite, valeurDirection);
        }
        else{
            int NvLigne = ligne + hautBas;            //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

            methodes.AffichageSituation(couleur, ligne, colonne, NvLigne, NvColonne, 1, valeurDirection);

            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = couleur;
        }
    }

    public static void roque(int[][] plateau, int Roi, int ligne, int colonne, boolean tourLoin, boolean tourProche){
        int Tour;
        String couleur;
        int choix = demandeRoque(plateau, ligne, colonne, tourLoin, tourProche);
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
            System.out.println("\uD83C\uDF1F Le roi " + couleur + " a roqué avec la tour (" + (ligne+1) + "," + col4 + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne+2] = Roi;
            plateau[ligne][colonne+3] = 0;
            plateau[ligne][colonne+1] = Tour;
        }
        else if(choix==2){
            System.out.println();
            System.out.println("\uD83C\uDF1F Le roi " + couleur + " a roqué avec la tour (" + (ligne+1) + "," + col5 + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne-2] = Roi;
            plateau[ligne][colonne-4] = 0;
            plateau[ligne][colonne-1] = Tour;
        }
    }
    public static int demandeRoque(int[][] plateau, int ligne, int colonne, boolean TourLoin, boolean TourProche){
        Scanner scanner = new Scanner(System.in);

        boolean petit = PetitRoque(plateau, ligne, colonne, TourProche);
        boolean grand = GrandRoque(plateau, ligne, colonne, TourLoin);
        int choixRoque;

        if(petit && grand){
            do {
                System.out.print("Tapez 1 pour un petit Roque (avec la Tour la plus proche) ou tapez 2 pour un grand Roque (avec la Tour la plus éloignée)  : ");
                choixRoque = scanner.nextInt();
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
    public static boolean PetitRoque(int[][] plateau, int ligne, int colonne, boolean TourProche){
        return TourProche && plateau[ligne][colonne+1]==0 && plateau[ligne][colonne+2]==0;
    }
    public static boolean GrandRoque(int[][] plateau, int ligne, int colonne, boolean TourLoin){
        return TourLoin && plateau[ligne][colonne-1]==0 && plateau[ligne][colonne-2]==0 && plateau[ligne][colonne-3]==0;
    }

    public static void PriseEnPassantD(int[][] plateau, int ligne, int colonne, int piece){
        String couleur;
        String col = conversionEnString(colonne+1);

        if(piece==6) {    //pion jaune
            couleur = "jaune";
        } else {
            couleur = "bleu";
        }

        System.out.println();
        System.out.println("\uD83C\uDF1F le pion " + couleur + " a fais une prise en passant en (" + (ligne + 2) + "," + col + ")");
        plateau[ligne + 1][colonne + 1] = piece;
        plateau[ligne][colonne + 1] = 0;
    }

    public static void PriseEnPassantG(int[][] plateau, int ligne, int colonne, int piece){
        String couleur;
        String col = conversionEnString(colonne-1);

        if(piece==6) {    //pion jaune
            couleur = "jaune";
        } else {
            couleur = "bleu";
        }

        System.out.println();
        System.out.println("\uD83C\uDF1F le pion " + couleur + " a fais une prise en passant en (" + ligne + "," + col + ")");
        plateau[ligne-1][colonne-1] = piece;
        plateau[ligne][colonne-1] = 0;
    }

    public static boolean pepPossibleGauche(int[][] plateau, int ligne, int colonne, int couleur, boolean pep, int ligneAvant, int colonneAvant){
        boolean possible = false;
        int ennemi;

        if(pep) {
            ennemi = plateau[ligneAvant][colonneAvant];

            if (couleur == 12 && ligne == 3) {       //bleus
                if (caseValide(3, colonne - 1) && plateau[3][colonne - 1] == ennemi) {
                    possible = true;
                }
            } else if (couleur == 6 && ligne == 4) {    //jaunes
                if (caseValide(4, colonne - 1) && plateau[4][colonne - 1] == ennemi) {
                    possible = true;
                }
            }
        }
        return possible;
    }

    public static boolean pepPossibleDroite(int[][] plateau, int ligne, int colonne, int couleur, boolean pep, int ligneAvant, int colonneAvant){
        boolean possible = false;
        int ennemi;

        if(pep) {
            ennemi = plateau[ligneAvant][colonneAvant];

            if (couleur == 12 && ligne == 3) {       //bleus
                if (caseValide(3, colonne + 1) && plateau[3][colonne + 1] == ennemi) {
                    possible = true;
                }
            } else if (couleur == 6 && ligne == 4) {    //jaunes
                if (caseValide(4, colonne + 1) && plateau[4][colonne + 1] == ennemi) {
                    possible = true;
                }
            }
        }
        return possible;
    }

    public static void Methode1(int[][] plateau, int ligne, int colonne, int couleur, int hautBas, int gaucheDroite, int direction){
        Scanner sc = new Scanner(System.in);

        System.out.print("Tu veux avancer de combien ? ");
        int choix = Integer.parseInt(sc.nextLine());    //demande de combien l'utilisateur veut se déplacer sans prendre en compte la direction

        boolean empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);
        int NvLigne = ligne+(hautBas*choix);        //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
        int NvColonne = colonne+(gaucheDroite*choix);   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

        while (!(caseValide(NvLigne, NvColonne)) || empechement || memeCouleur(plateau, NvLigne, NvColonne, couleur)) {
            System.out.println("❌ impossible d'avancer jusque là");
            System.out.print("de combien veux-tu avancer ? : ");
            choix = Integer.parseInt(sc.nextLine());

            empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);

            NvLigne = ligne+(hautBas*choix);
            NvColonne = colonne+(gaucheDroite*choix);
        }
        AffichageSituation(couleur, ligne, colonne, NvLigne, NvColonne, choix, direction);
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

    public static void AffichageSituation(int pieceC, int ligne, int colonne, int nvLigne, int nvColonne, int distance, int direction) {
        String piece = piece(pieceC);
        String directions = direction(direction, pieceC);
        String couleur;
        String col = conversionEnString(colonne);
        String nvCol = conversionEnString(nvColonne);

        if (pieceC > 6)
            couleur = "bleue ";
        else
            couleur = "jaune ";

        System.out.println();
        if (distance > 0) {
            System.out.println("\uD83C\uDF1F " + piece + couleur + "avance de " + distance + " cases " + directions + " (" + (ligne + 1) + "," + col + ") -> (" + (nvLigne + 1) + "," + nvCol + ")");
        } else {
            System.out.println("\uD83C\uDF1F " + piece + couleur + "s'est déplacé de la case (" + (ligne + 1) + "," + col + ") vers la case (" + (nvLigne + 1) + "," + nvCol + ")");
        }
    }

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
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                // On calcule la distance et la direction du mouvement
                int distance = Math.max(Math.abs(ligneRoi - ligneEnnemi), Math.abs(colonneRoi - colonneEnnemi));

                int directionLigne = 0;
                if (ligneRoi > ligneEnnemi) directionLigne = 1;
                else if (ligneRoi < ligneEnnemi) directionLigne = -1;

                int directionColonne = 0;
                if (colonneRoi > colonneEnnemi) directionColonne = 1;
                else if (colonneRoi < colonneEnnemi) directionColonne = -1;

                // On vérifie s'il y a un obstacle sur le chemin
                if (empechement(plateau, ligneEnnemi, colonneEnnemi, distance, directionLigne, directionColonne) == false) {
                    possible = true;
                }
            }
        }

        // cas du fou
        else if (piece == 3 || piece == 9) {
            if (mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                int distance = Math.abs(ligneRoi - ligneEnnemi);

                int directionLigne = 0;
                if (ligneRoi > ligneEnnemi) directionLigne = 1;
                else if (ligneRoi < ligneEnnemi) directionLigne = -1;

                int directionColonne = 0;
                if (colonneRoi > colonneEnnemi) directionColonne = 1;
                else if (colonneRoi < colonneEnnemi) directionColonne = -1;

                if (empechement(plateau, ligneEnnemi, colonneEnnemi, distance, directionLigne, directionColonne) == false) {
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
                if (ligneRoi > ligneEnnemi) directionLigne = 1;
                else if (ligneRoi < ligneEnnemi) directionLigne = -1;

                int directionColonne = 0;
                if (colonneRoi > colonneEnnemi) directionColonne = 1;
                else if (colonneRoi < colonneEnnemi) directionColonne = -1;

                if (empechement(plateau, ligneEnnemi, colonneEnnemi, distance, directionLigne, directionColonne) == false) {
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

    public static boolean estEnEchec(int[][] plateau, char couleurRoi) {
        //Couleur du roi
        int Roi;
        if (couleurRoi == 'B') {
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

                if (piece != 0 && !memeCouleur(plateau, l, c, Roi)) {
                    if (peutAttaquer(plateau, l, c, roiL, roiC)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
