
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MethodesTest {

    @Test
    void testConversionColEnInt() {
        assertEquals(0, methodes.conversionColEnInt("a"), "cas colonne 0 minuscule");
        assertEquals(0, methodes.conversionColEnInt("A"), "cas colonne 0 majuscule");
        assertEquals(7, methodes.conversionColEnInt("h"), "cas colonne 7 minuscule");
        assertEquals(10, methodes.conversionColEnInt("x"), "cas rejouer");
        assertEquals(8, methodes.conversionColEnInt("z"),"cas hors limite" );
    }

    @Test
    void testMouvementCavalier() {
        assertTrue(methodes.mouvementCavalier(4, 3, 2, 2), "cas (4,3) vers (2,2)");
        assertTrue(methodes.mouvementCavalier(4, 3, 2, 4), "cas (4,3) vers (2,4)");
        assertFalse(methodes.mouvementCavalier(4, 3, 4, 5), " cas mouvement horizontal");
        assertFalse(methodes.mouvementCavalier(4, 3, 5, 3), " cas mouvement vertical");
        assertFalse(methodes.mouvementCavalier(4, 3, 4, 3), " cas aucun mouvement");
    }

    @Test
    void testMouvementTour() {
        int[][] plateau = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 6, 0, 0, 0},       //pion jaune (2,4)
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 9, 0, 0, 7, 0, 0, 0},       //fou bleu (6,1)        //tour bleue (6,4)
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        assertTrue(methodes.mouvementTour(plateau, 6, 4, 6, 6), "cas mouvement horizontal");
        assertTrue(methodes.mouvementTour(plateau, 6, 4, 3, 4), "cas mouvement vertical");
        assertTrue(methodes.mouvementTour(plateau, 6, 4, 2, 4), "cas prise d'une pièce ennemie");
        //assertFalse(methodes.mouvementTour(plateau, 6, 4, 6, 0), "cas mouvement impossible (obstacle)");
        assertFalse(methodes.mouvementTour(plateau, 6, 4, 6, 4), " cas aucun mouvement");
    }

    @Test
    void testEstEnEchec() {
        int[][] plateau = {
                {0, 0, 0, 0, 5, 0, 0, 0},       //roi jaune (0,4)
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 7, 0, 0, 0},       //tour bleue (6,4)
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        assertTrue(methodes.estEnEchec(plateau, 'J'), "cas échec");

        plateau[5][4] = 6;  //pion jaune entre les deux
        assertFalse(methodes.estEnEchec(plateau, 'J'), "cas pas d'échec");
    }

    @Test
    void testMemeCouleur() {
        int[][] plateau = {
                {1, 0, 0, 0, 0, 0, 0, 0},       //tour jaune (0,0)
                {6, 0, 0, 0, 0, 0, 0, 0},       //pion jaune (1,0)
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 0, 0, 0, 0, 0}        //tour bleu (0,7)
        };

        assertTrue(methodes.memeCouleur(plateau, 0, 0, 6), "cas de même couleur");
        assertFalse(methodes.memeCouleur(plateau, 0, 7, 6), "cas de couleur différente");
        assertFalse(methodes.memeCouleur(plateau, 5, 5, 1), "cas case vide");
    }

    @Test
    void testMaterielInsuffisant() {
        int[][] plateau = {
                {0, 0, 0, 0, 5, 0, 0, 0},       //roi jaune (0,4)
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 10, 0, 0, 0}       //roi bleu (7, 4)
        };

        assertTrue(methodes.MaterielInsuffisant(plateau), "cas uniquement deux rois");

        plateau[0][2] = 3;  //fou jaune
        assertTrue(methodes.MaterielInsuffisant(plateau), "cas roi+fou contre roi");

        plateau[3][3] = 4;  //dame jaune
        assertFalse(methodes.MaterielInsuffisant(plateau), "cas materiel suffisant");
    }
}
