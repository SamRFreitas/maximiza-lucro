package genetico;


import maximiza.Objeto;


import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


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

        Collections.sort(this.populacao, Individuo.fitComparator);

        this.calcularProbabilidades();

        Print.mostrarPopulacao(this.populacao);

        Roleta roleta = new Roleta(this.populacao);



        //this.selecionarPares();

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

    public void selecionarPares() {

        ArrayList<Integer> probabilidadesDaPopulacao = new ArrayList<>();
        ArrayList<Individuo> populacaoRoleta = new ArrayList<Individuo>(this.populacao);

        //this.mostrarPopulacao(populacaoRoleta);

        //populacaoRoleta.remove(populacaoRoleta.get(0));
        //populacaoRoleta.remove(populacaoRoleta.get(0));

        //this.mostrarPopulacao(populacaoRoleta);

        for(Individuo individuo : populacaoRoleta) {
            int porcentagem =  (int) Math.round(individuo.probabilidade * 100);
            probabilidadesDaPopulacao.add(porcentagem);
        }



        //PREENCHER UM VETOR BASEADO A PROBABILIDADE DE ESCOLHA DO INDIVIDUDO
        ArrayList<Integer> vetorPorcentagem = new ArrayList<Integer>(100);


        for(int porcentagem : probabilidadesDaPopulacao) {
            for(int i=0; i < porcentagem; i++) {
                vetorPorcentagem.add(porcentagem);
                System.out.println("Index: "+ (vetorPorcentagem.size()-1) + " Value: " + porcentagem);
            }
        }

        Random random = new Random();

        int index = random.nextInt(99);
        int  probabilidadeEscolhida = vetorPorcentagem.get(index);


        System.out.println("Index: " + index);
        System.out.println("Porcentagem escolhida: " + probabilidadeEscolhida);




    }


}

