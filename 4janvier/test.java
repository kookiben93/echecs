import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodesTest {

    private int[][] plateau;

    @BeforeEach
    void plateau() {
        plateau = new int[8][8];
    }

    @Test
    void testConversionColEnInt() {
        assertEquals(0, methodes.conversionColEnInt("a"), "cas colonne 0 minuscule");
        assertEquals(0, methodes.conversionColEnInt("A"), "cas colonne 0 majuscule");
        assertEquals(7, methodes.conversionColEnInt("h"), "cas colonne 7 majuscule");
        assertEquals(10, methodes.conversionColEnInt("x"), "cas rejouer");
        assertEquals(8, methodes.conversionColEnInt("z"),"cas hors limite" );
    }

    @Test
    void testMouvementCavalier() {
        assertTrue(methodes.mouvementCavalier(4, 3, 2, 2), "cas (4,3) vers (2,2)");
        assertTrue(methodes.mouvementCavalier(4, 3, 2, 4), "cas (4,3) vers (2,4)");
        assertFalse(methodes.mouvementCavalier(4, 3, 4, 5), " cas mouvement horizontal");
    }

    @Test
    void testEstEnEchec() {
        plateau[7][4] = 5;  //Roi Jaune
        plateau[0][4] = 7;  //Tour Bleue

        assertTrue(methodes.estEnEchec(plateau, 'J'), "cas échec");

        plateau[5][4] = 6;  //Pion Jaune entre les deux
        assertFalse(methodes.estEnEchec(plateau, 'J'), "cas pas d'échec");
    }

    @Test
    void testMemeCouleur() {
        plateau[0][0] = 1; // Tour Jaune
        plateau[0][1] = 6; // Pion Jaune
        plateau[7][7] = 7; // Tour Bleue

        assertTrue(methodes.memeCouleur(plateau, 0, 1, 1), "cas de même couleur");
        assertFalse(methodes.memeCouleur(plateau, 7, 7, 1), "cas de couleur différente");
        assertFalse(methodes.memeCouleur(plateau, 5, 5, 1), "cas case vide");
    }

    @Test
    void testMaterielInsuffisant() {
        plateau[0][4] = 5;  //Roi Jaune
        plateau[7][4] = 10; //Roi Bleue
        assertTrue(methodes.MaterielInsuffisant(plateau), "cas uniquement deux rois");


        plateau[0][2] = 3;  //Fou Jaune
        assertTrue(methodes.MaterielInsuffisant(plateau), "cas roi+fou contre roi");

        plateau[3][3] = 4;  //Dame Jaune
        assertFalse(methodes.MaterielInsuffisant(plateau), "cas materiel suffisant");
    }
}
