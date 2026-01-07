import java.util.Scanner;

public class Main {
    static boolean abandonJoueur = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] plateau = new int[8][8];

        methodes.plateau(plateau);

        String pseudoBlanc;
        String pseudoNoir="";
        String pseudo1;
        String pseudo2="";
        int modeJeu;
        int choix = -1;
        int robot;

        System.out.println("BIENVENUE");
        while (choix != 1 && choix != 2 && choix != 3) {
            choix = methodes.debut();
        }
        while (choix == 2) {
            choix = methodes.regles();
        }

        if (choix == 3) {
            System.out.println("Dommage :(");
        }

        else {
            //choix du mode (1v1 ou robot)
            do {
                System.out.print("Voulez-vous jouer en 1v1 (1) contre le robot (2) ? : ");
                String robotS = scanner.nextLine();
                robot = methodes.conversionEnInt(robotS);
            } while (robot!=1 && robot!=2);

            //choix difficulté (débutant ou moyen)
            do{
                System.out.print("Tapez 1 pour le mode débutant, tapez 2 pour le mode moyen : ");
                String modeS = scanner.nextLine();

                modeJeu = methodes.conversionEnInt(modeS);
            } while (modeJeu!=1 && modeJeu!=2);

            //Si le mode est 1v1, demande le pseudo des 2 joueurs
            if(robot==1) {
                System.out.print("Joueur 1 entrez votre pseudo : ");
                pseudo1 = scanner.nextLine();

                System.out.print("Joueur 2 entrez votre pseudo : ");
                pseudo2 = scanner.nextLine();
            }
            //Si le mode est robot, demande le pseudo du seul joueur
            else{
                System.out.print("Entrez votre pseudo : ");
                pseudo1 = scanner.nextLine();
            }

            //Demande du choix de la couleur des pièces
            System.out.print(pseudo1 + " voulez-vous jouer les bleus ? (ils commencent) ");
            String bleu = scanner.nextLine();

            while (!bleu.equals("oui") && !bleu.equals("non")) {
                System.out.print("Saisie incorrecte ! ");
                System.out.print(pseudo1 + " voulez-vous jouer avec les bleus ? (ils commencent) ");
                bleu = scanner.nextLine();
            }

            if(robot==1) {                          //mode 1v1
                if (bleu.equals("oui")) {
                    pseudoBlanc = pseudo1;          //stocke les pseudos des 2joueurs
                    pseudoNoir = pseudo2;           //en fonction de la couleur choisie
                } else {                            //par le joueur1
                    pseudoBlanc = pseudo2;
                    pseudoNoir = pseudo1;
                }
            }
            else{                                   //mode robot
                if (bleu.equals("oui")) {
                    pseudoBlanc = pseudo1;          //stocke le pseudo du seul joueur
                } else {                            //en fonction de la couleur qu'il a choisi
                    pseudoBlanc = pseudo2;
                }
            }

            if(robot == 1 || (bleu.equals("oui")))      //affiche le plateau si le mode est 1v1 ou si le mode est robot
                methodes.remplir(plateau);              //et le joueur choisi les blancs (commence donc la partie)

            int tour = 0;
            char joueur;

            while (!abandonJoueur && !methodes.MaterielInsuffisant(plateau)) {
                //initialise les couleurs en fonction du tour (pair = blancs et impair = noirs)
                if (tour % 2 == 0){
                    joueur = 'B';
                } else {
                    joueur = 'N';
                }

                //Affichage des gagnants quand il y a échec et mat
                if (methodes.estEnEchecEtMat(plateau, joueur)) {
                    System.out.println("\n =========================================== ÉCHEC ET MAT ============================================");
                    if(robot==1) {      //mode 1v1, il y a toujours un joueur qui a gagné
                        if (joueur == 'B') {
                            System.out.println("Félicitations " + pseudoNoir + " ! Les Jaunes ont gagné.");
                        } else {
                            System.out.println("Félicitations " + pseudoBlanc + " ! Les Bleus ont gagné.");
                        }
                    }
                    else {          //mode robot, le joueur a soit gagné soit perdu
                        if ((bleu.equals("oui") && joueur == 'B') || (bleu.equals("non") && joueur == 'N'))
                            System.out.println("Félicitations " + pseudo1 + " ! Vous avez gagné.");
                        else
                            System.out.println(pseudo1 + " vous avez perdu !");
                    }
                    break;
                }

                if (bleu.equals("oui")) {       //si le joueur1 joue les bleus,
                    if (tour % 2 == 0) {        //il commence (en mode robot ET en mode 1v1, c'est lui qui commence)
                        System.out.println("Au tour de " + pseudoBlanc + " (bleus)");
                        if (methodes.estEnEchec(plateau, joueur)) {     //tant que le joueur est en échec on le prévient qu'il doit changer la situation
                            System.out.println();
                            System.out.println("⚠\uFE0F " + pseudoBlanc + " : votre Roi est en échec, vous devez le parer");
                        }
                        methodes.coordonnees(plateau, joueur, modeJeu);     //appel de la méthode demandant les coordonnées de la pièce à bouger
                        if (!abandonJoueur && robot == 1) {         //si le joueur n'a pas encore abandonné
                            methodes.remplir2(plateau);                 //Affichage du plateau avec les mouvements effectués pour le tour d'après
                        }
                    } else {
                        if(robot==1) {      //Si le mode est 1v1
                            System.out.println("Au tour de " + pseudoNoir + " (jaunes)");
                            if (methodes.estEnEchec(plateau, joueur)) {     //tant que le joueur est en échec on le prévient qu'il doit changer la situation
                                System.out.println();
                                System.out.println("⚠\uFE0F " + pseudoNoir + " : votre Roi est en échec, vous devez le parer");
                            }
                            methodes.coordonnees(plateau, joueur, modeJeu);     //appel de la méthode demandant les coordonnées de la pièce à bouger
                            if (!abandonJoueur) {                   //si le joueur n'a pas encore abandonné
                                methodes.remplir(plateau);              //Affichage du plateau avec les mouvements effectués pour le tour d'après
                            }
                        } else {            //Si le mode est robot, c'est donc le robot le joueur2
                            methodes.robotChoix(plateau, joueur);       //appel de la méthode du robot
                            methodes.remplir(plateau);          //affichage du plateau avec les bleus en bas et les mouvements effectués par le robot
                        }
                    }
                } else {                    //si le joueur1 joue les jaunes
                    if (tour % 2 == 0) {    //c'est le joueur2 qui commence (le robot pour l'autre mode)
                        //mode 1v1
                        if(robot==1) {
                            System.out.println("Au tour de " + pseudoBlanc + " (bleus)");
                            if (methodes.estEnEchec(plateau, joueur)) {     //tant que le joueur est en échec on le prévient qu'il doit changer la situation
                                System.out.println("⚠\uFE0F " + pseudoBlanc + " : votre Roi est en échec, vous devez le parer");
                            }
                            methodes.coordonnees(plateau, joueur, modeJeu);     //appel de la méthode demandant les coordonnées de la pièce à bouger
                            if (!abandonJoueur) {                   //si le joueur n'a pas encore abandonné
                                methodes.remplir2(plateau);             //Affichage du plateau avec les mouvements effectués pour le tour d'après
                            }
                        }
                        //mode robot
                        else {
                            methodes.robotChoix(plateau, joueur);       //appel de la méthode du robot
                            methodes.remplir2(plateau);         //affichage du plateau avec les jaunes et les mouvements effectués par le robot
                        }
                    } else {
                        System.out.println("Au tour de " + pseudoNoir + " (jaunes)");
                        if (methodes.estEnEchec(plateau, joueur)) {     //tant que le joueur est en échec on le prévient qu'il doit changer la situation
                            System.out.println("⚠\uFE0F " + pseudoNoir + " : votre Roi est en échec, vous devez le parer");
                        }
                        methodes.coordonnees(plateau, joueur, modeJeu);     //appel de la méthode demandant les coordonnées de la pièce à bouger
                        if (!abandonJoueur && robot == 1) {         //si le joueur n'a pas encore abandonné et le mode est 1v1 (en mode robot l'affichage est inutile après le tour du joueur)
                            methodes.remplir(plateau);                  //Affichage du plateau avec les mouvements effectués pour le tour d'après
                        }
                    }
                }
                tour++;
            }
            //Affichage des gagnants quand il y a abandon
            if (abandonJoueur) {
                if(robot==1) {      //mode 1v1
                    if (tour % 2 == 0)
                        System.out.println("Félicitations ! c'est " + pseudoBlanc + " qui a gagné");
                    else
                        System.out.println("Félicitations ! c'est " + pseudoNoir + " qui a gagné");
                }
                else{       //mode robot
                    System.out.println(pseudo1 + " vous avez perdu !");
                }
            }
            //Affichage de fin de partie en cas de NULL
            else if (methodes.MaterielInsuffisant(plateau)) {
                System.out.println("\n =========================================== NULL ============================================");
                System.out.println("Partie nulle : matériel insuffisant pour continuer");
            }
        }
    }

    //Méthode qui demande la confirmation d'abandon et modifie la valeur de abandonJoueur en true
    //si la réponse est oui ou renvoi à continuer la partie si la réponse est non
    public static void abandon(int[][] plateau, char joueur, int mode) {
        Scanner sc = new Scanner(System.in);
        String abandon;
        String couleur;
        if(joueur=='B')
            couleur = "bleu";
        else
            couleur = "jaune";

        System.out.print("Êtes vous sûrs de vouloir abandonner la partie ? ");
        abandon = sc.nextLine();

        while (!abandon.equals("oui") && !abandon.equals("non")) {
            System.out.print("Saisie incorrecte ! ");
            System.out.print("Voulez-vous abandonner la partie ?? ");
            abandon = sc.nextLine();
        }

        if (abandon.equals("oui")) {
            abandonJoueur=true;
            //Affichage de fin de partie en cas d'abandon
            System.out.println("\n =========================================== ABANDON ============================================");
            System.out.println("Abandon du Roi " + couleur + " !");
            System.out.println();
        } else {
            System.out.println();
            methodes.coordonnees(plateau, joueur, mode);
        }
    }
}
