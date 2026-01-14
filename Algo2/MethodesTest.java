import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodesTest {

    int[] tab = {12, 1, 8, 6, 18, 9};

    @Test
    void TestvaleurLaPlusProche() {
        assertEquals(8, Methodes.valeurLaPlusProche(tab, 8), "cas valeur présente");
        assertEquals(8, Methodes.valeurLaPlusProche(tab ,7), "cas 2 valeurs à même distance");
        assertEquals(12, Methodes.valeurLaPlusProche(tab, 14), "cas moyen");
        assertEquals(1, Methodes.valeurLaPlusProche(tab, -999), "cas négatif plus petit que toutes les valeurs du tableau");
        assertEquals(18, Methodes.valeurLaPlusProche(tab, 999),"cas positif plus grand que toutes les valeurs du tableau" );
    }
}
