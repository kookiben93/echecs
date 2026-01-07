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

    //fonction qui affiche le menu du début et renvoie le choix du joueur
    public static int debut() {
        int choix;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Jouer au jeu d'échecs");
        System.out.println("2. Connaître les régles");
        System.out.println("3. Quitter le jeu");
        String choixS = scanner.nextLine();     //prend la valeur sous forme de String pour éviter les erreurs

        choix = methodes.conversionEnInt(choixS);   //la convertit en int

        return choix;   //renvoie le choix du joueur
    }

    //fonction qui renvoie les règles du jeu d'échecs et raffiche le menu puis renvoie la valeur du nouveau choix
    public static int regles() {
        int choix;

        //affiche les règles du jeu
        System.out.println("=======================================================================================");
        System.out.println("                             ♟ RÈGLES DU JEU D'ÉCHECS ♟\n");
        System.out.println("=======================================================================================");

        System.out.println("\uD83C\uDFAF Objectif du jeu\n" +
                "Le but est de mettre le roi adverse en échec et mat :\n" +
                "le roi est attaqué et ne peut plus s’échapper, ni être protégé, ni capturer la pièce qui l’attaque.\n");
        System.out.println();
        System.out.println("\uD83E\uDDE9 Mouvement des pièces\n");
        System.out.println("♙ Pion\n" + "-avance d’une case vers l’avant\n" + "-peut avancer de deux cases depuis sa position de départ\n" +
                "-capture en diagonale\n" + "-ne peut pas reculer\n" +
                "-peut faire prise en passant : si un pion adverse avance de deux cases et se retrouve à côté, vous pouvez le capturer au tour suivant\n" +
                "-peut se faire promouvoir en dame , tour, fou ou cavalier sur la dernière ligne\n");
        System.out.println();
        System.out.println("♖ Tour\n" + "-se déplace horizontalement ou verticalement, sur n’importe quelle distance\n" +
                "-ne peut pas sauter par-dessus les pièces\n");
        System.out.println();
        System.out.println("♘ Cavalier\n" + "-se déplace en forme de L : 2 cases dans une direction + 1 case perpendiculaire\n" +
                "-seul à pouvoir sauter par-dessus les pièces\n");
        System.out.println();
        System.out.println("♗ Fou\n" + "-se déplace en diagonale, sur n’importe quelle distance\n" + "-reste toute la partie sur la même couleur\n" +
                "-ne peut pas sauter par-dessus les pièces\n");
        System.out.println();
        System.out.println("♕ Dame\n" + "-combine tour + fou : horizontal / vertical / diagonal\n");
        System.out.println();
        System.out.println("♔ Roi\n" + "-avance d’une case dans n’importe quelle direction\n" + "-ne peut jamais se mettre en échec\n" +
                "-peut roquer avec une tour si ni le roi, ni la tour n’ont bougé et qu'il n'y a aucune pièce entre eux\n" +
                "-le roi ne traverse pas une case attaquée\n" + "-le roi n’est pas en échec avant, pendant, ou après\n");
        System.out.println();
        System.out.println("\uD83C\uDFC1 Fin de partie\n" + "-La partie se termine par :\n" + "-échec et mat\n" + "-abandon\n" +
                "-pat (nul)\n" + "-accord de nulle\n" + "-plus de matériel suffisant pour mater (ex : roi contre roi)");
        System.out.println("=======================================================================================");
        System.out.println();

        choix = methodes.debut();   //redemande le choix en affichant le menu

        return choix;               //renvoie le nouveau choix
    }

    //Fonction demandant la valeur de la ligne choisie en String et la retourne en int
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

    //Fonction demandant la valeur de la colonne choisie en String et la retourne en int
    public static int coordonneeColonne() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("colonne : ");
        String col = scanner.nextLine();        //enregistre la valeur de la colonne en String

        int colonne = conversionColEnInt(col);      //appel de méthode pour transformer la valeur String en int

        while ((colonne < 0 || colonne > 7) && colonne != 10) {       //on force de la saisie tant que la valeur est supérieure aux indices possibles du tableau
            System.out.println("❌ Coordonnées impossible");
            System.out.print("colonne : ");
            col = scanner.nextLine();
            colonne = conversionColEnInt(col);
        }
        return colonne;
    }

    //Méthode appelant "coordoonneeLigne" et "coordoonneeColonne" pour sélectionner la pièce que le joueur veut jouer
    public static void coordonnees(int[][] plateau, char joueur, int mode) {
        int colonne=10;
        int ligne;

        System.out.println("Vous pouvez changez de coordonnées en écrivant \"x\" dans \"colonne\" ou abandonner en écrivant \"abandon\" dans \"ligne\"");
        do{
            System.out.println("Quelle pièce voulez vous jouer ? ");
            ligne = coordonneeLigne();

            if(ligne == 10)                                 //Si ligne = 10, appel de la méthode d'abandon
                Main.abandon(plateau, joueur, mode);
            else
                colonne = coordonneeColonne();              //Sinon, appel de la méthode pour choisir la colonne

        } while (ligne!=10 && colonne==10);        //redemande au joueur la pièce qu'il veut jouer tant que ligne est différent de 10 (10=abandon) et colonne de 10 (recommencer la saisie de ligne)

        if(ligne!=10) {             //Si le joueur ne voulait pas abandonner
            appelPiece(plateau, ligne, colonne, joueur, mode);      //appel de la pièce sélectionnée
        }
    }

    //fonction qui prend un pion, ses coordonnées et de nouvelles coordonnées et renvoie si ce pion peut aller dans ces nouvelles coordonnées
    public static boolean mouvementPionRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne, int piece) {
        int sens;
        int ligneDepart;

        if (piece == 12) {      //pion bleu
            sens = -1;          //monte
            ligneDepart = 6;
        } else {                //pion jaune
            sens = 1;           //descend
            ligneDepart = 1;
        }

        if (!caseValide(nvLigne, nvColonne)) {      //si les nouvelles coordonnées ne sont pas dans le plateau c'est faux
            return false;
        }

        int distL = (nvLigne - ligne) * sens;           //distance entre la ligne et la nouvelle ligne
        int distC = Math.abs(nvColonne - colonne);      //distance entre la colonne et la nouvelle colonne positive

        //avancer de 1 case
        if (distC == 0 && distL == 1 && plateau[nvLigne][nvColonne] == 0) {     //si le pion reste sur la même colonne + il avance que de 1 + la case devant lui est vide c'est vrai
            return true;
        }

        // Avancer de 2 cases
        else if (distC == 0 && distL == 2 && ligne == ligneDepart && plateau[ligne + sens][colonne] == 0 && plateau[nvLigne][nvColonne] == 0) { //si le pion reste sur la même colonne
            return true;                                                                                                                        // + il avance de 2 + il n'a pas bougé
        }                                                                                                                                       // + les 2 cases devant lui sont vide
        // c'est vrai
        // Manger en diagonale
        else if (distC == 1 && distL == 1 && !memeCouleurEtVide(plateau, nvLigne, nvColonne, piece)) {    // si le pion va sur 1 colonne avant ou 1 après + il avance de 1
            return true;                                                                                  // + la case ou il va n'est pas vide et est une pièce ennemi c'est vrai
        }
        return false; //si aucun de ces 3 mouvements n'est bon alors ce n'est pas un mouvememnt valide pour le pion
    }

    //fonction booléenne qui retourne vrai si le mouvement prise en passant est possible dans la situation où il est employé (pour le mode moyen et le mode robot)
    public static boolean pepRobot(int ligne, int colonne, int nvLigne, int nvColonne, int pion){
        boolean mouvementValide = false;        //mouvementValide initialisé à faux

        if(pepPossibleGauche(ligne, colonne, pion)) {      //si la prise en passant vers la gauche est possible

            if (pion == 12 && nvLigne == ligne-1 && nvColonne == colonne-1) {       //le mouvement ligne-1 (haut) et colonne-1 (gauche) est possible pour les pions bleus
                mouvementValide = true;
            }
            else if (pion == 6 && nvLigne == ligne+1 && nvColonne == colonne-1){      //le mouvement ligne+1 (bas) et colonne-1 (gauche) est possible pour les pions jaunes
                mouvementValide = true;
            }

        } else if(pepPossibleDroite(ligne, colonne, pion)){       //si la prise en passant vers la droite est possible

            if (pion == 12 && nvLigne == ligne-1 && nvColonne == colonne+1) {   //le mouvement ligne-1 (haut) et colonne+1 (droite) est possible pour les pions bleus
                mouvementValide = true;
            }
            else if (pion == 6 && nvLigne == ligne+1 && nvColonne == colonne+1){      //le mouvement ligne-1 (haut) et colonne+1 (droite) est possible pour les pions bleus
                mouvementValide = true;
            }
        }
        return mouvementValide;
    }

    //fonction qui prend en paramètre les coordonnées du roi et de nouvelles coordonnées et renvoie si il peut y aller
    public static boolean mouvementRoiRobot(int ligne, int colonne, int nvLigne, int nvColonne) {
        int distLigne = Math.abs(nvLigne - ligne);           //distance entre la ligne et la nouvelle ligne
        int distColonne = Math.abs(nvColonne - colonne);     //distance entre la colonne et la nouvelle colonne

        return (distLigne <= 1 && distColonne <= 1 && (distLigne != 0 || distColonne != 0));    //chaque distance peut être à 0 ou 1 car le roi n'avance que de 1
        //mais le roi doit bouger il ne peut pas y avoir (0,0)
    }

    //Méthode répertoriant et executant tous les mouvements du robot par génération de valeurs aléatoires
    public static void robotChoix(int[][] plateau, char joueur) {
        int ligne, colonne;         //coordonnées de la pièce jouée
        int nvLigne, nvColonne;    //coordonnées de la destination de la pièce jouée
        boolean bouger;             //booléen prenant comme valeur si la pièce peut bouger en fonction de sa position sur le plateau
        boolean mouvement;          //booléen prenant comme valeur les mouvements valide de la pièce jouée

        do {
            colonne = (int) (Math.random() * 8);        //génère une valeur de colonne aléatoire allant de 0 à 8
            ligne = (int) (Math.random() * 8);         //génère une valeur de ligne aléatoire allant de 0 à 8

            //change le booléen "bouger" en fonction de la pièce jouée
            if (plateau[ligne][colonne] == 7 || plateau[ligne][colonne] == 1) {           //tours
                bouger = pieceAutour(plateau, ligne, colonne);
            } else if (plateau[ligne][colonne] == 6 || plateau[ligne][colonne] == 12){    //pions
                bouger = pieceAutour3(plateau, ligne, colonne);
            } else if (plateau[ligne][colonne] == 9 || plateau[ligne][colonne] == 3){     //fous
                bouger = pieceAutour2(plateau, ligne, colonne);
            } else if(plateau[ligne][colonne]==8||plateau[ligne][colonne]==2){            //cavaliers
                bouger = bougerCavalierPossible(plateau, ligne, colonne);
            } else {
                bouger = pieceAutour(plateau, ligne, colonne) && pieceAutour2(plateau, ligne, colonne);
            }
            //refais la boucle tant que la case choisit est invalide, vide, appartient à l'adversaire ou que la pièce ne peut pas bouger
        } while(!caseValide(ligne, colonne) || plateau[ligne][colonne]==0 || !couleurJoueur(plateau, ligne, colonne, joueur) || bouger);

        int piece = plateau[ligne][colonne];    //prend la valeur de la pièce jouée

        do {
            nvColonne = (int) (Math.random() * 8);      //génère une valeur de colonne aléatoire allant de 0 à 8 pour la destination de la pièce choisie
            nvLigne = (int) (Math.random() * 8);        //pareil pour la ligne

            //change le booléen "mouvement" en fonction de la pièce jouée
            if (plateau[ligne][colonne] == 7 || plateau[ligne][colonne] == 1) {             //tours
                mouvement = mouvementTour(plateau, ligne, colonne, nvLigne, nvColonne);
            }
            else if (plateau[ligne][colonne] == 6 || plateau[ligne][colonne] == 12) {       //pions
                mouvement = mouvementPionRobot(plateau, ligne, colonne, nvLigne, nvColonne, piece) || pepRobot(ligne, colonne, nvLigne, nvColonne, piece);
                priseEnPassantRobot(plateau, ligne, colonne, nvLigne, nvColonne, piece);
            }
            else if (plateau[ligne][colonne] == 9 || plateau[ligne][colonne] == 3) {        //fous
                mouvement = mouvementFou(plateau, ligne, colonne, nvLigne, nvColonne);
            }
            else if (plateau[ligne][colonne] == 8 || plateau[ligne][colonne] == 2) {        //cavaliers
                mouvement = mouvementCavalier(ligne, colonne, nvLigne, nvColonne);
            }
            else if (plateau[ligne][colonne] == 4 || plateau[ligne][colonne] == 11) {       //dames
                mouvement = mouvementFou(plateau, ligne, colonne, nvLigne, nvColonne) || mouvementTour(plateau, ligne, colonne, nvLigne, nvColonne);
            }
            else {                                                                          //rois
                mouvement = mouvementRoiRobot(ligne, colonne, nvLigne, nvColonne);
            }
            //refais la boucle tant que la case choisit est invalide, est occupé par une pièce de meme couleur ou que le mouvement de la pièce est invalide
        } while(!caseValide(nvLigne, nvColonne) || memeCouleur(plateau, nvLigne, nvColonne, piece) || !mouvement);

        AffichageSituation(plateau, joueur, piece, ligne, colonne, nvLigne, nvColonne, -1, -1); //affichage du déplacement

        //déplacement de la pièce
        plateau[ligne][colonne]=0;
        plateau[nvLigne][nvColonne]=piece;
    }

    //fonction qui convertit un type String pris en paramètre en un int spécifique pour le choix de la colonne
    //Sépare donc l'affichage, des coordonnées réelles des pièces
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

    //fonction qui convertit un type String pris en paramètre en un int spécifique pour le choix de colonne
    //Sépare donc l'affichage, des coordonnées réelles des pièces
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

    //fonction qui convertit un type String pris en paramètre en un int spécifique pour le choix de colonne
    //Sépare donc l'affichage, des coordonnées réelles des pièces
    public static int conversionEnInt(String valeur) {
        int coordonnee = 9;

        switch(valeur){
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

    //fonction qui reconverti un int pris en paramètre en sa valeur en String
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

    //fonction qui renvoie la copie du plateau pris en paramètre
    public static int[][] copiePlateau(int[][] plateau) {
        int[][] copie = new int[8][8];                  //créé un nouveau plateau
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                copie[ligne][colonne] = plateau[ligne][colonne];    //chaque case est copié
            }
        }
        return copie;       //renvoie la copie
    }

    //méthode qui prend en paramètre un plateau et sa version précédente et va le modifier pour qu'il soit à sa version précédente
    public static void plateauPrecedent(int[][] plateau, int[][] plateauPrecedent) {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                plateau[ligne][colonne] = plateauPrecedent[ligne][colonne];     //chaque case est remis comme dans la version précédente
            }
        }
    }

    //méthode qui appel d'autre méthodes en fonction de la pièce jouée
    public static void appelPiece(int[][] plateau, int ligne, int colonne, char joueur, int mode) {
        int[][] sauvegarde = copiePlateau(plateau);

        if (couleurJoueur(plateau, ligne, colonne, joueur)) {        //si le joueur joue des pièces à lui

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
                if (bougerCavalierPossible(plateau, ligne, colonne)) {
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

            //si après le mouvement le roi est en échec le joueur rejoue
            if (estEnEchec(plateau, joueur)) {
                System.out.println("❌ Mouvement interdit : votre roi est en échec");

                plateauPrecedent(plateau, sauvegarde);

                coordonnees(plateau, joueur, mode);
            }
        } else {       //si le joueur joue des pièces de l'adversaire
            System.out.print("Ce ne sont pas vos pièces ! ");
            coordonnees(plateau, joueur, mode);
        }
    }

    //fonction qui retourne vrai si la Tour prise en paramètre n'a jamais bougé (pour le roque)
    public static boolean TourPasBougee(int compteurTour) {
        return compteurTour == 0;       //si le compteur est resté à 0
    }

    //fonction qui retourne vrai si la pièce du plateau jouée par le joueur lui appartient
    //faux sinon
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

    //fonction qui renvoie vrai si la case est dans le plateau, faux sinon
    public static boolean caseValide(int ligne, int colonne) {
        return (ligne >= 0 && ligne < 8) && (colonne >= 0 && colonne < 8);      //si les coordoonées sont supérieures à 0 et inférieures à 8
    }

    //fonction qui retourne vrai si le mouvement du Cavalier est en L et possible selon sa position (mode 2)
    public static boolean bougerCavalierPossible(int[][] plateau, int ligne, int colonne) {
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;       //booléen qui prend la valeur true quand le pièce est bleue

        //tableaux contenant les seules combinaisons de coordonnées possibles pour les mouvements en L du cavalier
        int[] lignes = {2, 2, 1, 1, -2, -2, 1, 1};
        int[] colonnes = {-1, 1, 2, -2, -1, 1, -2, 2};

        //boucle for qui teste toutes les combinaisons de lignes et colonnes assemblées pour les 8 mouvements en L possibles
        for (int i = 0; i < 8; i++) {
            nouvelleL = ligne + lignes[i];
            nouvelleC = colonne + colonnes[i];

            if (caseValide(nouvelleL, nouvelleC)) {     //vérifie que la combinaison est sur le plateau pour éviter les bugs de sorties de plateau
                boolean pieceEnFace = plateau[nouvelleL][nouvelleC] > 6;        //booléen qui prend la valeur true si la pièce en face est bleue

                if (plateau[nouvelleL][nouvelleC] == 0 || pieceEnFace != bleu) {       //si la case est vide ou si elle est occupée par une pièce de même couleur
                    return false;                                                      //retourne faux
                }
            }
        }
        return true;
    }


    //plusieurs mini fonctions retournant true si le mouvement en L est possible en fonction de la position du cavalier sur le plateau
    public static boolean LGrandHautGauche(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne - 2, colonne - 1) && !(memeCouleur(plateau, ligne - 2, colonne - 1, cavalier));
    }
    public static boolean LGrandHautDroit(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne - 2, colonne + 1) && !(memeCouleur(plateau, ligne - 2, colonne + 1, cavalier));
    }
    public static boolean LPetitHautGauche(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne - 1, colonne - 2) && !(memeCouleur(plateau, ligne - 1, colonne - 2, cavalier));
    }
    public static boolean LPetitHautDroit(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne - 1, colonne + 2) && !(memeCouleur(plateau, ligne - 1, colonne + 2, cavalier));
    }
    public static boolean LGrandBasGauche(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne + 2, colonne - 1) && !(memeCouleur(plateau, ligne + 2, colonne - 1, cavalier));
    }
    public static boolean LGrandBasDroit(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne + 2, colonne + 1) && !(memeCouleur(plateau, ligne + 2, colonne + 1, cavalier));
    }
    public static boolean LPetitBasGauche(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne + 1, colonne - 2) && !(memeCouleur(plateau, ligne + 1, colonne - 2, cavalier));
    }
    public static boolean LPetitBasDroit(int[][] plateau, int ligne, int colonne, int cavalier) {
        return caseValide(ligne + 1, colonne + 2) && !(memeCouleur(plateau, ligne + 1, colonne + 2, cavalier));
    }

    //fonction qui affiche les directions disponibles pour le cavalier en fonction de sa position actuelle sur le plateau s'il y a
    //plus d'une direction, le joueur peut en choisir une, sinon la direction sera choisie d'office et ce choix sera retourné
    //en un int (pour le mode 1)
    public static int AffichageDirectionsCavalier(int[][] plateau, int ligne, int colonne, int couleur) {
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

        if (possibilites > 1) {         //s'il y a plus d'une direction possible
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

                //choix valide seulement si le mouvement auquel il correspond est disponible
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

    //fonction qui retourne vrai si les cases+1 du haut, du bas, de gauche et de droite sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour(int[][] plateau, int ligne, int colonne) {
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;       //booleen contenant la valeur vrai si la pièce est bleue

        //tableaux contenant l'ensemble des coordonnées de lignes et colonnes possibles pour des mouvements droits
        int[] lignes = {0, 0, -1, 1};
        int[] colonnes = {-1, 1, 0, 0};

        //génère toutes les 4 possibilités de combinaisons possibles de lignes et colonnes pour les 4 directions droites
        for (int i = 0; i < 4; i++) {
            nouvelleL = ligne + lignes[i];
            nouvelleC = colonne + colonnes[i];

            if (caseValide(nouvelleL, nouvelleC)){  //si la case existe sur le plateau
                boolean pieceEnFace = plateau[nouvelleL][nouvelleC] > 6;        //booleen contenant la valeur vrais si la pièce en face est bleue aussi

                //retourne faux si si la case est vide ou ne contient pas de pièce de la même couleur
                if(plateau[nouvelleL][nouvelleC] == 0 || pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;        //sinon retourne vrai il y a des pièces autour
    }

    //fonction qui retourne vrai si les cases+1 des 4 diagonales sont disponibles pour la pièce jouée actuelle
    public static boolean pieceAutour2(int[][] plateau, int ligne, int colonne) {
        int piece = plateau[ligne][colonne];
        int nouvelleL;
        int nouvelleC;
        boolean bleu = piece > 6;       //booleen contenant la valeur vrai si la pièce est bleue

        //tableaux contenant l'ensemble des coordonnées de lignes et colonnes possibles pour des mouvements en diagonale
        int[] lignes = {-1, 1, -1, 1};
        int[] colonnes = {-1, 1, 1, -1};

        //génère toutes les 4 possibilités de combinaisons possibles de lignes et colonnes pour les 4 directions diagonales
        for (int i = 0; i < 4; i++) {
            nouvelleL = ligne + lignes[i];
            nouvelleC = colonne + colonnes[i];

            if (caseValide(nouvelleL, nouvelleC)) {     //si la case existe sur le plateau
                boolean pieceEnFace = plateau[nouvelleL][nouvelleC] > 6;        //booleen contenant la valeur vrai si la pièce en face est bleue aussi

                //retourne faux si si la case est vide ou ne contient pas de pièce de la même couleur
                if (plateau[nouvelleL][nouvelleC] == 0 || pieceEnFace != bleu) {
                    return false;
                }
            }
        }
        return true;        //sinon retourne vrai il y a des pièces autour
    }

    //fonction qui renvoie vrai si le pion peut bouger, faux sinon
    public static boolean pieceAutour3(int[][] plateau, int ligne, int colonne) {
        int pion = plateau[ligne][colonne];
        int sens;

        if (pion == 12) {   //pion bleu
            sens = -1; // monte
        } else {            //pion jaune
            sens = 1;  // descend
        }

        //on vérifie les 3 possibilités de mouvement
        boolean diagoGauche = caseValide(ligne + sens, colonne - 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne - 1, pion);
        boolean diagoDroite = caseValide(ligne + sens, colonne + 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne + 1, pion);
        boolean peutAvancer = caseValide(ligne + sens, colonne) && plateau[ligne + sens][colonne] == 0;

        //si une des options est possible on renvoie faux
        if (diagoGauche || diagoDroite || peutAvancer) {
            return false;
        } else {
            return true;
        }
    }

    //méthode qui permet l'affichage du plateau
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

    //méthode qui permet l'affichage des pièces sur le plateau vu par les bleus
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

    //méthode qui permet l'affichage des pièces sur le plateau vu par les jaunes
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

    //fonction qui vérifie si le mouvement est possible en vérifiant s'il y a des pièces empêchant un mouvement d'une distance
    //vers une direction prisent en paramètre
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

    //fonction qui renvoie vraie si la pièce actuelle est de la même couleur que celle de la couleur de la pièce sur la case de notre choix
    //faux sinon
    public static boolean memeCouleur(int[][] plateau, int ligne, int colonne, int couleur) {
        if (plateau[ligne][colonne] == 0){
            return false;
        }

        boolean bleu = couleur > 6;
        boolean pieceEnFace = plateau[ligne][colonne] > 6;

        return bleu == pieceEnFace;
    }

    //fonction qui renvoie vraie si la pièce actuelle est de la même couleur que celle de la couleur de la pièce sur la case de notre choix
    //ou si la case est vide, faux sinon
    public static boolean memeCouleurEtVide(int[][] plateau, int ligne, int colonne, int couleur) {
        if (plateau[ligne][colonne] == 0) return true;

        boolean bleu = couleur > 6;
        boolean pieceEnFace = plateau[ligne][colonne] > 6;

        return bleu == pieceEnFace;
    }

    //fonction qui affiche les directions disponibles en lignes droites en fonction de sa position actuelle sur le plateau s'il y a
    //plus d'une direction, le joueur peut en choisir une, sinon la direction sera choisie d'office et ce choix sera retourné
    //en un int (pour le mode 1)
    public static int affichageDirectionDroites(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        boolean haut = haut(plateau, ligne, colonne, piece);
        boolean gauche = gauche(plateau, ligne, colonne, piece);
        boolean droite = droite(plateau, ligne, colonne, piece);
        boolean bas = bas(plateau, ligne, colonne, piece);

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

        if (possibilites > 1) {             //s'il y a plus d'une direction possible, on demande au joueur de choisir
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if (joueur == 'B') {        //Si le joueur joue les bleus
                    if (haut)
                        System.out.print("1 pour aller en haut ");
                    if (gauche)
                        System.out.print("2 pour aller à gauche ");
                    if (droite)
                        System.out.print("3 pour aller à droite ");
                    if (bas)
                        System.out.print("4 pour aller en bas ");
                } else {                    //Si le joueur joue les jaunes (plateau inversé)
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

                //choix valide seulement si le mouvement auquel il correspond est disponible
                if ((choix == 1 && haut) || (choix == 2 && gauche) || (choix == 3 && droite) || (choix == 4 && bas)) {
                    valide = true;
                } else {
                    System.out.println("❌ Direction impossible, recommencez.");
                }
            }
        }
        return choix;
    }

    //fonction qui renvoie les choix que peut faire le joueur pour bouger le pion
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

        if(possibilites>1) {        //si il y a minimum une possibilité
            while (!valide) {
                //affichage des possibilés en fonction du joueur
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

    //méthode qui renvoie le choix de la promotion pour son pion au joueur
    public static int ChoixPromotion(int pion){
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        int promotion;

        while (choix != 1 && choix != 2 && choix != 3 && choix != 4) {
            System.out.print("1 promouvoir en dame, 2 promouvoir en tour, 3 promouvoir en fou, 4 promouvoir en cavalier : ");
            String choixS = sc.nextLine();
            choix = methodes.conversionEnInt(choixS);
        }
        if (pion == 12) { //pion bleu
            if (choix == 1){
                promotion = 11; //dame bleu
            }
            else if (choix == 2){
                promotion = 7;  //tour bleu
            }
            else if (choix == 3){
                promotion = 9;  //fou bleu
            }
            else {
                promotion = 8;  //cavalier bleu
            }
        }
        else {          //pion jaune
            if (choix == 1){
                promotion = 4;  //dame jaune
            }
            else if (choix == 2){
                promotion = 1;  //tour jaune
            }
            else if (choix == 3){
                promotion = 3;  //fou jaune
            }
            else {
                promotion = 2;  //cavalier jaune
            }
        }

        return promotion;       //renvoie la promotion du pion
    }

    //méthode s'occupant de la prise en passant pour les modes robot et 2
    //change la valeur de pep quand le pion bouge de 2 cases et enregistre ses coordonnées
    //autorise la prise en passant si elle est légale et appel les méthodes nécessaires pour effectuer le déplacement
    //des pièces lors de la prise en passant
    public static void priseEnPassantRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne, int pion) {
        boolean priseEnPassantG = pepPossibleGauche(ligne, colonne, pion);
        boolean priseEnPassantD = pepPossibleDroite(ligne, colonne, pion);

        //si le mouvement prise en passant est effectué et autorisé
        if (pepRobot(ligne, colonne, nvLigne, nvColonne, pion)) {
            if (priseEnPassantG) {            //si la prise en passant se fait par la gauche et est légale
                PriseEnPassantG(plateau, ligne, colonne, pion);         //appelle la méthode spécifique pour la gauche
            }
            else if (priseEnPassantD) {       //si la prise en passant se fait par la droite et est légale
                PriseEnPassantD(plateau, ligne, colonne, pion);         //appelle la méthode spécifique pour la droite
            }
        }

        if (Math.abs(ligne - nvLigne) == 2) {       //si le pion a avancé de 2cases, change la valeur de pep en true (pour le tour suivant)
            pep = true;
            ligneAvant = nvLigne;           //coordonnées du pion ayant
            colonneAvant = colonne;         // avancé de 2cases
        } else {           //si il n'a pas avancé de 2cases, change la valeur de pep en false (pour le tour suivant)
            pep = false;
        }
    }

    //méthode qui affiche au joueur si les coordonnées qu'il a inscrite peuvent faire bouger le pion et le fais avancer si possible (mode 2)
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

            //tous les mouvements que le pion peut faire
            mouvementValide = mouvementPionRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion) || pepRobot(ligne, colonne, nvLigne, nvColonne, pion);

            if (!mouvementValide) {
                System.out.println("Le pion ne peut pas aller là");
            }
        } while (!mouvementValide);

        priseEnPassantRobot(plateau, ligne, colonne, nvLigne, nvColonne, pion);

        //mise à jour du plateau
        plateau[ligne][colonne] = 0;
        plateau[nvLigne][nvColonne] = pion;

        //affichage du mouvement
        if(!pepRobot(ligne, colonne, nvLigne, nvColonne, pion)) {
            AffichageSituation(plateau, joueur, pion, ligne, colonne, nvLigne, nvColonne, -1, -1);
        }

        //vérification de la promotion
        if (nvLigne == ligneFin) {
            plateau[nvLigne][nvColonne] = ChoixPromotion(pion);
        }
    }

    //méthode qui affiche au joueur si les coordonnées qu'il a inscrite peuvent faire bouger le roi et le fais avancer si possible (mode 2)
    public static void mouvementRoi(int[][] plateau, int ligne, int colonne, char joueur, int roi) {
        int nvLigne, nvColonne;
        boolean mouvementValide = false;

        do {
            do {
                System.out.println("Où voulez-vous aller avec votre roi ?");
                nvLigne = coordonneeLigne();
                nvColonne = coordonneeColonne();

            }while(nvColonne==10);

            //calcul de la distance des ligne et colonne
            int distL = Math.abs(nvLigne - ligne);
            int distC = Math.abs(nvColonne - colonne);

            //chaque distance peut être à 0 ou 1 car le roi n'avance que de 1 mais le roi doit bouger il ne peut pas y avoir (0,0)
            if (distL <= 1 && distC <= 1 && (distL != 0 || distC != 0)) {

                //on vérifie qu'il va sur une case avec des ennemis ou vide
                if (!memeCouleur(plateau, nvLigne, nvColonne, roi)) {
                    mouvementValide = true;
                } else {
                    System.out.println("Le roi ne peut pas aller là");
                }
            } else {
                System.out.println("Le roi ne peut pas aller là");
            }
        } while (!mouvementValide);

        // mise à jour du plateau
        plateau[ligne][colonne] = 0;
        plateau[nvLigne][nvColonne] = roi;

        //affichage du mouvement
        AffichageSituation(plateau, joueur, roi, ligne, colonne, nvLigne, nvColonne, -1, -1);

    }

    //fonction qui renvoie vrai si les nouvelle coordonnées peuvent être dans les mouvements possibles du cavalier (mode 2)
    public static boolean mouvementCavalier(int ligne, int colonne, int nvLigne, int nvColonne) {
        return (Math.abs(nvLigne-ligne)==2 && Math.abs(nvColonne-colonne)==1) || (Math.abs(nvLigne-ligne)==1 && Math.abs(nvColonne-colonne)==2);
    }

    //fonction qui renvoie vrai si les nouvelle coordonnées peuvent être dans les mouvements possibles de la tour (mode 2)
    public static boolean mouvementTour(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        return (nvLigne==ligne && nvColonne!=colonne) || (nvLigne!=ligne && nvColonne==colonne) && !empechementRobot(plateau, ligne, colonne, nvLigne, nvColonne);    //mouvements seulement droits
    }

    //fonction qui renvoie vrai si les nouvelle coordonnées peuvent être dans les mouvements possibles du fou (mode 2)
    public static boolean mouvementFou(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        return Math.abs(nvLigne - ligne) == Math.abs(nvColonne - colonne) && !empechementRobot(plateau, ligne, colonne, nvLigne, nvColonne);      //mouvements seulement en diagonale
    }

    //méthode qui demande au joueur de saisir des coordonnées pour la destination de la pièce jouée et vérifie si le mouvement
    //de cette pièce est valide et force la saisie, avant d'effectuer le mouvement (pour le mode de jeu 2)
    public static void destinationPiece(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        int NvLigne, NvColonne;
        boolean mouvementValide;
        String pieceS = piece(piece);

        do {
            do {
                mouvementValide = false;
                System.out.println("Où veux-tu aller ?");
                NvLigne = coordonneeLigne();

                NvColonne = coordonneeColonne();
            } while (NvColonne == 10);

            //regarde en fonction de la pièce jouée si le mouvement est valide
            if (piece == 1 || piece == 7 || piece == 4 || piece == 11) {        //Tour ou Damme
                if (mouvementTour(plateau, ligne, colonne, NvLigne, NvColonne) && !empechementRobot(plateau, ligne, colonne, NvLigne, NvColonne)){
                    mouvementValide = true;
                }
            }
            if (piece == 3 || piece == 9 || piece == 4 || piece == 11) {        //Fou ou Dame
                if (mouvementFou(plateau, ligne, colonne, NvLigne, NvColonne)) {
                    mouvementValide = true;
                }
            }
            if (piece == 2 || piece == 8) {                     //Cavalier
                mouvementValide = mouvementCavalier(ligne, colonne, NvLigne, NvColonne);
            }

            //regarde si la case est valide et pas occupée par une pièce de même couleur ou return false
            if (!(caseValide(NvLigne, NvColonne)) || memeCouleur(plateau, NvLigne, NvColonne, piece)) {
                mouvementValide = false;
                System.out.println(pieceS + " ne peut pas aller là");
            }

        } while (!mouvementValide);     //tant que le mouvement est pas valide on redemande les coordonnées

        //affichage du déplacement de la pièce
        AffichageSituation(plateau, joueur, piece, ligne, colonne, NvLigne, NvColonne, -1, -1);

        //déplacement de la pièce
        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = piece;
    }

    //fonction qui retourne vrai s'il y a des pièces entre la position initiale de la pièce et sa destination-1 avec
    //simplement les lignes de départ et d'arrivée de chaque pièce
    public static boolean empechementRobot(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        //initialisation des directions
        int directionLigne = 0;     //HautBas
        int directionColonne = 0;      //GaucheDroite

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

        //initialisation de la première case à tester en fonction de la direction (on extrait la ligne de départ pour le test)
        int l = ligne + directionLigne;
        int c = colonne + directionColonne;

        while (l != nvLigne || c != nvColonne) {    //tant qu'on est pas arrivé à la case finale

            if (caseValide(l, c) && plateau[l][c] != 0) {   //si la case est dans le plateau et occupée
                return true;            //on retourne vrai (il y a un empechement)
            }

            //on ajoute/enlève 1 à chaque indice en fonction de la direction du mouvement
            l = l + directionLigne;
            c = c + directionColonne;
        }
        return false;       //sinon on retourne faux, aucun empêchement
    }

    //plusieurs mini fonctions retournant true si le mouvement est possible en fonction de la position de la pièce sur le plateau
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

    //méthode qui affiche au joueur les possibilités pour faire avancer son pion et le fais avancer si possible (mode 1)
    public static void BougePion(int[][] plateau, int ligne, int colonne, char joueur, int pion) {
        Scanner sc = new Scanner(System.in);

        int avancer = 1;
        int reponse = -1;
        boolean priseEnPassantG = pepPossibleGauche(ligne, colonne, pion);
        boolean priseEnPassantD = pepPossibleDroite(ligne, colonne, pion);

        int sens;           // -1 pour monter, 1 pour descendre
        int ligneDepart;    //la ligne où il peut avancer de 2
        int ligneFin;       //la ligne où il a une promotion
        int NvLigne = ligne;
        int NvColonne = colonne;

        if (pion == 12) { //pion bleu
            sens = -1;
            ligneDepart = 6;
            ligneFin = 0;
        } else {         //pion jaune
            sens = 1;
            ligneDepart = 1;
            ligneFin = 7;
        }

        //on regarde ce qu'il y a autour
        boolean diagoDroite = caseValide(ligne + sens, colonne + 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne + 1, pion);
        boolean diagoGauche = caseValide(ligne + sens, colonne - 1) && !memeCouleurEtVide(plateau, ligne + sens, colonne - 1, pion);
        boolean peutAvancerUn = plateau[ligne + sens][colonne] == 0;
        boolean peutAvancerDeux = (ligne == ligneDepart) && plateau[ligne + (2 * sens)][colonne] == 0;

        //choix du joueur
        int choix = affichageChoixPion(diagoDroite, diagoGauche, peutAvancerUn, joueur);

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
            if ((diagoGauche && !diagoDroite && !peutAvancerUn) || choix == 1) { //mouvement diagonale gauche
                NvLigne = ligne + sens;
                NvColonne = colonne - 1;
            } else if ((!diagoGauche && diagoDroite && !peutAvancerUn) || choix == 2) { //mouvement diagonale droite
                NvLigne = ligne + sens;
                NvColonne = colonne + 1;
            } else if ((!diagoGauche && !diagoDroite && peutAvancerUn) || choix == 3) { //avancer tout droit
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
            }

            //on place le pion ou la promotion
            if (NvLigne == ligneFin) {
                plateau[NvLigne][NvColonne] = ChoixPromotion(pion);
            } else {
                AffichageSituation(plateau, joueur, pion, ligne, colonne, NvLigne, NvColonne, avancer, 0);
                plateau[NvLigne][NvColonne] = pion;
            }
        }
    }

    //méthode qui affiche au joueur les possibilités pour faire avancer son roi et le fais avancer si possible (mode 1)
    public static void BougeRoi(int[][] plateau, int ligne, int colonne, char joueur, int couleur) {
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

        if (ligneDroite && diagonale) {         //si il peut faire les 2 on affiche le choix sinon non
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
            BougeTour(plateau, ligne, colonne, couleur, joueur);
        } else if (choix == 2) {
            BougeFou(plateau, ligne, colonne, couleur, joueur);
        }
    }

    //méthode qui affiche au joueur les possibilités pour faire avancer sa dame et la fais avancer si possible (mode 1)
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
            while (choix != 1 && choix != 2) {      //si il peut faire les 2 on affiche le choix sinon non
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

    //fonction qui affiche les directions disponibles en diagonales en fonction de sa position actuelle sur le plateau s'il y a
    //plus d'une direction, le joueur peut en choisir une, sinon la direction sera choisie d'office et ce choix sera retourné
    //en un int (pour le mode 1)
    public static int affichageDirectionDiagonales(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        boolean valide = false;
        int possibilites = 0;

        boolean hautGauche = hautGauche(plateau, ligne, colonne, piece);
        boolean hautDroite = hautDroite(plateau, ligne, colonne, piece);
        boolean basGauche = basGauche(plateau, ligne, colonne, piece);
        boolean basDroite = basDroite(plateau, ligne, colonne, piece);

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

        if (possibilites > 1) {         //s'il y a plus d'une direction possible, on demande au joueur de choisir
            while (!valide) {
                System.out.print("Choisissez une direction : ");
                if (joueur == 'B') {            //Si le joueur joue les bleus
                    if (hautGauche)
                        System.out.print("1 pour aller en haut à gauche ");
                    if (hautDroite)
                        System.out.print("2 pour aller en haut à droite ");
                    if (basGauche)
                        System.out.print("3 pour aller en bas à gauche ");
                    if (basDroite)
                        System.out.print("4 pour aller en bas à droite ");
                } else {            //Si le joueur joue les jaunes (plateau inversé)
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

                //choix valide seulement si le mouvement auquel il correspond est disponible
                if ((choix == 1 && hautGauche) || (choix == 2 && hautDroite) || (choix == 3 && basGauche) || (choix == 4 && basDroite)) {
                    valide = true;
                } else {
                    System.out.println("❌ Direction impossible, recommencez.");
                }
            }
        }
        return choix;
    }

    //méthode effectuant les mouvements en ligne droite (pour la Tour, le Roi et la Dame)
    public static void BougeTour(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        int valeurDirection = 0;
        int direction = affichageDirectionDroites(plateau, ligne, colonne, piece, joueur);

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
        if(piece == 1 || piece == 7 || piece == 4 || piece == 11) {      //tours+dame
            MouvementPieces(plateau, ligne, colonne, piece, hautBas, gaucheDroite, valeurDirection, joueur);
        }
        else{       //rois
            int NvLigne = ligne + hautBas;            //change en fonction de si le joueur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si le joueur veut aller à gauche/droite ou aucun des 2

            //affichage descriptif du mouvement effectué
            AffichageSituation(plateau, joueur, piece, ligne, colonne, NvLigne, NvColonne, 1, valeurDirection);

            //déplacement de du roi
            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = piece;
        }
    }

    //méthode effectuant les mouvements en diagonale (pour le Fou, le Roi et la Dame)
    public static void BougeFou(int[][] plateau, int ligne, int colonne, int piece, char joueur) {
        int direction = affichageDirectionDiagonales(plateau, ligne, colonne, piece, joueur);
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
        if(piece == 3 || piece == 9 || piece == 4 || piece == 11) {     //fou+dame
            MouvementPieces(plateau, ligne, colonne, piece, hautBas, gaucheDroite, valeurDirection, joueur);
        }
        else{       //roi
            int NvLigne = ligne + hautBas;            //change en fonction de si le joueur veut monter/descendre ou aucun des 2
            int NvColonne = colonne + gaucheDroite;   //change en fonction de si le joueur veut aller à gauche/droite ou aucun des 2

            //affichage descriptif du mouvement effectué
            AffichageSituation(plateau, joueur, piece, ligne, colonne, NvLigne, NvColonne, 1, valeurDirection);

            //déplacement de du roi
            plateau[ligne][colonne] = 0;
            plateau[NvLigne][NvColonne] = piece;
        }
    }

    //méthode proposant le roque s'il est possible ou appelle les méthodes du roi pour les mode 1 et 2 quand il n'est pas possible ou que
    //le joueur décide de ne pas roquer
    public static void demandeRoque(int[][] plateau, int ligne, int colonne, int couleur, char joueur, int mvtTourLoin, int mvtTourProche, int mvtRoi, int mode){
        Scanner sc = new Scanner(System.in);

        boolean TourLointaine = TourPasBougee(mvtTourLoin);
        boolean TourProche = TourPasBougee(mvtTourProche);
        boolean mouvementAzero = nbMouvementsTourRoi(TourLointaine, TourProche, mvtRoi);
        boolean petit = PetitRoque(plateau, ligne, colonne, TourProche, couleur);
        boolean grand = GrandRoque(plateau, ligne, colonne, TourLointaine, couleur);
        int oui=-1;

        if (mouvementAzero && (petit || grand)) {       //si les mouvements sont égaux à 0 ou qu'un des 2 roque et possible
            while(oui!=1 && oui!=0) {
                System.out.print("Voulez-vous roquer ? (1 pour oui, 0 pour non) : ");
                String ouiS = sc.nextLine();
                oui = methodes.conversionEnInt(ouiS);
            }
        }
        if (oui == 1) {         //si le joueur veut, appel de la méthode de roque
            roque(plateau, couleur, ligne, colonne, TourLointaine, TourProche);
        }
        else{       //sinon appel des méthodes du roi en fonction du mode
            if (mode == 1){         //mode débutant
                BougeRoi(plateau, ligne, colonne, joueur, couleur);
            }
            else{                   //mode moyen
                methodes.mouvementRoi(plateau, ligne, colonne, joueur, couleur);
            }
        }
    }

    //méthode effectuant le roque en fonction de la Tour et du Roi sélectionnés
    public static void roque(int[][] plateau, int Roi, int ligne, int colonne, boolean tourLoin, boolean tourProche){
        int Tour;
        String couleur;
        int choix = demandeTourRoque(plateau, ligne, colonne, tourLoin, tourProche, Roi);
        String col4 = conversionEnString(11-colonne);
        String col5 = conversionEnString(colonne-4);

        if(Roi==5) {    //roi jaune
            Tour = 1;   //tour jaune
            couleur = "jaune";
        } else {        //roi bleu
            Tour = 7;   //tour bleue
            couleur = "bleu";
        }

        //affichage de la situation et déplacement du roi+de la tour en fonction du roque effectué
        if(choix==1){       //pour le petit roque
            System.out.println();
            System.out.println("\uD83C\uDF1F Le roi " + couleur + " a roqué avec la tour (" + col4 + "," + (8-ligne) + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne+2] = Roi;
            plateau[ligne][colonne+3] = 0;
            plateau[ligne][colonne+1] = Tour;
        }
        else if(choix==2){      //pour le grand roque
            System.out.println();
            System.out.println("\uD83C\uDF1F Le roi " + couleur + " a roqué avec la tour (" + col5 + "," + (8-ligne) + ")");
            plateau[ligne][colonne] = 0;
            plateau[ligne][colonne-2] = Roi;
            plateau[ligne][colonne-4] = 0;
            plateau[ligne][colonne-1] = Tour;
        }
    }

    //fonction retournant un int en demandant avec quelle Tour le joueur veut roquer quand les 2 roques sont possibles
    //ou un int atitré quand seulement un seul roque est possible
    public static int demandeTourRoque(int[][] plateau, int ligne, int colonne, boolean TourLoin, boolean TourProche, int roi){
        Scanner scanner = new Scanner(System.in);

        boolean petit = PetitRoque(plateau, ligne, colonne, TourProche, roi);
        boolean grand = GrandRoque(plateau, ligne, colonne, TourLoin, roi);
        int choixRoque;

        if(petit && grand){     //2 roques possibles
            do {
                System.out.print("Tapez 1 pour un petit Roque (avec la Tour la plus proche) ou tapez 2 pour un grand Roque (avec la Tour la plus éloignée)  : ");
                String choixRoqueS = scanner.nextLine();

                choixRoque = methodes.conversionEnInt(choixRoqueS);
            } while(choixRoque!=1 && choixRoque!=2);
        }
        else if(petit){         //seulement le petit roque possible
            choixRoque=1;
        }
        else{                   //seulement le grand roque possible
            choixRoque=2;
        }
        return choixRoque;
    }

    //fonction retournant vrai si le roi n'a pas bougé et si l'une des 2 Tours de sa couleur, également
    public static boolean nbMouvementsTourRoi(boolean TourLoin, boolean TourProche, int nbMouvementR){
        return (TourLoin || TourProche) && nbMouvementR==0;
    }
    //fonction retournant vrai si le petit roque est possible en fonction de si la tour la plus proche n'a jamais bougé, et si les cases
    //séparant le roi et cette Tour sont vides et pas sous échec
    public static boolean PetitRoque(int[][] plateau, int ligne, int colonne, boolean TourProche, int roi){
        return TourProche && plateau[ligne][colonne+1]==0 && !caseEnEchec(plateau, ligne, colonne+1, roi)
                && plateau[ligne][colonne+2]==0 && !caseEnEchec(plateau, ligne, colonne+2, roi);
    }
    //fonction retournant vrai si le grand roque est possible en fonction de si la tour la plus éloignée n'a jamais bougé, et si les cases
    //séparant le roi et cette Tour sont vides et pas sous échec
    public static boolean GrandRoque(int[][] plateau, int ligne, int colonne, boolean TourLoin, int roi){
        return TourLoin && plateau[ligne][colonne-1]==0 && !caseEnEchec(plateau, ligne, colonne-1, roi) &&
                plateau[ligne][colonne-2]==0 && !caseEnEchec(plateau, ligne, colonne-2, roi)
                && plateau[ligne][colonne-3]==0 && !caseEnEchec(plateau, ligne, colonne-3, roi) ;
    }

    //méthode effectuant la prise en passant vers la droite
    public static void PriseEnPassantD(int[][] plateau, int ligne, int colonne, int pion){
        String couleur;
        String col = conversionEnString(colonne+1);

        if(pion==6) {    //pion jaune
            couleur = "jaune";
        } else {        //pion bleu
            couleur = "bleu";
        }

        //affichage de la situation
        System.out.println();
        System.out.println("\uD83C\uDF1F le pion " + couleur + " a fais une prise en passant en (" + col + "," + (ligne + 2) + ")");

        //vide des cases du pion mangé et du pion bougé
        plateau[ligne][colonne+1] = 0;
        plateau[ligneAvant][colonneAvant] = 0;

        //réaffichage du pion bougé en fonction de sa couleur (le pion bleu "ligne-1" et le pion jaune "ligne+1")
        if (pion == 12)     //pion bleu
            plateau[ligne-1][colonne+1] = pion;
        else
            plateau[ligne+1][colonne+1] = pion;
    }

    //méthode effectuant la prise en passant vers la gauche
    public static void PriseEnPassantG(int[][] plateau, int ligne, int colonne, int pion){
        String couleur;
        String col = conversionEnString(colonne-1);

        if(pion==6) {    //pion jaune
            couleur = "jaune";
        } else {        //pion bleu
            couleur = "bleu";
        }

        //affichage de la situation
        System.out.println();
        System.out.println("\uD83C\uDF1F le pion " + couleur + " a fais une prise en passant en (" + col + "," + ligne + ")");

        //vide des cases du pion mangé et du pion bougé
        plateau[ligne][colonne-1] = 0;
        plateau[ligneAvant][colonneAvant] = 0;

        //réaffichage du pion bougé en fonction de sa couleur (le pion bleu "ligne-1" et le pion jaune "ligne+1")
        if (pion == 12)         //pion bleu
            plateau[ligne-1][colonne-1] = pion;
        else                    //pion jaune
            plateau[ligne+1][colonne-1] = pion;
    }

    //fonction qui retourne vrai si la prise en passant est possible par la gauche en fonction du pion joué
    public static boolean pepPossibleGauche(int ligne, int colonne, int couleur){
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

    //fonction qui retourne vrai si la prise en passant est possible par la droite en fonction du pion joué
    public static boolean pepPossibleDroite(int ligne, int colonne, int pion){
        boolean possible = false;

        if(pep) {
            if (pion == 12 && ligne == 3) {       //bleus
                if (ligneAvant==3 && colonneAvant==(colonne+1)) {
                    possible = true;
                }
            } else if (pion == 6 && ligne == 4) {    //jaunes
                if (ligneAvant==4 && colonneAvant==(colonne+1)) {
                    possible = true;
                }
            }
        }
        return possible;
    }

    //fonction qui retourne vrai quand la pièce jouée ne peut avancer que d'une case maximum
    public static boolean avancerUneCaseMax(int[][] plateau, int ligne, int colonne, int piece, int directionLigne, int directionColonne) {
        int l = ligne + 2 * directionLigne;         //nouvelles coordonnées de la pièce pour un mouvement de 2cases
        int c = colonne + 2 * directionColonne;     //en fonction de la direction

        //si la case+2 de la pièce jouée est dans le plateau et pas occupée par une pièce de même couleur+que la case+1 est vide
        if (caseValide(l, c) && !memeCouleur(plateau, l, c, piece)){
            if(plateau[ligne+directionLigne][colonne+directionColonne]==0) {
                return false;           //retourne faux (la pièce peut avancer au moins de 2cases supplémentaires
            }
        }
        return true;
    }

    //Méthode demandant de combien veut avancer le joueur (force la saisie) et fait bouger la pièce pour le mode 1
    //utilisée par la Tour et le Fou
    public static void MouvementPieces(int[][] plateau, int ligne, int colonne, int couleur, int hautBas, int gaucheDroite, int direction, char joueur){
        Scanner sc = new Scanner(System.in);
        int NvLigne, NvColonne;
        int choix=1;

        //si la pièce peut avancer de plus de 2cases, on demande de combien on veut avancer, sinon la case avance automatiquement
        //d'une case
        if(!avancerUneCaseMax(plateau, ligne, colonne, couleur, hautBas, gaucheDroite)) {
            System.out.print("Tu veux avancer de combien ? ");      //demande de combien le joueur veut se déplacer sans prendre en compte la direction
            String choixS = sc.nextLine();
            choix = methodes.conversionEnInt(choixS);
        }
        boolean empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);
        NvLigne = ligne + (hautBas * choix);        //change en fonction de si le joueur veut monter/descendre ou aucun des 2
        NvColonne = colonne + (gaucheDroite * choix);   //change en fonction de si le joueur veut aller à gauche/droite ou aucun des 2

        while (!(caseValide(NvLigne, NvColonne)) || memeCouleur(plateau, NvLigne, NvColonne, couleur) || empechement) {
            System.out.println("❌ impossible d'avancer jusque là");
            System.out.print("de combien veux-tu avancer ? : ");
            String choixS = sc.nextLine();
            choix = methodes.conversionEnInt(choixS);

            empechement = empechement(plateau, ligne, colonne, choix, hautBas, gaucheDroite);

            NvLigne = ligne + (hautBas * choix);
            NvColonne = colonne + (gaucheDroite * choix);
        }

        AffichageSituation(plateau, joueur, couleur, ligne, colonne, NvLigne, NvColonne, choix, direction);     //affichage du mouvement

        //déplacement de la pièce
        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = couleur;
    }

    //fonction retournant un String coorespondant au nom de la pièce jouée, en fonction de son numéro
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

    //fonction retournant un String pour l'affichage des mouvements, en fonction de la direction et
    //de la pièce jouée
    public static String direction(int direction, int piece){
        String directions=" ";

        if(piece<=6) {          //pour le mouvement des pièces jaunes (quand les bleus sont en bas du plateau)
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
        else{                           //pour le mouvement des pièces bleues (quand les jaunes sont en bas du plateau)
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

    //Méthode affichant chaque mouvement effectué par le joueur précédent pour faciliter la compréhension du jeu
    public static void AffichageSituation(int[][] plateau, char joueur, int pieceC, int ligne, int colonne, int nvLigne, int nvColonne, int distance, int direction) {
        String piece = piece(pieceC);
        String directions = direction(direction, pieceC);
        String couleur;
        String col = conversionEnString(colonne);
        String nvCol = conversionEnString(nvColonne);

        if (!estEnEchec(plateau, joueur)) {     //affichage que si le mouvement ne met pas en échec
            if (pieceC > 6)
                couleur = "bleue ";
            else
                couleur = "jaune ";

            System.out.println();
            if (distance > 0) {         //pour le mode 1 (débutant)
                System.out.println("\uD83C\uDF1F " + piece + couleur + "avance de " + distance + " cases " + directions + " (" + col + "," + (8-ligne) + ") -> (" + nvCol + "," + (8 - nvLigne) + ")");
            } else {                    //pour le mode 2 (moyen) -> plus "pro"
                System.out.println("\uD83C\uDF1F " + piece + couleur + "s'est déplacé de la case (" + col + "," + (8 - ligne) + ") vers la case (" + nvCol + "," + (8 - nvLigne)+ ")");
            }
        }
    }

    //fonction booleene qui retourne vrai quand il y a pat, en parcourant le tableau à la recherche de mouvement possible
    //pour chaque pièce trouvée de la couleur du joueur
    public static boolean pat(int[][] plateau, char joueur) {
        if (estEnEchec(plateau, joueur)) {      //S'il y a échec alors il n'y a pas de pat
            return false;
        }

        //on parcourt tout le tableau à la recherche des pièces disponibles
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {

                int piece = plateau[ligne][colonne];        //on récupère la pièce trouvée

                if (couleurJoueur(plateau, ligne, colonne, joueur)) {     //On vérifie que la case contient une pièce du joueur
                    boolean coupPossible = false;

                    //Vérifier si la pièce peut bouger selon son type
                    if (piece == 6 || piece == 12)
                        coupPossible = pieceAutour3(plateau, ligne, colonne);
                    else if (piece == 1 || piece == 7)      //tours
                        coupPossible = pieceAutour(plateau, ligne, colonne);
                    else if (piece == 3 || piece == 9)      //fous
                        coupPossible = pieceAutour2(plateau, ligne, colonne);
                    else if (piece == 2 || piece == 8)      //cavalier
                        coupPossible = bougerCavalierPossible(plateau, ligne, colonne);
                    else if (piece == 4 || piece == 11 || piece == 5 || piece == 10)        //dame roi
                        coupPossible = pieceAutour2(plateau, ligne, colonne) || pieceAutour(plateau, ligne, colonne);

                    if (coupPossible) {         //si une pièce au moins peut bouger alors pas pat
                        return false;
                    }
                }
            }
        }
        return true;        //si aucune pièce peut bouger+le roi n'est pas en échec alors pat
    }


    //fonction booléenne qui parcourt tout le tableau et retourne vrai quand un des cas de matériel insuffisant est présent
    //(cas énumérés dans la méthode)
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

    //fonction qui renvoie vrai si une piece de coordonnée (ligneEnnemi, colonneEnnemi) peut aller
    //sur la case de coordonnée (ligne, colonne) (s'il peut l'attaquer) et faux sinon
    public static boolean peutAttaquer(int[][] plateau, int ligneEnnemi, int colonneEnnemi, int ligne, int colonne) {
        int piece = plateau[ligneEnnemi][colonneEnnemi];
        boolean possible = false;

        // cas du cavalier
        if (piece == 2 || piece == 8) {
            if (mouvementCavalier(ligneEnnemi, colonneEnnemi, ligne, colonne)) {
                possible = true;
            }
        }

        // cas de la tour
        else if (piece == 1 || piece == 7) {
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligne, colonne)) {
                possible = true;
            }
        }

        // cas du fou
        else if (piece == 3 || piece == 9) {
            if (mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligne, colonne)) {
                possible = true;
            }
        }

        // cas de la dame
        else if (piece == 4 || piece == 11) {
            if (mouvementTour(plateau, ligneEnnemi, colonneEnnemi, ligne, colonne) ||
                    mouvementFou(plateau, ligneEnnemi, colonneEnnemi, ligne, colonne)) {
                possible = true;
            }
        }

        // cas du pion
        else if (piece == 6) { // Pion Jaune
            if (ligne == ligneEnnemi + 1 && (colonne == colonneEnnemi + 1 || colonne == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        else if (piece == 12) { // Pion Bleu
            if (ligne == ligneEnnemi - 1 && (colonne == colonneEnnemi + 1 || colonne == colonneEnnemi - 1)) {
                possible = true;
            }
        }
        return possible;
    }

    //fonction qui renvoie un booléen pour savoir si le joueur a son roi en echec ou non
    public static boolean estEnEchec(int[][] plateau, char joueur) {
        //couleur du roi
        int Roi;
        if (joueur == 'B') {
            Roi = 10;
        } else {
            Roi = 5;
        }

        //on trouve la position du roi sur le plateau
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

        return caseEnEchec(plateau, roiL, roiC, Roi);
    }

    //fonction qui retourne vrai si la case du plateau choisie est attaquée par une pièce ennemie
    //(si la case mettrait le roi en échec)
    //permet de vérifier si le mouvement roque est possible pour le roi
    public static boolean caseEnEchec(int[][] plateau, int roiL, int roiC, int Roi) {
        // Parcourir tout le plateau pour trouver les pièces ennemies
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {

                //si la case est occupée par une pièce ennemie et qu'elle peut attaquer le roi
                if (!memeCouleurEtVide(plateau, l, c, Roi) && peutAttaquer(plateau, l, c, roiL, roiC)) {
                    return true;        //retourne vrai
                }
            }
        }
        return false;
    }

    //fonction qui renvoie un booléen pour savoir si le joueur est en échec et mat
    public static boolean estEnEchecEtMat(int[][] plateau, char joueur) {

        //si le joueur n'est pas en échec c'est faux
        if (!estEnEchec(plateau, joueur)){
            return false;
        }

        boolean joueurBleu = (joueur == 'B');

        //on parcourt tout le tableau et on prend chaque pièce
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {

                int piece = plateau[l][c];
                if (piece != 0) {   //si la piece n'est pas une case vide
                    if ((joueurBleu && piece > 6) || (!joueurBleu && piece <= 6)) { //si la pièce appartient au joueur

                        //on parcourt encore le tableau et on prend chaque destination
                        for (int nl = 0; nl < 8; nl++) {
                            for (int nc = 0; nc < 8; nc++) {

                                boolean mouvementValide = false;
                                //si la destination de la pièce est soit vide soit c'est une pièce ennemie
                                if (!memeCouleur(plateau, nl, nc, piece)) {

                                    if (piece == 5 || piece == 10) {    //mouvement roi
                                        mouvementValide = mouvementRoiRobot(l, c, nl, nc);
                                    } else if (piece == 6 || piece == 12) {     //mouvement pion
                                        mouvementValide = mouvementPionRobot(plateau, l, c, nl, nc, piece);
                                    } else {        //autres pièces
                                        mouvementValide = peutAttaquer(plateau, l, c, nl, nc);
                                    }
                                }

                                if (mouvementValide) {      //si le mouvement est valide

                                    //on simule le coup que cela ferait sur une copie
                                    int[][] copie = copiePlateau(plateau);
                                    copie[nl][nc] = piece;
                                    copie[l][c] = 0;

                                    if (!estEnEchec(copie, joueur)) {   //si le roi n'est plus en échec après la simulation alors il y a une moyen de parer l'échec donc faux
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //après avoir tester toutes les pièces le programme n'a pas renvoyer faux donc vrai
        return true;
    }
}
