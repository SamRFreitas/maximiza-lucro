package genetico;

import java.util.ArrayList;

public class Print {
    public static void mostrarPopulacao(ArrayList<Individuo> populacao) {
        System.out.println("---------------------------------------");

        for(Individuo individuo :  populacao) {
            System.out.print("X"+ individuo.geracao + "," +individuo.indice + " ");
            individuo.mostrarCromossomos();
            System.out.print(" | Fit: " + individuo.fitness + " ");
            System.out.print(" | Prob: " + individuo.probabilidade + " ");
            System.out.println();
        }

        System.out.println("---------------------------------------");
    }
}
