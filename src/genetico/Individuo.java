package genetico;


import maximiza.Objeto;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class Individuo {
    ArrayList<Objeto> objetos = new ArrayList<Objeto>();
    ArrayList<Integer> cromossomo = new ArrayList<Integer>();
    int geracao;


    int indice;

    public double probabilidade;
    public double fitness;
    public double peso;


    public Individuo(ArrayList<Objeto> objetos, int geracao, int indice) {
        this.objetos = objetos;
        this.geracao = geracao;
        this.indice = indice;
        this.gerarCromossomos();
    }

    public Individuo(ArrayList<Objeto> objetos, int geracao, int indice, ArrayList<Integer> cromossomo) {
        this.objetos = objetos;
        this.geracao = geracao;
        this.indice = indice;
        this.cromossomo = cromossomo;
        this.selecionarObjetos();
    }

    public String getNomeIndividuo(){
        return ("X"+ this.geracao + "," + this.indice + " ");
    }


    public void gerarCromossomos() {
        Random random = new Random();
        for (int i = 0; i < this.objetos.size(); i++) {
            this.cromossomo.add(random.nextInt(2));
        }
        this.selecionarObjetos();
    }


    public void mostrarCromossomos(ArrayList<Integer> cromossomo) {
        System.out.print("[ ");
        for (int i = 0; i < cromossomo.size(); i++) {
            System.out.print(cromossomo.get(i));
            if (i != cromossomo.size() -1) {
                System.out.print(" | ");
            }
        }
        System.out.print(" ]");


        // System.out.println();
    }

    public void mostrarIndividuo() {
        System.out.println("Indivíduo: " + this.getNomeIndividuo());
        this.mostrarCromossomos(this.cromossomo);
        System.out.println();
        System.out.println("Fitness: " + this.fitness);
        System.out.println();
    }


    public void selecionarObjetos() {

        double somaFitness = 0;
        double somaPeso = 0;

        for (int i = 0; i < this.objetos.size(); i++) {

            if (this.cromossomo.get(i) == 1) {
                somaFitness += this.objetos.get(i).lucro;
                somaPeso += this.objetos.get(i).peso;
            }

        }

        this.fitness = somaFitness;
        this.peso = somaPeso;

    }

    public static Comparator<Individuo> fitComparator = new Comparator<Individuo>() {
        @Override
        public int compare(Individuo individuo1, Individuo individuo2) {

            if (individuo1.fitness > individuo2.fitness) {
                return -1;
            } else if (individuo2.fitness > individuo1.fitness) {
                return 1;
            }

            return 0;
        }
    };

}

