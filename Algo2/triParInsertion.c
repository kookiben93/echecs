#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define TAILLE 20000

void triInsertion(int tab[], int longueur);

int main() {
    int tab[TAILLE];
    int i;
    int max=50;

    srand(time(NULL));

    for (i = 0; i < TAILLE; i++) {
        tab[i] = rand() % (max+1);
    }

    triInsertion(tab, TAILLE);

    for(int i = 0; i < TAILLE; i++){
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
