import java.util.Scanner;

public class methodes {
    static int mvtTourA1=0;
    static int mvtTourA8=0;
    static int mvtTourH1=0;
    static int mvtTourH8=0;
    static int mvtRoiN=0;
    static int mvtRoiB=0;
    
    public static void main(String[] args) {
        
    }

    public static void coordonnees(int[][] plateau, char joueur, int mode){
        Scanner scanner = new Scanner(System.in);
        char col;
        int colonne;
        int ligne;

        System.out.println("Quelle pièce voulez vous jouer ? ");
        System.out.print("Entrez le numéro de la ligne : ");
        ligne = (scanner.nextInt()) - 1;
        while (ligne < 0 || ligne > 7) {
            System.out.println("Coordonnées impossible");
            System.out.print("Entrez le numéro de la ligne : ");
            ligne = (scanner.nextInt()) - 1;
        }
        System.out.print("Entrez le lettre de la colonne : ");
        col = scanner.next().charAt(0);

        colonne = conversionEnInt(col);

        while (colonne < 0 || colonne > 7) {
            System.out.println("Coordonnées impossible");
            System.out.print("Entrez le numéro de la colonne : ");
            col = scanner.next().charAt(0);
            colonne = conversionEnInt(col);
        }

        appelPiece(plateau, ligne, colonne, joueur, mode);
    }

    public static int conversionEnInt(char colonne){
        int coordonnee = 0;

        if(colonne=='a' || colonne=='A')
            coordonnee = 0;
        else if(colonne=='b' || colonne=='B')
            coordonnee = 1;
        else if(colonne=='c' || colonne=='C')
            coordonnee = 2;
        else if(colonne=='d' || colonne=='D')
            coordonnee = 3;
        else if(colonne=='e' || colonne=='E')
            coordonnee = 4;
        else if(colonne=='f' || colonne=='F')
            coordonnee = 5;
        else if(colonne=='g' || colonne=='G')
            coordonnee = 6;
        else if(colonne=='h' || colonne=='H')
            coordonnee = 7;

        return coordonnee;
    }

    //appel des méthodes en fonction de la pièce jouée
    public static void appelPiece(int[][] plateau, int ligne, int colonne, char joueur, int mode){

        if(couleurJoueur(plateau, ligne, colonne, joueur)) {

            //appel des pions
            if (plateau[ligne][colonne] == 6) {
                pieces.pionJ(plateau, ligne, colonne);
            } else if (plateau[ligne][colonne] == 12) {
                pieces.pionB(plateau, ligne, colonne);

                //appel des tours
            } else if (plateau[ligne][colonne] == 7 || plateau[ligne][colonne] == 1) {
                if (pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger la tour");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.tour(plateau, ligne, colonne, mode, joueur);
                    if(ligne==0  && colonne==0)
                        mvtTourA1++;
                    else if(ligne==0 && colonne==7)
                        mvtTourH1++;
                    else if(ligne==7 && colonne==0)
                        mvtTourA8++;
                    else if(ligne==7 && colonne==7)
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
                    System.out.println("Impossible de bouger le roi");
                    coordonnees(plateau, joueur, mode);
                } else {
                    if(plateau[ligne][colonne]==5) {
                        pieces.roi(plateau, ligne, colonne, mvtTourA1, mvtTourH1, mvtRoiN, joueur);
                        mvtRoiN++;
                    }
                    else{
                        pieces.roi(plateau, ligne, colonne, mvtTourA8, mvtTourH8, mvtRoiB, joueur);
                        mvtRoiB++;
                    }
                }

                //appel des cavaliers
            } else if (plateau[ligne][colonne] == 8 || plateau[ligne][colonne] == 2) {
                if (bougerCavalier(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger le cavalier");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.cavalier(plateau, ligne, colonne, mode);
                }

                //appel des dames
            } else if (plateau[ligne][colonne] == 4 || plateau[ligne][colonne] == 11) {
                if (pieceAutour2(plateau, ligne, colonne) && pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger la dame");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.dame(plateau, ligne, colonne, mode, joueur);
                }
            } else {
                System.out.println("Case vide, veuillez recommencez");
                coordonnees(plateau, joueur, mode);
            }
        }
        else{
            System.out.print("Ce ne sont pas vos pièces ! ");
            coordonnees(plateau, joueur, mode);
        }
    }

    public static boolean TourPasBougee(int compteurTour){
        return compteurTour==0;
    }

    public static boolean couleurJoueur(int[][] plateau, int ligne, int colonne, char joueur){
        boolean valeur=true;

        if(joueur=='B'){
            if(plateau[ligne][colonne]<6){
                valeur = false;
            }
        }
        else {
            if(plateau[ligne][colonne]>6) {
                valeur = false;
            }
        }
        return valeur;
    }

    //booleen qui prend que les cases valide du plateau
    public static boolean caseValide(int ligne, int colonne) {
        return (ligne >= 0 && ligne < 8) && (colonne >= 0 && colonne < 8);
    }

    public static boolean bougerCavalier(int[][] plateau, int ligne, int colonne){
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {2, 2, 1, 1, -2, -2, 1, 1};
        int[] colonnes = {-1, 1, 2, -2, -1, 1, -2, 2};

        for(int i=0; i<8; i++){
            nouvelleL = ligne+lignes[i];
            nouvelleC = colonne+colonnes[i];

            if(caseValide(nouvelleL, nouvelleC)==true){
                if(plateau[nouvelleL][nouvelleC]==0){
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

    public static boolean mouvementCavalier(int ligne, int colonne, int nvLigne, int nvColonne) {
        return (Math.abs(nvLigne-ligne)==2 && Math.abs(nvColonne-colonne)==1) || (Math.abs(nvLigne-ligne)==1 && Math.abs(nvColonne-colonne)==2);
    }

    //méthode qui vérifie que les cases+1 du haut, du bas, de gauche et de droite
    //sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour(int[][] plateau, int ligne, int colonne){
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {0, 0, -1, 1};
        int[] colonnes = {-1, 1, 0, 0};

        for(int i=0; i<4; i++){
            nouvelleL = ligne+lignes[i];
            nouvelleC = colonne+colonnes[i];

            if(caseValide(nouvelleL, nouvelleC)==true){
                if(plateau[nouvelleL][nouvelleC]==0){
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
    public static boolean pieceAutour2(int[][] plateau, int ligne, int colonne){
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;

        int[] lignes = {-1, 1, -1, 1};
        int[] colonnes = {-1, 1, 1, -1};

        for(int i=0; i<4; i++){
            nouvelleL = ligne+lignes[i];
            nouvelleC = colonne+colonnes[i];

            if(caseValide(nouvelleL, nouvelleC)==true){
                if(plateau[nouvelleL][nouvelleC]==0){
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

    //affichage du plateau
    public static void plateau(int[][] plateau) {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                plateau[i][j] = 0; //case vide
                plateau[1][j] = 6; //Pion Noir
                plateau[6][j] = 12; //Pion Blanc
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
        String RESET  = "\u001B[0m";
        String BLEU   = "\u001B[34m";
        String JAUNE  = "\u001B[33m";

        for (int i = 0; i < plateau.length; i++) {
            System.out.print(i+1 + " ");
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 1){
                    System.out.print("|" + JAUNE + "   Tour   " + RESET + "|");
                }
                else if (plateau[i][j] == 2){
                    System.out.print("|" + JAUNE + " Cavalier " + RESET + "|");
                }
                else if (plateau[i][j] == 3){
                    System.out.print("|" + JAUNE + "   Fou    " + RESET + "|");
                }
                else if (plateau[i][j] == 4){
                    System.out.print("|" + JAUNE + "   Dame   " + RESET + "|");
                }
                else if (plateau[i][j] == 5){
                    System.out.print("|" + JAUNE + "   Roi    " + RESET + "|");
                }
                else if (plateau[i][j] == 6){
                    System.out.print("|" + JAUNE + "   Pion   " + RESET + "|");
                }
                else if(plateau[i][j] == 7){
                    System.out.print("|" + BLEU + "   Tour   " + RESET + "|");
                }
                else if (plateau[i][j] == 8){
                    System.out.print("|" + BLEU + " Cavalier " + RESET + "|");
                }
                else if (plateau[i][j] == 9){
                    System.out.print("|" + BLEU + "   Fou    " + RESET + "|");
                }
                else if (plateau[i][j] == 10){
                    System.out.print("|" + BLEU + "   Roi    " + RESET + "|");
                }
                else if (plateau[i][j] == 11){
                    System.out.print("|" + BLEU + "   Dame   " + RESET + "|");
                }
                else if (plateau[i][j] == 12){
                    System.out.print("|" + BLEU + "   Pion   " + RESET + "|");
                }
                else {
                    System.out.print("|          |");
                }
            }
            System.out.println();
        }
        System.out.println("        A           B           C           D           E           F           G           H");
        System.out.println();
    }

    public static void remplir2(int[][] plateau) {
        String RESET  = "\u001B[0m";
        String BLEU   = "\u001B[34m";
        String JAUNE  = "\u001B[33m";

        for (int i = 0; i < plateau.length; i++) {
            System.out.print(8-i + " ");

            for (int j = 0; j < plateau[i].length; j++) {
                int piece = plateau[7-i][7-j];

                if (piece == 1){
                    System.out.print("|" + JAUNE + "   Tour   " + RESET + "|");
                }
                else if (piece == 2){
                    System.out.print("|" + JAUNE + " Cavalier " + RESET + "|");
                }
                else if (piece == 3){
                    System.out.print("|" + JAUNE + "   Fou    " + RESET + "|");
                }
                else if (piece == 4){
                    System.out.print("|" + JAUNE + "   Dame   " + RESET + "|");
                }
                else if (piece == 5){
                    System.out.print("|" + JAUNE + "   Roi    " + RESET + "|");
                }
                else if (piece == 6){
                    System.out.print("|" + JAUNE + "   Pion   " + RESET + "|");
                }
                else if(piece == 7){
                    System.out.print("|" + BLEU + "   Tour   " + RESET + "|");
                }
                else if (piece == 8){
                    System.out.print("|" + BLEU + " Cavalier " + RESET + "|");
                }
                else if (piece == 9){
                    System.out.print("|" + BLEU + "   Fou    " + RESET + "|");
                }
                else if (piece == 10){
                    System.out.print("|" + BLEU + "   Roi    " + RESET + "|");
                }
                else if (piece == 11){
                    System.out.print("|" + BLEU + "   Dame   " + RESET + "|");
                }
                else if (piece == 12){
                    System.out.print("|" + BLEU + "   Pion   " + RESET + "|");
                }
                else {
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
    public static boolean empechement (int[][] plateau, int ligne, int colonne, int distance, int dLigne, int dColonne){
        for (int i = 1; i < distance; i++) {
            int l = ligne + i * dLigne;
            int c = colonne + i * dColonne;

            if (caseValide(l, c) && plateau[l][c] != 0){
                return true;
            }
        }
        return false;
    }

    //méthode qui vérifie si le mouvement est possible en vérifiant
    //s'il y a des pièces empêchant un mouvement de plusieurs
    //lignes (sah g la flemme de bien expliquer sorry)
    public static boolean empechementD (int[][] plateau, int ligne, int colonne, int distance, int dLigne, int dColonne){
        for (int i = 1; i < distance; i++) {
            int l = ligne + i * dLigne;
            int c = colonne + i * dColonne;

            if (caseValide(l, c) && plateau[l][c] != 0){
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

    public static int affichageDirections(boolean haut, boolean gauche, boolean droite, boolean bas, char joueur){
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        if(haut)
            possibilites++;
        if(bas)
            possibilites++;
        if(gauche)
            possibilites++;
        if(droite)
            possibilites++;

        if(possibilites>1) {
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if(joueur=='B') {
                    if (haut)
                        System.out.print("1 pour aller en haut ");
                    if (gauche)
                        System.out.print("2 pour aller à gauche ");
                    if (droite)
                        System.out.print("3 pour aller à droite ");
                    if (bas)
                        System.out.print("4 pour aller en bas ");
                }
                else{
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
                    System.out.println("Direction impossible, recommence.");
                }
            }
        }
        return choix;
    }

    public static int affichageChoixPion(boolean diagoDroite, boolean diagoGauche, boolean avanceUn) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        if (diagoDroite){
            if(diagoGauche){
                if(avanceUn){           //diagoDroite+diagoGauche+avanceUn
                    System.out.print("1 prendre la pièce en diagonale gauche, 2 prendre la pièce en diagonale droite, 3 avancer : ");
                    choix = Integer.parseInt(sc.nextLine());
                } else{                 //diagoDroite+diagoGauche
                    System.out.print("1 prendre la pièce en diagonale gauche, 2 prendre la pièce en diagonale droite : ");
                    choix = Integer.parseInt(sc.nextLine());
                }
            } else{
                if(avanceUn){          //diagoDroite+avanceUn
                    System.out.print("2 prendre la pièce en diagonale droite, 3 avancer : ");
                    choix = Integer.parseInt(sc.nextLine());
                }
            }
        } else{
            if(diagoGauche){
                if(avanceUn){       //diagoGauche+avanceUn
                    System.out.print("1 prendre la pièce en diagonale gauche, 3 avancer : ");
                    choix = Integer.parseInt(sc.nextLine());
                }
            }
        }

        return choix;
    }












    public static boolean mouvementTour(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        boolean valeur=true;

        if(nvLigne!=ligne){
            if(nvColonne!=colonne){
                valeur=false;
            }
        }
        else if(nvColonne!=colonne){
            if(nvLigne!=ligne){
                valeur=false;
            }
        }
        else{
            valeur=false;
        }
        return valeur;
    }

    public static boolean mouvementFou(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        return Math.abs(nvLigne - ligne) == Math.abs(nvColonne - colonne);
    }

    public static void destinationPiece(int[][] plateau, int ligne, int colonne, int piece){
        Scanner sc = new Scanner(System.in);
        int NvLigne, NvColonne;
        boolean mouvementValide;
        char col;

        do {
            mouvementValide=true;
            System.out.println("Où veux-tu aller ?");
            System.out.print("Entrez le numéro de la ligne : ");
            NvLigne = Integer.parseInt(sc.nextLine())-1;

            System.out.print("Entrez le lettre de la colonne : ");
            col = sc.next().charAt(0);

            NvColonne = conversionEnInt(col);

            if(piece==1 || piece==2){
                mouvementValide = mouvementTour(plateau, ligne, colonne, NvLigne, NvColonne);
            }
            else if(piece==3 || piece==9){
                mouvementValide = mouvementFou(plateau, ligne, colonne, NvLigne, NvColonne);
            }

            if((NvColonne<0 || NvColonne>7) || memeCouleur(plateau, NvLigne, NvColonne, piece)){
                mouvementValide = false;
                System.out.println("La pièce ne peut pas aller là");
            }
        } while (!mouvementValide);

        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = piece;
    }

    public static boolean hautGauche(int[][] plateau, int ligne, int colonne, int couleur){
        return caseValide(ligne-1, colonne-1) && (plateau[ligne-1][colonne-1] == 0 && !(memeCouleur(plateau, ligne-1, colonne-1, couleur)));
    }
    public static boolean hautDroite(int[][] plateau, int ligne, int colonne, int couleur){
        return caseValide(ligne-1, colonne+1) && (plateau[ligne-1][colonne+1] == 0 && !(memeCouleur(plateau, ligne-1, colonne+1, couleur)));
    }
    public static boolean basGauche(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne + 1, colonne - 1) && (plateau[ligne + 1][colonne - 1] == 0 && !(memeCouleur(plateau, ligne + 1, colonne - 1, couleur)));
    }
    public static boolean basDroite(int[][] plateau, int ligne, int colonne, int couleur) {
        return caseValide(ligne+1, colonne+1) && (plateau[ligne+1][colonne+1] == 0 && !(memeCouleur(plateau, ligne+1, colonne+1, couleur)));
    }
    public static boolean haut(int[][] plateau, int ligne, int colonne, int couleur){
        return caseValide(ligne-1, colonne) && (plateau[ligne-1][colonne] == 0 && !(memeCouleur(plateau, ligne-1, colonne, couleur)));
    }
    public static boolean gauche(int[][] plateau, int ligne, int colonne, int couleur){
        return caseValide(ligne, colonne-1) && (plateau[ligne][colonne-1] == 0 && !(memeCouleur(plateau, ligne, colonne-1, couleur)));
    }
    public static boolean droite(int[][] plateau, int ligne, int colonne, int couleur){
        return caseValide(ligne, colonne+1) && (plateau[ligne][colonne+1] == 0 && !(memeCouleur(plateau, ligne, colonne+1, couleur)));
    }
    public static boolean bas(int[][] plateau, int ligne, int colonne, int couleur){
        return caseValide(ligne+1, colonne) && (plateau[ligne+1][colonne] == 0 && !(memeCouleur(plateau, ligne+1, colonne, couleur)));
    }

    public static int affichageDirectionsFou(boolean hautGauche, boolean hautDroite, boolean basGauche, boolean basDroite, char joueur){
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        if(hautGauche)
            possibilites++;
        if(hautDroite)
            possibilites++;
        if(basGauche)
            possibilites++;
        if(basDroite)
            possibilites++;

        if(possibilites>1) {
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if(joueur=='B') {
                    if (hautGauche)
                        System.out.print("1 pour aller en haut à gauche ");
                    if (hautDroite)
                        System.out.print("2 pour aller en haut à droite ");
                    if (basGauche)
                        System.out.print("3 pour aller en bas à gauche ");
                    if (basDroite)
                        System.out.print("4 pour aller en bas à droite ");
                }
                else{
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
                    System.out.println("Direction impossible, recommence.");
                }
            }
        }
        return choix;
    }

    public static void roque(int[][] plateau, int Roi, int ligne, int colonne, boolean tourLoin, boolean tourProche){
        int Tour;
        int choix = demandeRoque(plateau, ligne, colonne, tourLoin, tourProche);

        if(Roi==5)
            Tour = 1;
        else
            Tour = 7;

        if(choix==1){
            System.out.println();
            System.out.println("Le roi a roqué avec la tour (" + (ligne+1) + "," + (colonne+4) + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne+2] = Roi;
            plateau[ligne][colonne+3] = 0;
            plateau[ligne][colonne+1] = Tour;
        }
        else if(choix==2){
            System.out.println();
            System.out.println("Le roi a roqué avec la tour (" + (ligne+1) + "," + (5-colonne) + ")");
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
        int choixRoque = 0;

        if(petit && grand){
            System.out.print("Tapez 1 pour un petit Roque (avec la Tour la plus proche) ou tapez 2 pour un grand Roque (avec la Tour la plus éloignée)  : ");
            choixRoque = scanner.nextInt();
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

    public static void Methode1(int[][] plateau, int ligne, int colonne, int couleur, int hautBas, int gaucheDroite, int direction){
        Scanner sc = new Scanner(System.in);

        System.out.print("Tu veux avancer de combien ? ");
        int choix = Integer.parseInt(sc.nextLine());    //demande de combien l'utilisateur veut se déplacer sans prendre en compte la direction

        boolean empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);
        int NvLigne = ligne+(hautBas*choix);        //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
        int NvColonne = colonne+(gaucheDroite*choix);   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

        while (!(caseValide(NvLigne, NvColonne)) || empechement || memeCouleur(plateau, NvLigne, NvColonne, couleur)) {
            System.out.println("impossible d'avancer jusque là");
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
        else if(piece == 4 || piece == 10)
            pieces = "Le roi ";
        else if(piece == 5 || piece == 11)
            pieces = "La dame ";
        else if(piece == 6 || piece == 12)
            pieces = "Le pion ";

        return pieces;
    }
    public static String direction(int direction, int piece){
        String directions="vide";

        if(piece<=6) {
            switch (direction) {
                case 1:
                    directions = "le haut";
                    break;
                case 2:
                    directions = "la gauche";
                    break;
                case 3:
                    directions = "la droite";
                    break;
                case 4:
                    directions = "le bas";
                    break;
                case 5:
                    directions = "le haut gauche en diagonale";
                    break;
                case 6:
                    directions = "le haut droit en diagonale";
                    break;
                case 7:
                    directions = "le bas gauche en diagonale";
                    break;
                case 8:
                    directions = "Le bas droit en diagonale";
                    break;
            }
        }
        else{
            switch (direction) {
                case 1:
                    directions = "le bas";
                    break;
                case 2:
                    directions = "la droite";
                    break;
                case 3:
                    directions = "la gauche";
                    break;
                case 4:
                    directions = "le haut";
                    break;
                case 5:
                    directions = "le bas droit en diagonale";
                    break;
                case 6:
                    directions = "le bas gauche en diagonale";
                    break;
                case 7:
                    directions = "le haut droit en diagonale";
                    break;
                case 8:
                    directions = "Le haut gauche en diagonale";
                    break;
            }
        }
        return directions;
    }

    public static void AffichageSituation(int pieceC, int ligne, int colonne, int nvLigne, int nvColonne, int distance, int direction){
        String piece = piece(pieceC);
        String directions = direction(direction, pieceC);
        String couleur;

        if(pieceC<=6)
            couleur = "bleue ";
        else
            couleur = "jaune ";

        System.out.println();
        System.out.println(piece+ couleur + "avance de " + distance + " cases vers " + directions + " (" + (ligne+1) + "," + (colonne+1) + ")" + " -> " + "(" + (nvLigne+1) + "," + (nvColonne+1) + ")");
    }

    public static boolean peutAttaquer(int[][] plateau, int ligneEnnemi, int colonneEnnemi, int ligneRoi, int colonneRoi) {
        int piece = plateau[ligneEnnemi][colonneEnnemi];
        boolean possible = false;

        // --- CAS DU CAVALIER (2 ou 8) ---
        if (piece == 2 || piece == 8) {
            if (mouvementCavalier(ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
                possible = true;
            }
        }

        // --- CAS DE LA TOUR (1 ou 7) ---
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

        // --- CAS DU FOU (3 ou 9) ---
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

        // --- CAS DE LA DAME (4 ou 11) ---
        else if (piece == 4 || piece == 11) {
            // La dame combine les mouvements de la Tour et du Fou
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

        // --- CAS DU PION (6 ou 12) ---
        else if (piece == 6) { // Pion Jaune (descend vers les lignes plus hautes)
            // Le pion attaque uniquement en diagonale (1 ligne plus bas, 1 colonne à côté)
            if (ligneRoi == ligneEnnemi + 1 && (colonneRoi == colonneEnnemi + 1 || colonneRoi == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        else if (piece == 12) { // Pion Bleu (monte vers les lignes plus basses)
            if (ligneRoi == ligneEnnemi - 1 && (colonneRoi == colonneEnnemi + 1 || colonneRoi == colonneEnnemi - 1)) {
                possible = true;
            }
        }

        return possible;
    }

    public static boolean estEnEchec(int[][] plateau, char couleurRoi) {
        //Déterminer la couleur du roi qu'on cherche
        int idRoi;
        if (couleurRoi == 'B') {
            idRoi = 10;
        } else {
            idRoi = 5;
        }

        // 2. Trouver la position du Roi sur le plateau
        int roiL = -1;
        int roiC = -1;
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                if (plateau[l][c] == idRoi) {
                    roiL = l;
                    roiC = c;
                }
            }
        }

        // 3. Parcourir tout le plateau pour trouver les pièces ennemies
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                int pieceEnnemie = plateau[l][c];

                if (pieceEnnemie != 0) {
                    //Si la pièce n'est pas de la même couleur que le Roi, c'est un ennemi
                    if (!memeCouleur(plateau, l, c, idRoi)) {
                        if (peutAttaquer(plateau, l, c, roiL, roiC)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
