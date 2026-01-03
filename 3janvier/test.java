import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test {

    @Test
    public void testhautGauche() {
        int[][] plateau = new int[8][8];
        int pionBleu = 12;

        assertFalse(methodes.hautGauche(plateau, 0, 0, pionBleu),
                "cas sortie de plateau");

        int TourBleu = 7;
        plateau[5][4] = pionBleu;
        plateau[4][3] = TourBleu;

        assertFalse(methodes.hautGauche(plateau, 5, 4, 12),
                "cas pièce allié");
    }
}
