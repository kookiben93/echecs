import java.util.Random;

public class Methodes {

    public static void triInsertion(int[] tab) {
        int temp;
        int j;
        for (int i = 1; i < tab.length - 1; i++) {
            j = i - 1;
            while ((j >= 0) && (tab[j] > tab[j + 1])) {
                temp = tab[j + 1];
                tab[j + 1] = tab[j];
                tab[j] = temp;
                j--;
            }
        }
    }

    public static int[] trierCroissant(int longueur) {
        int[] t = new int[longueur];
        for (int i = 0; i < t.length; i++)
            t[i] = i;
        return t;
    }

    public static int[] trierDecroissant(int longueur) {
        int[] t = new int[longueur];
        for (int i = 0; i < t.length; i++)
            t[i] = t.length - i;
        return t;
    }

    /*
        Retourne un tableau de int de la longueur demandée rempli avec des valeurs aléatoires
        (valeurs comprises entre Integer.MIN_VALUE=-2^31 et Integer.MAX_VALUE=2^31-1 inclus).
    */
    public static int[] générerDonnées(int longueur) {
        Random random = new Random();
        int[] t = new int[longueur];
        for (int i = 0; i < t.length; i++)
            t[i] = random.nextInt(); // tirage aléatoire d'un int quelconque (compris entre -2^31 et 2^31-1)
        return t;
    }

    /*
        Retourne un tableau de int de la longueur demandée rempli avec des valeurs aléatoires comprises entre min et max inclus.
    */
    public static int[] générerDonnéesEntreBornes(int longueur, int min, int max) {
        Random random = new Random();
        int[] t = new int[longueur];
        for (int i = 0; i < t.length; i++)
            t[i] = random.nextInt(max - min + 1) + min; // nextInt(borne) retourne un int compris entre 0 inclus et borne exclus.
        return t;
    }

    public static void afficher(int[] tab) {
        for (int i = 1; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
        System.out.println();
    }

    //La question 3.a
    //Méthode qui trouve la valeur la plus proche à celle recherchée dans un tableau de int pas trié
    //renvoie la valeur si elle est présente 
    //si 2 valeurs sont à égales distance, renvoi la valeur la plus grande
    public static int valeurLaPlusProche(int[] tab, int valeur) {
        int plusProche = tab[0];        //initialisation de la valeur la plus proche à la première valeur du tableau
        int i = 1;               //initialisation du compteur du while à 1

        while (i < tab.length && plusProche != valeur) {        //boucle tant que le tableau entier n'est pas parcouru et qu'un nombre égale à la valeur n'a pas été trouvée
            int distanceActuelle = Math.abs(tab[i] - valeur);        //distance entre valeur et tab[i] actuel
            int distancePlusProche = Math.abs(plusProche - valeur);        //distance entre valeur et le dernier nombre le plus proche trouvé

            if (distanceActuelle < distancePlusProche)        //si la distance avec le nombre actuel est plus courte que celle avec le dernier nombre le plus proche
                plusProche = tab[i];                    //remplacer la valeur du nombre le plus proche par le nombre actuel
            else if (distanceActuelle == distancePlusProche && plusProche < tab[i])        //si la distance est égale entre les 2, on prend le plus grand des 2
                plusProche = tab[i];
            i++;
        }
        return plusProche;        //retourne le nombre le plus proche
    }

    //La questions 3.b
    //Méthode qui trouve la valeur la plus proche à celle recherchée dans un tableau de int trié
    //par recherche dichoomique
    //renvoie la valeur si elle est présente 
    //si 2 valeurs sont à égales distance, renvoi la valeur la plus grande
    public static int valeurLaPlusProcheDichotomique(int[] tab, int valeur) {
        int indDebut = 0;        //indice du début de la partie du tableau dans laquelle on cherche initialisé à 0
        int indFin = tab.length - 1;        //indice du début de la partie du tableau dans laquelle on cherche initialisé à taille du tableau -1
        int indMilieu;            //indice de la valeur du milieu de la partie du tableau dans laquelle on cherche
        int plusProche = tab[0];        //valeur la plus proche qu'on trouve au fur et à mesure des recherches, initialisée à la première valeur du tableau
        
        while (indDebut <= indFin) {        //tant que indDebut et indFin se sont pas croisés
            indMilieu = (indDebut + indFin) / 2;        //initialisation de indMilieu actuel
            int distanceActuelle = Math.abs(tab[indMilieu] - valeur);        //distance entre valeur et tab[i] actuel
            int distancePlusProche = Math.abs(plusProche - valeur);        //distance entre valeur et le dernier nombre le plus proche trouvé

            if (distanceActuelle < distancePlusProche) {        //si la distance avec le nombre du milieu est plus courte que celle avec le dernier nombre le plus proche
                plusProche = tab[indMilieu];                 //remplacer la valeur du nombre le plus proche par le nombre du milieu
            } 
            else if (distanceActuelle == distancePlusProche && tab[indMilieu] > plusProche) {     //si la distance est égale entre les 2, on prend le plus grand des 2
                plusProche = tab[indMilieu];
            }

            if (tab[indMilieu] == valeur) {        //si la valeur du milieu actuel est égale à la valeur
                return tab[indMilieu];            //on retourne la valeur directement
            }
            else if (valeur > tab[indMilieu]) {        //si la valeur du milieu actuel est plus petite que valeur
                indDebut = indMilieu + 1;            //on continue la recherche dans la partie droite du tableau
            } 
            else {                //si la valeur du milieu actuel est plus grande que valeur
                indFin = indMilieu - 1;            //on continue la recherche dans la partie gauche du tableau
            }
        }
        return plusProche;            //retourne le nombre le plus proche
    }
}
