package genetico;


import maximiza.Objeto;


import java.util.ArrayList;
import java.util.Random;


public class Individuo {
    ArrayList<Objeto> objetos = new ArrayList<Objeto>();
    ArrayList<Integer> cromossomo = new ArrayList<Integer>();
    int geracao;


    int indice;

    public double probabilidade;
    public double fitness;


    public Individuo(ArrayList<Objeto> objetos, int geracao, int indice) {
        this.objetos = objetos;
        this.geracao = geracao;
        this.indice = indice;
        this.gerarCromossomos();
    }


    public void gerarCromossomos() {
        Random random = new Random();
        for (int i = 0; i < this.objetos.size(); i++) {
            this.cromossomo.add(random.nextInt(2));
        }
        this.selecionarObjetos();
    }


    public void mostrarCromossomos() {
        System.out.print("[ ");
        for (int i = 0; i < this.objetos.size(); i++) {
            System.out.print(this.cromossomo.get(i));
            if (i != 4) {
                System.out.print(" | ");
            }
        }
        System.out.print(" ]");


        // System.out.println();
    }


    public void selecionarObjetos() {

        double soma = 0;
        for (int i = 0; i < this.objetos.size(); i++) {
            if (this.cromossomo.get(i) == 1) {
                soma += this.objetos.get(i).lucro;
            }

        }

        this.fitness = soma;


    }

}

