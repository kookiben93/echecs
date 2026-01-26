import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //int[] données = Methodes.générerDonnéesEntreBornes(10000000, 0, 20);
        int[] données = Methodes.générerDonnées(10000000);  //tableau valeur aléatoire
        //int[] données = Methodes.trierCroissant(1000);  //tableau trié croissant
        //int[] données = Methodes.trierDecroissant(200000); // tableau trié décroissant


        //Methodes.triInsertion(données);    // trie le tableau en utilisant un algorithme de tri par insertion
        Arrays.sort(données); // trie le tableau en utilisant un algorithme de type Quicksort

        //Methodes.afficher(données); //affiche le tableau

        //System.out.print("valeur la plus proche de 15: ");
        //System.out.println(Methodes.valeurLaPlusProche(données, 15)); //tableau pas trié
        //System.out.println(Methodes.valeurLaPlusProcheDichotomique(données, 15)); //tableau trié croissant

    }
}
