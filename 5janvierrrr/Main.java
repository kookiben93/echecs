import java.util.Scanner;

public class Main {
    static boolean abandonJoueur = false;

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] plateau = new int[8][8];

        methodes.plateau(plateau);

        String pseudoBlanc="";
        String pseudoNoir="";
        String pseudo1="";
        String pseudo2="";
        int modeJeu;
        int choix = -1;
        int robot;

        System.out.println("BIENVENUE");
        while (choix != 1 && choix != 2 && choix != 3 && choix != 4) {
            choix = methodes.debut();
        }
        while (choix == 2 || choix == 4) {
            if (choix == 4){
                System.out.println("Easter egg : On peut appuyer sur la coordonnée X dans 'colonne' pour rechoisir d'autres coordonnées)");
                System.out.println();
                choix = methodes.debut();
            } else {
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
                choix = methodes.debut();
            }

        }

        if (choix == 3) {
            System.out.println("Dommage :(");
        }

        else {
            do {
                System.out.print("Voulez-vous jouer en 1v1 (1) contre le robot (2) ? : ");
                String robotS = scanner.nextLine();
                robot = methodes.conversionEnInt(robotS);
            } while (robot!=1 && robot!=2);

            do{
                System.out.print("Tapez 1 pour le mode débutant, tapez 2 pour le mode normal : ");
                String modeS = scanner.nextLine();

                modeJeu = methodes.conversionEnInt(modeS);
            } while (modeJeu!=1 && modeJeu!=2);

            if(robot==1) {
                System.out.print("Joueur 1 entrez votre pseudo : ");
                pseudo1 = scanner.nextLine();

                System.out.print("Joueur 2 entrez votre pseudo : ");
                pseudo2 = scanner.nextLine();
            }
            else{
                System.out.print("Entrez votre pseudo : ");
                pseudo1 = scanner.nextLine();
            }

            System.out.print(pseudo1 + " voulez-vous jouer les bleus ? (ils commencent) ");
            String bleu = scanner.nextLine();

            while (!bleu.equals("oui") && !bleu.equals("non")) {
                System.out.print("Saisie incorrecte ! ");
                System.out.print(pseudo1 + " voulez-vous jouer avec les bleus ? (ils commencent) ");
                bleu = scanner.nextLine();
            }

            if(robot==1) {
                if (bleu.equals("oui")) {
                    pseudoBlanc = pseudo1;
                    pseudoNoir = pseudo2;
                } else {
                    pseudoBlanc = pseudo2;
                    pseudoNoir = pseudo1;
                }
            }
            else{
                if (bleu.equals("oui")) {
                    pseudoBlanc = pseudo1;
                } else {
                    pseudoBlanc = pseudo2;
                }
            }

            if(robot == 1 || (robot == 2 && bleu.equals("oui")))
                methodes.remplir(plateau);

            int tour = 0;
            char joueur;

            while (!abandonJoueur && !methodes.MaterielInsuffisant(plateau)) {
                if (tour % 2 == 0){
                    joueur = 'B';
                } else {
                    joueur = 'N';
                }

                if (methodes.estEnEchecEtMat(plateau, joueur)) {
                    System.out.println("\n =========================================== ÉCHEC ET MAT ============================================");
                    if(robot==1) {
                        if (joueur == 'B') {
                            System.out.println("Félicitations " + pseudoNoir + " ! Les Jaunes ont gagné.");
                        } else {
                            System.out.println("Félicitations " + pseudoBlanc + " ! Les Bleus ont gagné.");
                        }
                    }
                    else {
                        if ((bleu.equals("oui") && joueur == 'B') || (bleu.equals("non") && joueur == 'N'))
                                System.out.println("Félicitations " + pseudo1 + " ! Vous avez gagné.");
                        else
                                System.out.println(pseudo1 + " vous avez perdu !");
                    }
                    break;
                }

                if (bleu.equals("oui")) {
                    if (tour % 2 == 0) {
                        System.out.println("Au tour de " + pseudoBlanc + " (bleus)");
                        if (methodes.estEnEchec(plateau, joueur)) {
                            System.out.println(pseudoBlanc + " : votre Roi est en échec, vous devez le parer");
                        }
                        methodes.coordonnees(plateau, joueur, modeJeu);
                        if (!abandonJoueur && robot == 1) {
                            methodes.remplir2(plateau);
                        }
                    } else {
                        if(robot==1) {
                            System.out.println("Au tour de " + pseudoNoir + " (jaunes)");
                            if (methodes.estEnEchec(plateau, joueur)) {
                                System.out.println(pseudoNoir + " : votre Roi est en échec, vous devez le parer");
                                methodes.coordonnees(plateau, joueur, modeJeu);
                            }
                            methodes.coordonnees(plateau, joueur, modeJeu);
                            if (!abandonJoueur) {
                                methodes.remplir2(plateau);
                            }
                        } else {
                            methodes.robotChoix(plateau, joueur);
                            methodes.remplir(plateau);
                        }
                    }
                } else {
                    if (tour % 2 == 0) {
                        if(robot==1) {
                            System.out.println("Au tour de " + pseudoBlanc + " (bleus)");
                            if (methodes.estEnEchec(plateau, joueur)) {
                                System.out.println(pseudoBlanc + " : votre Roi est en échec, vous devez le parer");
                            }
                            methodes.coordonnees(plateau, joueur, modeJeu);
                            if (!abandonJoueur) {
                                methodes.remplir2(plateau);
                            }
                        }
                        else {
                            methodes.robotChoix(plateau, joueur);
                            methodes.remplir2(plateau);
                        }
                    } else {
                        System.out.println("Au tour de " + pseudoNoir + " (jaunes)");
                        if (methodes.estEnEchec(plateau, joueur)) {
                            System.out.println(pseudoNoir + " : votre Roi est en échec, vous devez le parer");
                        }
                        methodes.coordonnees(plateau, joueur, modeJeu);
                        if (!abandonJoueur && robot == 1) {
                            methodes.remplir(plateau);
                        }
                    }
                }
                tour++;
            }
            if (abandonJoueur) {
                if(robot==1) {
                    if (tour % 2 == 0)
                        System.out.println("Félicitations ! c'est " + pseudoBlanc + " qui a gagné");
                    else
                        System.out.println("Félicitations ! c'est " + pseudoNoir + " qui a gagné");
                }
                else{
                    System.out.println(pseudo1 + " vous avez perdu !");
                }
            } else if (methodes.MaterielInsuffisant(plateau)) {
                System.out.println("\n =========================================== NULL ============================================");
                System.out.println("Partie nulle : matériel insuffisant pour continuer");
            }
        }
    }

    public static void abandon(int[][] plateau, char joueur, int mode) {
        Scanner sc = new Scanner(System.in);
        String abandon;
        String couleur;
        if(joueur=='B')
            couleur = "bleu";
        else
            couleur = "jaune";

        System.out.print("Voulez-vous abandonner ? ");
        abandon = sc.nextLine();

        while (!abandon.equals("oui") && !abandon.equals("non")) {
            System.out.print("Saisie incorrecte ! ");
            System.out.print(" voulez-vous abandonner la partie ? ");
            abandon = sc.nextLine();
        }

        if (abandon.equals("oui")) {
            abandonJoueur=true;
            System.out.println("\n =========================================== ABANDON ============================================");
            System.out.println("Abandon du Roi " + couleur + " !");
            System.out.println();
        } else {
            methodes.coordonnees(plateau, joueur, mode);
        }
    }
}
