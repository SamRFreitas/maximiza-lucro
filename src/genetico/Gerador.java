package genetico;


import maximiza.Objeto;


import java.util.ArrayList;


public class Gerador {
    // ArrayList<Geracao> geracao = new ArrayList<Geracao>();


    ArrayList<Individuo> populacao = new ArrayList<Individuo>();
    float taxaDeReproducao;


    float taxaDeMutacao;


    int numeroDeGeracoes;


    int quantidadeDeIndividuos;


    public Gerador (float taxaDeReproducao, double taxaDeMutacao, int numeroDeGeracoes, int quantidadeDeIndividuos, ArrayList<Objeto> objetos) {
        for (int i = 1; i <= numeroDeGeracoes; i++) {
            for (int j = 1; j <= quantidadeDeIndividuos; j++) {


                this.populacao.add(new Individuo(objetos, i, j));
            }
        }

        this.calcularProbabilidades();

        this.mostrarPopulacao();


    }

    public void calcularProbabilidades () {
        double fitnessTotal = 0;

        for(Individuo individuo :  this.populacao) {
            fitnessTotal += individuo.fitness;
        }

        for(Individuo individuo :  this.populacao) {
            individuo.probabilidade = individuo.fitness/fitnessTotal;
        }
    }

    public void mostrarPopulacao() {

        for(Individuo individuo :  this.populacao) {
            System.out.print("X"+ individuo.geracao + "," +individuo.indice + " ");
            individuo.mostrarCromossomos();
            System.out.print(" | Fit: " + individuo.fitness + " ");
            System.out.print(" | Prob: " + individuo.probabilidade + " ");
            System.out.println();
        }

    }

    public void selecionarPares() {
        // PREENCHER UM VETOR BASEADO NA PROBABILIDADE DE ESCOLHA DO INDIVIDUDO
        //int[] vetorPorcentagem = ;

        //
    }


}

