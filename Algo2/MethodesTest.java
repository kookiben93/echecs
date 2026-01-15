import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodesTest {

    @Test
    void testTriInsertion() {
        //Tableau déjà trié
        int[] tab1 = {1, 2, 3, 4, 5};
        int[] attendu1 = {1, 2, 3, 4, 5};
        Methodes.triInsertion(tab1);
        assertArrayEquals(attendu1, tab1);

        //Tableau trié à l'envers
        int[] tab2 = {5, 4, 3, 2, 1};
        int[] attendu2 = {1, 2, 3, 4, 5};
        Methodes.triInsertion(tab2);
        assertArrayEquals(attendu2, tab2);

        //Tableau avec valeurs égales
        int[] tab3 = {2, 3, 2, 3, 1};
        int[] attendu3 = {1, 2, 2, 3, 3};
        Methodes.triInsertion(tab3);
        assertArrayEquals(attendu3, tab3);

        //Tableau avec une seule valeur
        int[] tab4 = {42};
        int[] attendu4 = {42};
        Methodes.triInsertion(tab4);
        assertArrayEquals(attendu4, tab4);

        //Tableau vide
        int[] tab5 = {};
        int[] attendu5 = {};
        Methodes.triInsertion(tab5);
        assertArrayEquals(attendu5, tab5);

        //Tableau avec nombres négatifs
        int[] tab6 = {-2, -5, 0, 3, -1};
        int[] attendu6 = {-5, -2, -1, 0, 3};
        Methodes.triInsertion(tab6);
        assertArrayEquals(attendu6, tab6);
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
