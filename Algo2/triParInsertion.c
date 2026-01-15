#include <stdio.h>        //pour print f
#include <stdlib.h>        //pour rand et srand
#include <time.h>           //pour le temps (utilisé dans srand)
#define TAILLE 20000        //taille définie à 20 000 (taille utilisé par le tableau)

void triInsertion(int tab[], int longueur);

int main() {
    int tab[TAILLE];        //initialisation d'un tableau de taille TAILLE
    int max=50;            //valeur du nombre le plus grand pouvant être généré dans le teableau aléatoirement

    srand(time(NULL));        //initialisation du générateur aléatoire avec l'heure actuelle

    for (i = 0; i < TAILLE; i++) {        //boucle de remplissage du tableau avec des valeurs aléatoires
        tab[i] = rand() % (max+1);        //comprises entre 0 et max (50 ici)
    }

    triInsertion(tab, TAILLE);            //appel de la méthode de tri du tableau (tri par insertion)

    for(int i = 0; i < TAILLE; i++){      //boucle d'affichage du tableau après le tri
        printf("%d ", tab[i]);
    }
}

void triInsertion(int tab[], int longueur) {
    int temp;
    int j;

    for (int i = 1; i < longueur; i++) {
        j = i - 1;
        while (j >= 0 && tab[j] > tab[j + 1]) {
            temp = tab[j + 1];
            tab[j + 1] = tab[j];
            tab[j] = temp;
            j--;
        }
    }
}
