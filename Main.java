public class Main {
    public static void main(String[] args) {

        int[][] tab = new int[9][9];
        methodes.plateau(tab);

        methodes.remplir(tab);

        for(int i = 0; i < 100; i++){
            methodes.coordonnees(tab);

            methodes.remplir(tab);
        }
    }
}
