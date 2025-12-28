import java.util.Scanner;

public class Main {
    static boolean abandonJoueur = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] plateau = new int[8][8];

        methodes.plateau(plateau);

        String pseudoBlanc = "";
        String pseudoNoir = "";
        int modeJeu;
        int choix = 0;

        System.out.println("BIENVENUE");
        while (choix != 1 && choix != 2 && choix != 3) {
            choix = methodes.debut(choix);
        }
        while (choix != 1) {
            if (choix == 2) {
                System.out.println("Indisponible pour le moment");
                choix = methodes.debut(choix);
            } else {
                System.out.println("Pourquoi ???");
                choix = methodes.debut(choix);
            }
        }

        System.out.print("Tapez 1 pour le mode débutant, tapez 2 pour le mode normal : ");
        modeJeu = Integer.parseInt(scanner.nextLine());

        System.out.print("Joueur 1 entrez votre pseudo : ");
        String pseudo1 = scanner.nextLine();

        System.out.print("Joueur 2 entrez votre pseudo : ");
        String pseudo2 = scanner.nextLine();

        System.out.print(pseudo1 + " voulez-vous jouer les bleus ? ");
        String bleu = scanner.nextLine();

        while (!bleu.equals("oui") && !bleu.equals("non")) {
            System.out.print("Saisie incorrecte !");
            System.out.print(pseudo1 + " voulez-vous jouer avec les bleus ? ");
            bleu = scanner.nextLine();
        }

        if (bleu.equals("oui")) {
            pseudoBlanc = pseudo1;
            pseudoNoir = pseudo2;
        } else if (bleu.equals("non")) {
            pseudoBlanc = pseudo2;
            pseudoNoir = pseudo1;
        }

        methodes.remplir(plateau);

        int tour = 0;
        while(tour < 100 && !abandonJoueur) {

            if (tour % 2 == 0) {
                System.out.println("Au tour de " + pseudoBlanc + " (bleus)");
                if (methodes.estEnEchec(plateau, 'B')) {
                    System.out.println(pseudoBlanc + " : votre Roi est en échec, vous devez le parer");
                }
                methodes.coordonnees(plateau, 'B', modeJeu);
                if(!abandonJoueur) {
                    methodes.remplir2(plateau);
                }
            } else {
                System.out.println("Au tour de " + pseudoNoir + " (jaunes)");
                if (methodes.estEnEchec(plateau, 'N')) {
                    System.out.println(pseudoNoir + " : votre Roi est en échec, vous devez le parer");
                }
                methodes.coordonnees(plateau, 'N', modeJeu);
                if(!abandonJoueur) {
                    methodes.remplir(plateau);
                }
            }
            tour++;
        }
    }

    public static void abandon(int[][] plateau, int ligne, int colonne, int tourLoin, int tourProche, int Roi, char joueur, int mode) {
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
            System.out.println("abandon du Roi " + couleur + " !");
        } else if (abandon.equals("non") && tourLoin!=-1) {
            pieces.roi(plateau, ligne, colonne, tourLoin, tourProche, Roi, joueur, mode);
        } else {
            methodes.coordonnees(plateau, joueur, mode);
        }
    }
}
