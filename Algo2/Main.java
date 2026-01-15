import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] données = Methodes.générerDonnéesEntreBornes(10000000, 0, 20);
        //int[] données = Methodes.générerDonnées(10000000);  //cas moyen
        //int[] données = Methodes.trierCroissant(10000000);  //meilleur cas
        //int[] données = Methodes.trierDecroissant(200000); // pire cas

        //Methodes.triInsertion(données);    // trie le tableau en utilisant un algorithme de tri par insertion
        //Arrays.sort(données); // trie le tableau en utilisant un algorithme de type Quicksort

        System.out.print("valeur la plus proche de 15: ");
        System.out.println(Methodes.valeurLaPlusProche(données, 15)); //tableau pas trié
        //System.out.println(Methodes.valeurLaPlusProcheDichotomique(données, 15)); //tableau trié

    }
}
