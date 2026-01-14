import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long start = System.nanoTime();

        //int[] données = Methodes.générerDonnéesEntreBornes(20, 0, 20);
        //Methodes.afficher(données);

        //int[] données = Methodes.générerDonnées(200000);  //cas moyen
        //int[] données = Methodes.trierCroissant(200000);  //meilleur cas
        int[] données = Methodes.trierDecroissant(200000); // pire cas
        Methodes.triInsertion(données);    // trie le tableau en utilisant un tri par insertion

        //Arrays.sort(données); // trie le tableau en utilisant un algorithme de type Quicksort

        //System.out.println(Arrays.toString(données));

        /*System.out.print("valeur la plus proche : ");
        int valeur = sc.nextInt();

        System.out.println(Methodes.valeurLaPlusProche(données, valeur));*/

        long end = System.nanoTime();

        System.out.println((end - start) / 1_000_000.0 + " ms");
    }

}
