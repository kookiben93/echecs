import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodesTest {

    @Test
    void testTriInsertionTousLesCas() {

        int[] tab0 = {};
        Methodes.triInsertion(tab0);
        assertArrayEquals(new int[]{}, tab0, "cas vide");

        int[] tab1 = {42};
        Methodes.triInsertion(tab1);
        assertArrayEquals(new int[]{42}, tab1, "cas 1 élément");

        int[] tab2 = {1, 2, 3, 4, 5};
        Methodes.triInsertion(tab2);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, tab2,"cas trié croissant");

        int[] tab3 = {5, 4, 3, 2, 1};
        Methodes.triInsertion(tab3);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, tab3, "cas trié décroissant");

        int[] tab4 = {3, 1, 4, 5, 2};
        Methodes.triInsertion(tab4);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, tab4, "cas moyen");

    }


    @Test
    void TestvaleurLaPlusProche() {
        int[] tab = {12, 1, 8, 6, 18, 9};

        assertEquals(8, Methodes.valeurLaPlusProche(tab, 8), "cas valeur présente");
        assertEquals(8, Methodes.valeurLaPlusProche(tab ,7), "cas 2 valeurs à même distance");
        assertEquals(12, Methodes.valeurLaPlusProche(tab, 14), "cas moyen");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, -999), "cas négatif plus petit que toutes les valeurs du tableau");
        assertEquals(18, Methodes.valeurLaPlusProche(tab, 999),"cas positif plus grand que toutes les valeurs du tableau" );

        int[] tab2 = {};
        assertEquals(-1, Methodes.valeurLaPlusProche(tab2, 8), "cas tableau vide");
    }

    @Test
    void valeurLaPlusProcheDichotomique() {
        int[] tab = {1, 2, 8, 15, 15, 17, 18, 19, 20};

        assertEquals(8, Methodes.valeurLaPlusProche(tab, 8), "cas valeur présente");
        assertEquals(17, Methodes.valeurLaPlusProche(tab , 16), "cas 2 valeurs à même distance");
        assertEquals(8, Methodes.valeurLaPlusProche(tab, 10), "cas moyen");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, -999), "cas négatif plus petit que toutes les valeurs du tableau");
        assertEquals(20, Methodes.valeurLaPlusProche(tab, 999), "cas positif plus grand que toutes les valeurs du tableau" );
        assertEquals(20, Methodes.valeurLaPlusProche(tab, 20), "cas limite valeur présente, la plus à droite");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, 1), "cas limite valeur présente, la plus à gauche" );

        int[] tab2 = {};
        assertEquals(-1, Methodes.valeurLaPlusProche(tab2, 8), "cas tableau vide");
    }
}
