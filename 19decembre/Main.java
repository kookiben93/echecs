import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int joueur1=0;
        int joueur2=0;

        int[][] tab = new int[8][8];
        methodes.plateau(tab);

        methodes.remplir(tab);

        System.out.print("Joueur 1 entrez votre pseudo : ");
        String pseudo1 = scanner.nextLine();

        System.out.print("Joueur 2 entrez votre pseudo : ");
        String pseudo2 = scanner.nextLine();

        System.out.print(pseudo1 + "Veux-tu jouer les bleus (blancs) ou les jaunes (noirs) ?");
        String choix = scanner.nextLine();

        while(!choix.equals("blancs") && !choix.equals("noirs")){
            System.out.print(pseudo1 + "Veux-tu jouer les bleus (blancs) ou les jaunes (noirs) ? ");
            choix = scanner.nextLine();
        }

        if (choix.equals("blancs")) {
            joueur1=1;
            joueur2=2;
        }
        else if (choix.equals("noirs")) {
            joueur1=2;
            joueur2=1;
        }

        for(int i = 0; i < 100; i++){
            if(i%2==0) {
                methodes.coordonnees(tab, joueur1);
            }
            else{
                methodes.coordonnees(tab, joueur2);
            }
            methodes.remplir(tab);
        }
    }
}
