import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MethodesTest {

    @Test
    void testConversionColEnInt() {
        assertEquals(0, Methodes.conversionColEnInt("a"), "cas colonne 0 minuscule");
        assertEquals(0, Methodes.conversionColEnInt("A"), "cas colonne 0 majuscule");
        assertEquals(7, Methodes.conversionColEnInt("h"), "cas colonne 7 majuscule");
        assertEquals(10, Methodes.conversionColEnInt("x"), "cas rejouer");
        assertEquals(8, Methodes.conversionColEnInt("z"),"cas hors limite" );
    }

    @Test
    void testMouvementCavalier() {
        assertTrue(Methodes.mouvementCavalier(4, 3, 2, 2), "cas (4,3) vers (2,2)");
        assertTrue(Methodes.mouvementCavalier(4, 3, 2, 4), "cas (4,3) vers (2,4)");
        assertFalse(Methodes.mouvementCavalier(4, 3, 4, 5), " cas mouvement horizontal");
    }

    @Test
    void testEstEnEchec() {
        int [][] plateau = new int[8][8];

        plateau[7][4] = 5;  //Roi Jaune
        plateau[0][4] = 7;  //Tour Bleue

        assertTrue(Methodes.estEnEchec(plateau, 'J'), "cas échec");

        plateau[5][4] = 6;  //Pion Jaune entre les deux
        assertFalse(Methodes.estEnEchec(plateau, 'J'), "cas pas d'échec");
    }

    @Test
    void testMemeCouleur() {
        int [][] plateau = new int[8][8];

        plateau[0][0] = 1; // Tour Jaune
        plateau[0][1] = 6; // Pion Jaune
        plateau[7][7] = 7; // Tour Bleue

        assertTrue(Methodes.memeCouleur(plateau, 0, 1, 1), "cas de même couleur");
        assertFalse(Methodes.memeCouleur(plateau, 7, 7, 1), "cas de couleur différente");
        assertFalse(Methodes.memeCouleur(plateau, 5, 5, 1), "cas case vide");
    }

    @Test
    void testMaterielInsuffisant() {
        int [][] plateau = new int[8][8];

        plateau[0][4] = 5;  //Roi Jaune
        plateau[7][4] = 10; //Roi Bleue
        assertTrue(Methodes.MaterielInsuffisant(plateau), "cas uniquement deux rois");


        plateau[0][2] = 3;  //Fou Jaune
        assertTrue(Methodes.MaterielInsuffisant(plateau), "cas roi+fou contre roi");

        plateau[3][3] = 4;  //Dame Jaune
        assertFalse(Methodes.MaterielInsuffisant(plateau), "cas materiel suffisant");
    }
}
