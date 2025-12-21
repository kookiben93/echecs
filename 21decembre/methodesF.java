import java.sql.SQLOutput;
import java.util.Scanner;

public class methodesF {
    //affichage des directions en fonctions de la position de la pièce actuelle
    public static int affichageDirections(boolean haut, boolean gauche, boolean droite, boolean bas){
        Scanner sc = new Scanner(System.in);
        int direction=0;

        if (haut){
            if (gauche){
                if (droite){
                    if (bas){   //haut+gauche+droite+bas
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //haut+gauche+droite
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 3 pour aller à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (bas){   //haut+gauche+bas
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{      //haut+gauche
                        System.out.print("1 pour aller en haut, 2 pour aller à gauche : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            } else {
                if (droite){
                    if (bas){   //haut+droite+bas
                        System.out.print("1 pour aller en haut, 3 pour aller à droite, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //haut+droite
                        System.out.print("1 pour aller en haut, 3 pour aller à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (bas){   //haut+bas
                        System.out.print("1 pour aller en haut, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        } else {
            if (gauche){
                if (droite){
                    if (bas){   //gauche+droite+bas
                        System.out.print("2 pour aller à gauche, 3 pour aller à droite, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    } else{     //gauche+droite
                        System.out.print("2 pour aller à gauche, 3 pour aller à droite : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                } else{
                    if (bas){   //gauche+bas
                        System.out.print("2 pour aller à gauche, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            } else{
                if (droite){
                    if (bas){   //droite+bas
                        System.out.print("3 pour aller à droite, 4 pour aller en bas : ");
                        direction = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        }
        return direction;
    }


    public static int affichageChoixPion(boolean diagoDroite, boolean diagoGauche, boolean avanceUn) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        if (diagoDroite){
            if(diagoGauche){
                if(avanceUn){           //diagoDroite+diagoGauche+avanceUn
                    System.out.print("1 prendre la pièce en diagonale gauche, 2 prendre la pièce en diagonale droite, 3 avancer : ");
                    choix = Integer.parseInt(sc.nextLine());
                } else{                 //diagoDroite+diagoGauche
                    System.out.print("1 prendre la pièce en diagonale gauche, 2 prendre la pièce en diagonale droite : ");
                    choix = Integer.parseInt(sc.nextLine());
                }
            } else{
                if(avanceUn){          //diagoDroite+avanceUn
                    System.out.print("2 prendre la pièce en diagonale droite, 3 avancer : ");
                    choix = Integer.parseInt(sc.nextLine());
                }
            }
        } else{
            if(diagoGauche){
                if(avanceUn){       //diagoGauche+avanceUn
                    System.out.print("1 prendre la pièce en diagonale gauche, 3 avancer : ");
                    choix = Integer.parseInt(sc.nextLine());
                }
            }
        }

        return choix;
    }
}
