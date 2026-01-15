#include <stdio.h>

void triInsertion(int tab[], int longueur);

int main() {
    int tab[] = {3, 7, 2, 9, 4};

    triInsertion(tab, 5);

    for(int i = 0; i < 5; i++){
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
