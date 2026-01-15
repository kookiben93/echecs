import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodesTest {

    @Test
    void TestvaleurLaPlusProche() {
        int[] tab = {12, 1, 8, 6, 18, 9};
        
        assertEquals(8, Methodes.valeurLaPlusProche(tab, 8), "cas valeur présente");
        assertEquals(8, Methodes.valeurLaPlusProche(tab ,7), "cas 2 valeurs à même distance");
        assertEquals(12, Methodes.valeurLaPlusProche(tab, 14), "cas moyen");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, -999), "cas négatif plus petit que toutes les valeurs du tableau");
        assertEquals(18, Methodes.valeurLaPlusProche(tab, 999),"cas positif plus grand que toutes les valeurs du tableau" );
    }
    
    @Test
    void valeurLaPlusProcheDichotomique() {
        int[] tab = {1, 2, 8, 15, 15, 17, 18, 19, 20};
        
        assertEquals(8, Methodes.valeurLaPlusProche(tab, 8), "cas valeur présente");
        assertEquals(17, Methodes.valeurLaPlusProche(tab , 16), "cas 2 valeurs à même distance");
        assertEquals(9, Methodes.valeurLaPlusProche(tab, 10), "cas moyen");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, -999), "cas négatif plus petit que toutes les valeurs du tableau");
        assertEquals(20, Methodes.valeurLaPlusProche(tab, 999), "cas positif plus grand que toutes les valeurs du tableau" );
        assertEquals(20, Methodes.valeurLaPlusProche(tab, 20), "cas limite valeur présente, la plus à droite");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, 1), "cas limite valeur présente, la plus à gauche" );
    }
}
