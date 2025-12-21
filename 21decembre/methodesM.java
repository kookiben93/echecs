import java.sql.SQLOutput;
import java.util.Scanner;

public class methodesM {

    public static boolean mouvementCavalier(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        boolean valeur=true;

        if(nvLigne-ligne==2 || nvLigne-ligne==-2){
            if(nvColonne-colonne!=1 && nvColonne-colonne!=-1){
                valeur=false;
            }
        }
        else if(nvLigne-ligne==1 || nvLigne-ligne==-1){
            if(nvColonne-colonne!=2 && nvColonne-colonne!=-2){
                valeur=false;
            }
        }
        else{
            valeur=false;
        }
        return valeur;
    }

    public static boolean mouvementTour(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        boolean valeur=true;

        if(nvLigne!=ligne){
            if(nvColonne!=colonne){
                valeur=false;
            }
        }
        else if(nvColonne!=colonne){
            if(nvLigne!=ligne){
                valeur=false;
            }
        }
        else{
            valeur=false;
        }
        return valeur;
    }

    public static boolean mouvementFou(int[][] plateau, int ligne, int colonne, int nvLigne, int nvColonne) {
        boolean valeur=true;

        if(nvLigne==ligne || nvColonne==colonne){          
                valeur=false;
        }

        return valeur;
    }

    public static boolean ligneCavalier(int[][] plateau, int ligne, int nvLigne){
        boolean valeur=false;

        if(nvLigne-ligne==2 || nvLigne-ligne==-2 || nvLigne-ligne==1 || nvLigne-ligne==-1){
            valeur=true;
        }
        return valeur;
    }

    public static void destinationPiece(int[][] plateau, int ligne, int colonne, int piece){
        int NvLigne, NvColonne;
        int mouvementValide;

        System.out.println("Où veux-tu aller ?");
        System.out.print("Entrez le numéro de la ligne : ");
        NvLigne = (sc.nextInt())-1;
            
        System.out.print("Entrez le numéro de la colonne : ");
        NvColonne = (sc.nextInt())-1;

        do {
            if(piece==1 || piece==2){
                mouvementValide = mouvementTour(plateau, ligne, colonne, NvLigne, NvColonne);
            }
            else if(piece==3 || piece==9){
                mouvementValide = mouvementFou(plateau, ligne, colonne, NvLigne, NvColonne);
            }
            
            if((NvColonne<0 || NvColonne>7) || methodes.memeCouleur(plateau, NvLigne, NvColonne, couleur)){
                System.out.println("La pièce ne peut pas aller là");
                System.out.print("Entrez un autre numéro de ligne : ");
                NvLigne = (sc.nextInt())-1;
                System.out.print("Entrez un autre numéro de colonne : ");
                NvColonne = (sc.nextInt())-1;
            }
        } while (!mouvementValide)

        plateau[ligne][colonne] = 0;
        plateau[NvLigne][NvColonne] = couleur;
    }
}
