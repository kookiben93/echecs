import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] plateau = new int[8][8];

        methodes.plateau(plateau);

        String pseudoBlanc="";
        String pseudoNoir="";

        System.out.print("Joueur 1 entrez votre pseudo : ");
        String pseudo1 = scanner.nextLine();

        System.out.print("Joueur 2 entrez votre pseudo : ");
        String pseudo2 = scanner.nextLine();

        System.out.print(pseudo1 + " veux-tu jouer les bleus (blancs) ou les jaunes (noirs) ? ");
        String choix = scanner.nextLine();

        while(!choix.equals("blancs") && !choix.equals("noirs")){
            System.out.print(pseudo1 + " veux-tu jouer les bleus (blancs) ou les jaunes (noirs) ? ");
            choix = scanner.nextLine();
        }

        if (choix.equals("blancs")) {
            pseudoBlanc=pseudo1;
            pseudoNoir=pseudo2;
        }
        else if (choix.equals("noirs")) {
            pseudoBlanc=pseudo2;
            pseudoNoir=pseudo1;
        }

        methodes.remplir(plateau);

        for(int tour = 0; tour < 100; tour++){
            if(tour%2==0) {
                System.out.println("Au tour de " + pseudoBlanc);
                methodes.coordonnees(plateau, 'B');
                methodes.remplir2(plateau);

            }
            else{
                System.out.println("Au tour de " + pseudoNoir);
                methodes.coordonnees(plateau, 'N');
                methodes.remplir(plateau);
            }
        }
    }
}

