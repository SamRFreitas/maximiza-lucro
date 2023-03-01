package genetico;

import java.util.ArrayList;

public class Print {
    public static void mostrarPopulacao(ArrayList<Individuo> populacao, String titulo) {
        System.out.println("---------------------------------------");
        System.out.println(titulo);


        for(Individuo individuo :  populacao) {
            System.out.print(individuo.getNomeIndividuo());
            individuo.mostrarCromossomos(individuo.cromossomo);
            System.out.print(" | Fit: " + individuo.fitness + " ");
            System.out.print(" | Peso: " + individuo.peso + " ");
            System.out.print(" | Prob: " + individuo.probabilidade + " ");
            System.out.println();
        }

        System.out.println("---------------------------------------");
    }
}
