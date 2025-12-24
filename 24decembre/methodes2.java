import java.util.Scanner;

public class methodes {

    public static void main(String[] args) {

    }

    public static void coordonnees(int[][] plateau, char joueur, int mode){
        Scanner scanner = new Scanner(System.in);
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
        System.out.print("Entrez le numéro de la colonne : ");
        colonne = (scanner.nextInt()) - 1;
        while (colonne < 0 || colonne > 7) {
            System.out.println("Coordonnées impossible");
            System.out.print("Entrez le numéro de la colonne : ");
            colonne = (scanner.nextInt()) - 1;
        }
        appelPiece(plateau, ligne, colonne, joueur, mode);
    }

    //appel des méthodes en fonction de la pièce jouée
    public static void appelPiece(int[][] plateau, int ligne, int colonne, char joueur, int mode){
        // 1. On crée une copie du plateau avant le mouvement
        int[][] copiePlateau = new int[8][8];
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                copiePlateau[i][j] = plateau[i][j];
            }
        }

        if(couleurJoueur(plateau, ligne, colonne, joueur)) {
            if (plateau[ligne][colonne] == 6) {
                pieces.pionJ(plateau, ligne, colonne);
            } else if (plateau[ligne][colonne] == 12) {
                pieces.pionB(plateau, ligne, colonne);
            } else if (plateau[ligne][colonne] == 7 || plateau[ligne][colonne] == 1) {
                if (pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger cette pièce");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.tour(plateau, ligne, colonne, mode);
                }
            } else if (plateau[ligne][colonne] == 9 || plateau[ligne][colonne] == 3) {
                if (pieceAutour2(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger cette pièce");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.fou(plateau, ligne, colonne, mode);
                }
            } else if (plateau[ligne][colonne] == 10 || plateau[ligne][colonne] == 5) {
                if (pieceAutour2(plateau, ligne, colonne) && pieceAutour(plateau, ligne, colonne)) {
                    System.out.println("Impossible de bouger cette pièce");
                    coordonnees(plateau, joueur, mode);
                } else {
                    pieces.roi(plateau, ligne, colonne);
                }
            } else if (plateau[ligne][colonne] == 8 || plateau[ligne][colonne] == 2) {
                pieces.cavalier(plateau, ligne, colonne, mode);
            } else if (plateau[ligne][colonne] == 4 || plateau[ligne][colonne] == 11) {
                pieces.dame(plateau, ligne, colonne, mode);
            } else {
                System.out.println("Case vide, veuillez recommencez");
                coordonnees(plateau, joueur, mode);
            }
            if (estEnEchec(plateau, joueur)) {
                System.out.println("MOUVEMENT INTERDIT : Votre roi est en échec !");

                // 2. RETOUR EN ARRIÈRE : On remet le plateau comme il était
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        plateau[i][j] = copiePlateau[i][j];
                    }
                }
                // 3. On redemande au joueur de choisir une autre pièce
                coordonnees(plateau, joueur, mode);
            }
        }
        else{
            System.out.print("Ce ne sont pas vos pièces ! ");
            coordonnees(plateau, joueur, mode);
        }
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

    public static boolean mouvementCavalier(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        boolean valeur=true;

        if(nvLigne-ligne==2 || nvLigne-ligne==-2){
            if(nvColonne-colonne!=1 && nvColonne-colonne!=-1){
                valeur=false;
            }
        }
        else if(nvLigne-ligne==1 || nvLigne-ligne==-1){
            if(nvColonne-colonne!=2 && nvColonne-colonne!=-2){
                valeur=false;
            }
        }
        else{
            valeur=false;
        }
        return valeur;
    }

    public static boolean ligneCavalier(int[][] plateau, int ligne, int nvLigne){
        boolean valeur=false;

        if(nvLigne-ligne==2 || nvLigne-ligne==-2 || nvLigne-ligne==1 || nvLigne-ligne==-1){
            valeur=true;
        }
        return valeur;
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

        System.out.println("        1           2           3           4           5           6           7           8");
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
        System.out.println();
    }

    public static void remplir2(int[][] plateau) {
        String RESET  = "\u001B[0m";
        String BLEU   = "\u001B[34m";
        String JAUNE  = "\u001B[33m";

        System.out.println("        8           7           6           5           4           3           2           1");
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

    //affichage des directions en fonctions de la position de la pièce actuelle
    public static int affichageDirections(boolean haut, boolean gauche, boolean droite, boolean bas){
        Scanner sc = new Scanner(System.in);
        int direction=0;

        if (haut){
            if (gauche){
                if (droite){
                    if (bas){   //haut+gauche+droite+bas
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //haut+gauche+droite
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (bas){   //haut+gauche+bas
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{      //haut+gauche
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            } else {
                if (droite){
                    if (bas){   //haut+droite+bas
                        System.out.print("1 pour aller en haut, 3 pour aller à droite, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //haut+droite
                        System.out.print("1 pour aller en haut, 3 pour aller à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (bas){   //haut+bas
                        System.out.print("1 pour aller en haut, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        } else {
            if (gauche){
                if (droite){
                    if (bas){   //gauche+droite+bas
                        System.out.print("2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //gauche+droite
                        System.out.print("2 pour aller à gauche, 3 pour aller à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (bas){   //gauche+bas
                        System.out.print("2 pour aller à gauche, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            } else{
                if (droite){
                    if (bas){   //droite+bas
                        System.out.print("3 pour aller à droite, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        }
        return direction;
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

        do {
            mouvementValide=true;
            System.out.println("Où veux-tu aller ?");
            System.out.print("Entrez le numéro de la ligne : ");
            NvLigne = Integer.parseInt(sc.nextLine())-1;

            System.out.print("Entrez le numéro de la colonne : ");
            NvColonne = Integer.parseInt(sc.nextLine())-1;

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

    //affichage des directions en fonctions de la position de la pièce actuelle
    public static int affichageDirectionsFou(boolean hautGauche, boolean hautDroite, boolean basGauche, boolean basDroite){
        Scanner sc = new Scanner(System.in);
        int direction=0;

        if (hautGauche){
            if (hautDroite){
                if (basGauche){
                    if (basDroite){   //hautGauche+hautDroite+basGauche+basDroite
                        System.out.print("1 pour aller en haut à gauche, 2 pour aller en haut à droite, 3 pour aller en bas à gauche, 4 pour aller en bas à droite :");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //hautGauche+hautDroite+basGauche
                        System.out.print("1 pour aller en haut à gauche, 2 pour aller en haut à droite, 3 pour aller en bas à gauche : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (basDroite){   //hautGauche+hautDroite+basDroite
                        System.out.print("1 pour aller en haut à gauche, 2 pour aller en haut à droite, 4 pour aller en bas à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{      //hautGauche+hautDroite
                        System.out.print("1 pour aller en haut à gauche, 2 pour aller en haut à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            } else {
                if (basGauche){
                    if (basDroite){   //hautGauche+basGauche+basDroite
                        System.out.print("1 pour aller en haut à gauche, 3 pour aller en bas à gauche, 4 pour aller en bas à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //hautGauche+basGauche
                        System.out.print("1 pour aller en haut à gauche, 3 pour aller en bas à gauche : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (basDroite){   //hautGauche+basDroite
                        System.out.print("1 pour aller en haut à gauche, 4 pour aller en bas à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        } else {
            if (hautDroite){
                if (basGauche){
                    if (basDroite){   //hautDroite+basGauche+basDroite
                        System.out.print("2 pour aller en haut à droite, 3 pour aller en bas à gauche, 4 pour aller en bas à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //hautDroite+basGauche
                        System.out.print("2 pour aller en haut à droite, 3 pour aller en bas à gauche : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (basDroite){   //hautDroite+basDroite
                        System.out.print("2 pour aller en haut à droite, 4 pour aller en bas à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            } else{
                if (basGauche){
                    if (basDroite){   //basGauche+basDroite
                        System.out.print("3 pour aller en bas à gauche, 4 pour aller en bas à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        }
        return direction;
    }

    public static void Methode1(int[][] plateau, int ligne, int colonne, int couleur, int hautBas, int gaucheDroite){
        Scanner sc = new Scanner(System.in);
        System.out.print("Tu veux avancer de combien ? ");
        int choix = Integer.parseInt(sc.nextLine());    //demande de combien l'utilisateur veut se déplacer sans prendre en compte la direction

        boolean empechement=false;
        int NvLigne = ligne+(hautBas*choix);        //change en fonction de si l'utilisateur veut monter/descendre ou aucun des 2
        int NvColonne = colonne+(gaucheDroite*choix);   //change en fonction de si l'utilisateur veut aller à gauche/droite ou aucun des 2

        if(couleur==1 || couleur==7){
            empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);
        }
        /*else if(piece==3 || piece==9){
            mouvementValide = mouvementFou(plateau, ligne, colonne, NvLigne, NvColonne);
        }*/

        while (!(caseValide(NvLigne, NvColonne)) || empechement || memeCouleur(plateau, NvLigne, NvColonne, couleur)) {
            System.out.println("impossible d'avancer jusque là");
            System.out.print("de combien veux-tu avancer ? : ");
            choix = Integer.parseInt(sc.nextLine());

            NvLigne = ligne+(hautBas*choix);
            NvColonne = colonne+(gaucheDroite*choix);
        }
        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = couleur;
    }


    public static boolean peutAttaquer(int[][] plateau, int ligneEnnemi, int colonneEnnemi, int ligneRoi, int colonneRoi) {
        int piece = plateau[ligneEnnemi][colonneEnnemi];
        boolean possible = false;

        // --- CAS DU CAVALIER (2 ou 8) ---
        if (piece == 2 || piece == 8) {
            if (mouvementCavalier(plateau, ligneEnnemi, colonneEnnemi, ligneRoi, colonneRoi)) {
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
